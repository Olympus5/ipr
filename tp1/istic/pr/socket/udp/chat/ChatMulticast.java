/**
 * 
 */
package istic.pr.socket.udp.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Erwan IQUEL Mathieu LE CLEC'H
 * @version 1.0
 */
public class ChatMulticast {
	private static int port = 9999;
	private static DatagramPacket packet = null;
	private static MulticastSocket msocket = null;
	private static byte[] buf = new byte[1024];
	private static Executor service = Executors.newFixedThreadPool(2);
	private static String addr = "225.0.0.1";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String name, message;
		Runnable thread;
		
		if(args.length <= 0) {
			System.out.println("Votre nom: ");
			name = (new Scanner(System.in)).nextLine();
		} else {
			name = args[0];
		}
		
		try {
			ChatMulticast.msocket = new MulticastSocket(ChatMulticast.port);
			ChatMulticast.msocket.joinGroup(InetAddress.getByName(ChatMulticast.addr));
			thread = new TraiterMessage(ChatMulticast.msocket);
			
			do {
				message = ChatMulticast.lireMessageAuClavier();
				ChatMulticast.envoyerMessage(ChatMulticast.msocket, name + ">" + message);
				service.execute(thread);
			} while(message != "FIN");
			
			((TraiterMessage) thread).stop();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(ChatMulticast.msocket != null) {
				try {
					ChatMulticast.msocket.leaveGroup(InetAddress.getByName(ChatMulticast.addr));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				ChatMulticast.msocket.close();
			}
		}
	}

	public static void envoyerMessage(MulticastSocket s, String message) throws IOException {		
		ChatMulticast.packet = new DatagramPacket(message.getBytes(), message.length(), InetAddress.getByName(ChatMulticast.addr), ChatMulticast.port);
		s.send(ChatMulticast.packet);
	}

	public static String recevoirMessage(MulticastSocket s) throws IOException {
		String result;
		
		ChatMulticast.packet = new DatagramPacket(ChatMulticast.buf, ChatMulticast.buf.length);
		s.receive(ChatMulticast.packet);
		
		result = new String(ChatMulticast.buf, 0, ChatMulticast.buf.length);
		
		//On vide le buffer
		ChatMulticast.buf = new byte[1024];
		
		return result;
	}
	
	public static String lireMessageAuClavier() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Votre message: ");
		
		return br.readLine();
	}

}
