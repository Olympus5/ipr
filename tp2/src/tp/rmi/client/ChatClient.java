/**
 * 
 */
package tp.rmi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
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
			ChatRemote cr = (ChatRemote) Naming.lookup("//localhost/chat");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}

}
