package istic.pr.socket.tcp.chat;

import java.net.Socket;

public class TraiteUnClient implements Runnable {
	private Socket socketClient;
	private String charset;

	public TraiteUnClient(Socket socketClient, String charset) {
		this.socketClient = socketClient;
		this.charset = charset;
	}

	@Override
	public void run() {
		if(this.socketClient != null && this.charset != null) {
			System.out.println("Handle a client...");
			//Handle
			ServeurTCP.traiterSocketCliente(this.socketClient, this.charset);
		}
	}
}
