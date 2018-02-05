package tp.rmi.common;

import java.rmi.*;

/**
 * @author Erwan IQUEL, Mathieu LE CLEC'H
 * @version 1.0
 */
public interface ChatRemote extends Remote {
	/**
	 * Repeat a message
	 * @param name
	 * @param message
	 * @return The message with the name of the sender
	 * @throws RemoteException
	 */
	public String echo(String name, String message) throws RemoteException;
	
	public void send(String name, String message) throws RemoteException;
	
	public void registerCallback(ReceiveCallback callback) throws RemoteException;
}
