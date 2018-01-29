package istic.pr.socket.address;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class AfficheInterfaces {
	public static void main(String[] args) {
		StringBuilder sbNis = new StringBuilder();
		StringBuilder sbIas = new StringBuilder();

		  
		Enumeration<NetworkInterface> nis = null;
		Enumeration<InetAddress> ias = null;
		NetworkInterface ni = null;
		InetAddress ia = null;
		
		try {
			nis = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		if(nis != null) {
			while(nis.hasMoreElements()) {
				ni = nis.nextElement();
				sbNis.append(ni.getName() + ": " + ni.getDisplayName() + "\n");
				ias = ni.getInetAddresses();
				
				while(ias.hasMoreElements()) {
					ia = ias.nextElement();
					sbIas.append("->" + ia.getHostAddress() + "\n");
				}
			}
		}
		
		System.out.println(sbNis + "" + sbIas);
	}
}
