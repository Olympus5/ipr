/**
 * 
 */
package tp.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import tp.rmi.common.ChatRemote;

/**
 * @author Erwan IQUEL, Mathieu LE CLEC'H
 * @version 1.0
 */
public class ChatRemoteImpl extends UnicastRemoteObject implements ChatRemote {
	public static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * @throws RemoteException
	 */
	protected ChatRemoteImpl() throws RemoteException {
		super();
	}

	@Override
	public String echo(String name, String message) throws RemoteException {
		return name + ">" + message;
	}

}
