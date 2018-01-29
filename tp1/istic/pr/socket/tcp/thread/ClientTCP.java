//...

package istic.pr.socket.tcp.thread;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
    	int port = 9999;
    	Socket s = null;
    	PrintWriter pw = null;
    	BufferedReader br = null;
    	String result = "";
    	
    	if(args.length  < 2) {
    		System.err.println("Veuillez entrer un nom et un charset pour votre client");
    		System.exit(-1);
    	}
    	
    	try {
    		String host = InetAddress.getLocalHost().getHostAddress();
    		
			s = new Socket(host, port);
			//créer reader et writer associés
			pw = creerPrinter(s, args[1]);
			br = creerReader(s, args[1]);
			
	        //Tant que le mot «fin» n’est pas lu sur le clavier,
	        
			envoyerNom(pw, args[0]);
			
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
     * @param p
     * @param message
     * @throws IOException
     */
    public static void envoyerMessage(PrintWriter p, String message) throws
    IOException {
    	p.write(message);
    	p.flush();
    }
    
    /**
     * 
     * @param printer
     * @param nom
     */
    public static void envoyerNom(PrintWriter printer, String nom) {
    	printer.write("NAME: " + nom);
    	printer.flush();
    }

}