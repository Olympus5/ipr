package fr.istic.ipr.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;

public class Chat {
	private final static String EXCHANGE_NAME = "chat";
	
	public static void main(String[] argv) {
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = null;
		Channel channel = null;
		ExecutorService service = Executors.newFixedThreadPool(4);
		String message = "", nom = "", topic = "";
		
		if(argv.length < 2) {
			nom = "anonyme";
			
			if(argv.length < 1) {
				topic = "chat.mri";
			} else {
				topic = argv[0];
			}
		} else {
			topic = argv[0];
			nom = argv[1];
		}
		
		try {
			factory.setUri("amqp://lyfxesxf:aPjKUUBJuBdBcgqCeDg9z5O5Ptkt117Q@sheep.rmq.cloudamqp.com/lyfxesxf");
			connection = factory.newConnection();
			channel = connection.createChannel();
			
			channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
			
			
			//Reception du message
			service.execute(new MyRunnable(channel, EXCHANGE_NAME, topic));
			
			//Envoie de messages
			message = topic + "#" + nom + ">" + lireMessageClavier();
			
			while(!message.equals("FIN")) {
				channel.basicPublish(EXCHANGE_NAME, topic, null, message.getBytes("UTF-8"));
				message = topic + "#" + nom + ">" + lireMessageClavier();
			}
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
			
		}
	}
	
	public static void recevoirMessage(Consumer consumer, String queueName, Channel channel) throws IOException {
		channel.basicConsume(queueName, true, consumer);
	}
	
	public static String lireMessageClavier() throws IOException {
		BufferedReader  br= new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
		System.out.println("Entrez votre message: ");
		return br.readLine();
	}
}
