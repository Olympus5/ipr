package istic.pr.socket.tcp.chat;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Chat Server
 * @author Erwan IQUEL Mathieu LE CLEC'H
 * @version 5.0
 */
public class ServeurTCP {

	private static List<PrintWriter> printerSocketActives = new ArrayList<PrintWriter>();
	private static int port = 9999;
	private static ServerSocket ss = null;
	private static ExecutorService service = Executors.newFixedThreadPool(4);

	/**
	 * Main function
	 * @param args Array of arguments send by the user
	 */
	public static void main(String[] args) {
		boolean leave = false;
		Socket socket = null;

		if(args.length  < 1) {//On regarde si l'utilisateur à rentré un paramètre
			System.err.println("Veuillez entrer un charset pour votre serveur");
			System.exit(-1);
		}

		try {
			ss = new ServerSocket(port);//Création de la socket serveur

			while(!leave) {//On boucle tant qu'on veut pas quitter la boucle d'écoute
				//Waiting a client
				System.out.println("Waiting...");

				try {
					// Une fois une connexion reçu on lance un nouveau thread
					socket = ss.accept();
					service.execute(new TraiteUnClient(socket, args[0]));
				} catch(IOException e) {
					e.printStackTrace();
					leave = true;//Erreur on quitte la boucle
					/* Si il y a une erreur ne pas oublier de fermer la socket traité par 
					 * le Thread main
					 */
					socket.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(ss != null) {
				try {
					// Ne pas oublier de fermer la socket serveur
					ss.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * Function to manage client connection
	 * @param socketVersUnClient Client connection
	 * @param charset
	 */
	public static void traiterSocketCliente(Socket socketVersUnClient, String charset) {
		//Créer printer et reader
		PrintWriter pw = null;
		BufferedReader br = null;
		String result;

		try {
			pw = creerPrinter(socketVersUnClient, charset);
			ajouterPrinterSocketActive(pw);

			br = creerReader(socketVersUnClient, charset);

			String nom = avoirNom(br);

			//Tant qu’il y’a un message à lire via recevoirMessage
			while((result = recevoirMessage(br)) != null) {
				//Envoyer message au client via envoyerMessage
				result = nom + ">" + result;
				System.out.println(result);
				envoyerATouteLesSocketsActive(result);
			}

		} catch(SocketException e){

		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(pw != null) {
				enleverPrinterSocketActive(pw);
				pw.close();
			}

			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Create a BufferedReader
	 * @param socketVersUnClient Client connection
	 * @return The BufferedReader
	 * @throws IOException GetInputStream() & new InputStreamReader() exception
	 */
	public static BufferedReader creerReader(Socket socketVersUnClient, String charset) throws IOException {
		BufferedInputStream bis = new BufferedInputStream(socketVersUnClient.getInputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(bis, charset));

		return br;
	}

	/**
	 * Create a PrintWriter
	 * @param socketVersUnClient Client connection
	 * @return The PrintWriter
	 * @throws IOException GetOutputStream() exception
	 */
	public static PrintWriter creerPrinter(Socket socketVersUnClient, String charset) throws
	IOException {
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socketVersUnClient.getOutputStream(), charset));
		PrintWriter pw = new PrintWriter(bw);
		return pw;
	}

	/**
	 * Receive a message from the client
	 * @param reader BufferedReader to read a client message
	 * @return The client message
	 * @throws IOException ReadLine() exception
	 */
	public static String recevoirMessage(BufferedReader reader) throws IOException {
		return reader.readLine();
	}

	/**
	 * Send a message to the client
	 * @param printer PrintWriter to send a message to the client
	 * @param message The message
	 * @throws IOException PrintLn() & flush() exception
	 */
	public static void envoyerMessage(PrintWriter printer, String message) throws IOException {
		printer.println(message);
		printer.flush();
	}

	/**
	 * Read the name of a client
	 * @param reader To read the client input
	 * @return The name of client
	 * @throws IOException ReadLine() exception
	 */
	public static String avoirNom(BufferedReader reader) throws IOException {
		return reader.readLine().split("[^:]*: ")[1];
	}

	/**
	 * Add a printer to the list of server printers
	 * @param printer The printer
	 */
	public static void ajouterPrinterSocketActive(PrintWriter printer) {
		printerSocketActives.add(printer);
	}

	/**
	 * Remove a printer from the list of server printers  
	 * @param printer
	 */
	public static void enleverPrinterSocketActive(PrintWriter printer) {
		printerSocketActives.remove(printer);
	}

	/**
	 * 
	 * @param message
	 * @throws IOException
	 */
	public static synchronized void envoyerATouteLesSocketsActive(String message) throws IOException {
		//envoie le message à toutes les sockets actives
		int i = 0;
		for(PrintWriter printer : printerSocketActives) {
			System.out.println(i++);
			envoyerMessage(printer, message);
		}
	}
}