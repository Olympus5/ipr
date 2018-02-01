/**
 * 
 */
package tp.rmi.server;

import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.RemoteException;

import tp.rmi.common.ChatRemote;

/**
 * @author Erwan IQUEL, Mathieu LE CLEC'H
 * @version 1.0
 */
public class ChatServer {
	public static void main(String[] args) {
		try {
			String path = Paths.get("bin").toUri().toURL().toString();
			System.setProperty("java.rmi.server.codebase", path);
			
			ChatRemote cr = new ChatRemoteImpl();
			Naming.rebind("//localhost/echo", cr);
		} catch (RemoteException | MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
