/**
 * 
 */
package tp.rmi.server;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * @author Erwan IQUEL, Mathieu LE CLEC'H
 * @version 1.0
 */
public class ChatServer {
	public static void main(String[] args) {
		try {
			/*
			 * Il est possible de créer un registre dynamiquement, cela est avantageux quand nous avons un Serveur
			 * Qui instanci l'ensemble des méthodes qui vont être distribués:
			 
			 LocateRegistry.createRegistry(1099);
			 
			 * Plus besoin de lancer dans bin ou de spécifier le codebase pour le registry :)
			 */
			
			// On met en place notre Security Manager
			/*System.out.println("Mise en place du Security Manager...");
			if(System.getSecurityManager() == null) {
				System.setSecurityManager(new SecurityManager());
			}*/
			
			// On instancie l'objet qui va être distribué par le serveur
			ChatRemoteImpl chatRemoteImpl = new ChatRemoteImpl();
			
			//On enregsitre notre objet dans le registre RMI
			String url = "rmi://" + InetAddress.getLocalHost().getHostName() + "/ChatRemote";
			System.out.println("Enregistrement de l'objet avec l'url: " + url + "...");	
			Naming.rebind(url, chatRemoteImpl);
			
			System.out.println("Serveur en marche.");
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
