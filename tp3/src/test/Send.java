package test;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;

public class Send {
	private final static String QUEUE_NAME = "hello";
	
	public static void main(String[] args) {
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = null;
		Channel channel = null;
		
		try {
			factory.setUri("amqp://lyfxesxf:aPjKUUBJuBdBcgqCeDg9z5O5Ptkt117Q@sheep.rmq.cloudamqp.com/lyfxesxf");
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			String message = "Hello world !";
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} finally {
			if(channel != null) {
				try {
					channel.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					e.printStackTrace();
				}
			}
			
			if(connection != null) {
				try {
					connection.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
