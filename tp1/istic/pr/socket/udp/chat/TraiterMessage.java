/**
 * 
 */
package istic.pr.socket.udp.chat;

import java.io.IOException;
import java.net.MulticastSocket;

/**
 * @author Erwan IQUEL Mathieu LE CLEC'H
 * @version 1.0
 */
public class TraiterMessage implements Runnable {
	private boolean stop = false;
	private MulticastSocket socket = null;
	
	public TraiterMessage(MulticastSocket socket) {
		this.socket = socket;
	}
	
	public void stop() {
		this.stop = true;
	}
	
	@Override
	public void run() {
		while(!stop) {
			try {
				System.out.println(ChatMulticast.recevoirMessage(this.socket));
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}
}
