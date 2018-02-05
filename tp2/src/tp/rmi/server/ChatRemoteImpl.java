/**
 * 
 */
package tp.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import tp.rmi.common.ChatRemote;
import tp.rmi.common.ReceiveCallback;

/**
 * @author Erwan IQUEL, Mathieu LE CLEC'H
 * @version 1.0
 */
public class ChatRemoteImpl extends UnicastRemoteObject implements ChatRemote {
	public static final long serialVersionUID = 1L;
	private List<ReceiveCallback> listCallback = new ArrayList<ReceiveCallback>();
	
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

	@Override
	public void send(String name, String message) throws RemoteException {
		for(ReceiveCallback callback : this.listCallback) {
			callback.newMessage(name + ">" + message);
		}
	}

	@Override
	public void registerCallback(ReceiveCallback callback) throws RemoteException {
		this.listCallback.add(callback);
	}

}
