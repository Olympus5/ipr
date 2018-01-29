package istic.pr.socket.tcp.echo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

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
    	int port = 10000;
    	ServerSocket ss = null;
    	Socket si = null;
    	
    	try {
			 ss = new ServerSocket(port);
			 
			 while(true) {
		    		//Waiting a client
		    		System.out.println("Waiting...");
		    		try {
						//Create
						 si = ss.accept();
						 System.out.println("Handle a client..." + si);
						//Handle
						traiterSocketCliente(si);
					} catch (IOException e) {
						e.printStackTrace();
						break;
					} finally {
						//Close the connection
						if(si != null) {
							si.close();
						}
					}
		    	}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(ss != null) {
				try {
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
    public static void traiterSocketCliente(Socket socketVersUnClient) {
        //Créer printer et reader
    	PrintWriter pw = null;
    	BufferedReader br = null;
    	String result;
    	
    	try {
			 pw = creerPrinter(socketVersUnClient);
			 br = creerReader(socketVersUnClient);
	    	
	        //Tant qu’il y’a un message à lire via recevoirMessage
			while((result = recevoirMessage(br)) != null) {
		        //Envoyer message au client via envoyerMessage
				envoyerMessage(pw, result);
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
    public static BufferedReader creerReader(Socket socketVersUnClient)
    throws IOException {
        BufferedInputStream bis = new BufferedInputStream(socketVersUnClient.getInputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(bis));
        
        return br;
    }

    /**
     * 
     * @param socketVersUnClient
     * @return
     * @throws IOException
     */
    public static PrintWriter creerPrinter(Socket socketVersUnClient) throws
    IOException {
    	BufferedOutputStream bos = new BufferedOutputStream(socketVersUnClient.getOutputStream());
    	PrintWriter pw = new PrintWriter(bos);
    	
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
    	printer.println(message);
    	printer.flush();
    }
}