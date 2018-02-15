package fr.istic.date;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class EnvoyerDate {

	private static final String EXCHANGE_NAME = "date";

	public static void main(String[] argv) {
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = null;
		Channel channel = null;
		
		try {
			factory.setUri("amqp://lyfxesxf:aPjKUUBJuBdBcgqCeDg9z5O5Ptkt117Q@sheep.rmq.cloudamqp.com/lyfxesxf");
			
			connection = factory.newConnection();
			channel = connection.createChannel();
			boolean stop = false;

			channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

			while(!stop) {
				String message = getDate();

				channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
				System.out.println(" [x] Sent '" + message + "'");
			}
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} finally {
			try {
				if(channel != null) {
					channel.close();
				}
				
				if(connection != null) {
					connection.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
		
		}
	}

	private static String getDate(){
		return LocalDateTime.now().toString();
	}

	private static String joinStrings(String[] strings, String delimiter) {
		int length = strings.length;
		if (length == 0) return "";
		StringBuilder words = new StringBuilder(strings[0]);
		for (int i = 1; i < length; i++) {
			words.append(delimiter).append(strings[i]);
		}
		return words.toString();
	}
}
