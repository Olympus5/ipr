package tp.rmi.common;

import java.rmi.*;

/**
 * @author Erwan IQUEL, Mathieu LE CLEC'H
 * @version 1.0
 */
public interface ChatRemote extends Remote {
	/**
	 * 
	 * @param name
	 * @param message
	 * @return
	 * @throws RemoteException
	 */
	public String echo(String name, String message) throws RemoteException;
}
