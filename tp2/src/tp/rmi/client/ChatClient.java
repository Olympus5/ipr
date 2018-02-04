package tp.rmi.client;

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
				System.out.println(((ChatRemote) r).echo("moi", "test"));
			} else {
				System.err.println("Mauvaise classe chargé: " + (ChatRemote.class));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
}
