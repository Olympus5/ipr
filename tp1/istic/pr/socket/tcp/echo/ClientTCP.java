//...

package istic.pr.socket.tcp.echo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 
 * @author erwan
 *
 */
public class ClientTCP {

	/**
	 * 
	 * @param args
	 */
    public static void main(String[] args) {
    	int port = 10000;
    	Socket s = null;
    	PrintWriter pw = null;
    	BufferedReader br = null;
    	String result = "";
    	
    	try {
    		String host = InetAddress.getLocalHost().getHostAddress();
    		
			s = new Socket(host, port);
			//créer reader et writer associés
			pw = creerPrinter(s);
			br = creerReader(s);
			
	        //Tant que le mot «fin» n’est pas lu sur le clavier,
	        
			while(!result.equals("FIN")) {
				//Lire un message au clavier
				result = lireMessageAuClavier();
		        //envoyer le message au serveur
				envoyerMessage(pw, result);
				//recevoir et afficher la réponse du serveur
				System.out.println(recevoirMessage(br));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(s != null) {
				try {
					s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
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
     * @return
     * @throws IOException
     */
    public static String lireMessageAuClavier() throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	System.out.println("Votre message: ");
    	return br.readLine();
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
     * @param p
     * @param message
     * @throws IOException
     */
    public static void envoyerMessage(PrintWriter p, String message) throws
    IOException {
    	p.println(message);
    	p.flush();
    }

}