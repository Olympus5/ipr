package tp.rmi.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import tp.rmi.common.ChatRemote;

/**
 * @author Erwan IQUEL, Mathieu LE CLEC'H
 * @version 1.0
 */
public class ChatClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length < 1) {
			System.err.println("Veuillez rentrer un pseudo.");
			System.exit(-1);
		}
		
		try {
			String path = Paths.get("bin").toUri().toURL().toString();
			System.setProperty("java.rmi.codebase", path);
			
			//Mise en place du Security Manager coté client
			if(System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}

			//On charge la méthodé distribué par notre serveur RMI
			Remote r = Naming.lookup("rmi://" + InetAddress.getLocalHost().getHostName() + "/ChatRemote");
			
			//On check si c'est bien le bon objet qui nous a été renvoyé
			if(r instanceof ChatRemote) {
				String message = "";
				
				while(!message.equals("FIN")) {
					message = lireMessageAuClavier();
					System.out.println(((ChatRemote) r).echo(args[0], message));
				}
				System.out.println(((ChatRemote) r).echo("moi", "test"));
			} else {
				System.err.println("Mauvaise classe chargé: " + (r.getClass()));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Lit un message venant du clavier
	 * @return Message lu au clavier
	 * @throws IOException
	 */
	public static String lireMessageAuClavier() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Votre message: ");
		return br.readLine();
	}
}
