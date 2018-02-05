package tp.rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * @author Erwan IQUEL, Mathieu LE CLEC'H
 * @version 1.0
 */
public interface ReceiveCallback extends Remote {
	/**
	 * 
	 * @param message
	 * @throws RemoteException
	 */
	public void newMessage(String message) throws RemoteException;
}
