/**
 * 
 */
package istic.pr.socket.tcp.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Erwan IQUEL Mathieu LE CLEC'H
 * @version 6.0
 */
public class TraiterUnServeur implements Runnable {
	private BufferedReader br;
	private boolean interrupted = false;
	private boolean stop = false;
	/**
	 * Constructor
	 * @param pw
	 * @param br
	 */
	public TraiterUnServeur(BufferedReader br) {
		this.br = br;
	}
	
	public void interrupt() throws InterruptedException {
		this.interrupted = true;
		throw new InterruptedException();
	}
	
	public boolean interrupted() {
		return this.interrupted;
	}
	
	public void stop() {
		this.stop = true;
	}
	
	public void run() {
		while(!stop) {
			try {
				System.out.println(ClientTCP.recevoirMessage(this.br));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
