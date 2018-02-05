package tp.rmi.client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import tp.rmi.common.ReceiveCallback;

/**
 * 
 * @author Erwan IQUEL, Mathieu LE CLEC'H
 * @version 1.0
 */
public class ReceiveCallbackImpl extends UnicastRemoteObject implements ReceiveCallback {
	
	private static final long serialVersionUID = 1L;
	
	protected ReceiveCallbackImpl() throws RemoteException {
		super();
	}

	@Override
	public void newMessage(String message) throws RemoteException {
		System.out.println(message);
	}

}
