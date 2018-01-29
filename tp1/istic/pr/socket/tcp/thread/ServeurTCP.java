package istic.pr.socket.tcp.thread;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author erwan
 *
 */
public class ServeurTCP {

	/**
	 * 
	 * @param args
	 */
    public static void main(String[] args) {
    	int port = 9999;
    	ServerSocket ss = null;
    	Socket socket = null;
    	ExecutorService service = Executors.newFixedThreadPool(4);
    	boolean leave = false;
    	
    	if(args.length  < 1) {//On regarde si l'utilisateur à rentré un paramètre
    		System.err.println("Veuillez entrer un charset pour votre serveur");
    		System.exit(-1);
    	}
    	
    	try {
			 ss = new ServerSocket(port);//Création de la socket serveur
			 
			 while(!leave) {//On boucle tant qu'on veut pas quitter la boucle d'écoute
				 try {
					 // Une fois une connexion reçu on lance un nouveau thread
					 socket = ss.accept();
					 service.execute(new TraiteUnClient(socket, args[0]));
				 } catch(IOException e) {
					 e.printStackTrace();
					 leave = true;//Erreur on quitte la boucle
				 } finally {
					 if(socket != null) {
						 try {
							 //Ne pas oublier de fermer la socket client
							 socket.close();
						 } catch(IOException e) {
							 e.printStackTrace();
						 }
					 }
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
     * 
     * @param socketVersUnClient
     */
    public static void traiterSocketCliente(Socket socketVersUnClient, String charset) {
        //Créer printer et reader
    	PrintWriter pw = null;
    	BufferedReader br = null;
    	String result;
    	
    	try {
			 pw = creerPrinter(socketVersUnClient, charset);
			 br = creerReader(socketVersUnClient, charset);
			 
			 String nom = avoirNom(br);
	    	
	        //Tant qu’il y’a un message à lire via recevoirMessage
			while((result = recevoirMessage(br)) != null) {
		        //Envoyer message au client via envoyerMessage
				envoyerMessage(pw, nom + ">" + result);
			}
			
		} catch(SocketException e){
			
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(pw != null) {
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
     * 
     * @param socketVersUnClient
     * @return
     * @throws IOException
     */
    public static BufferedReader creerReader(Socket socketVersUnClient, String charset)
    throws IOException {
    	BufferedInputStream bis = new BufferedInputStream(socketVersUnClient.getInputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(bis, charset));
        
        return br;
    }

    /**
     * 
     * @param socketVersUnClient
     * @return
     * @throws IOException
     */
    public static PrintWriter creerPrinter(Socket socketVersUnClient, String charset) throws
    IOException {
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socketVersUnClient.getOutputStream(), charset));
    	PrintWriter pw = new PrintWriter(bw);
    	return pw;
    }

    /**
     * 
     * @param reader
     * @return
     * @throws IOException
     */
    public static String recevoirMessage(BufferedReader reader) throws
    IOException {
    	return reader.readLine();
    }

    /**
     * 
     * @param printer
     * @param message
     * @throws IOException
     */
    public static void envoyerMessage(PrintWriter printer, String message)
    throws IOException {
    	printer.write(message);
    	printer.flush();
    }
    
    public static String avoirNom(BufferedReader reader) throws IOException {
    	return reader.readLine().split("[^:]*: ")[1];
    }
}