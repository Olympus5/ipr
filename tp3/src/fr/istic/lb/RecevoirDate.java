package fr.istic.lb;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class RecevoirDate {
	private static final String QUEUE_NAME = "file_date";

	public static void main(String[] argv) {
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = null;
		Channel channel = null;

		try {
			factory.setUri("amqp://lyfxesxf:aPjKUUBJuBdBcgqCeDg9z5O5Ptkt117Q@sheep.rmq.cloudamqp.com/lyfxesxf");
			connection = factory.newConnection();
			final Channel chan = channel = connection.createChannel();

			boolean durable = true;
			channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

			System.out.println(" [*] Waiting for messages. To exit press CTRL+C");


			channel.basicQos(1);

			final Consumer consumer = new DefaultConsumer(channel) {

				@Override
				public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
					String message = new String(body, "UTF-8");

					System.out.println(" [x] Received '" + message + "'");
					chan.basicAck(envelope.getDeliveryTag(), false);
				}
			};

			boolean autoAck = false;
			channel.basicConsume(QUEUE_NAME, autoAck, consumer);
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
		}
	}
}

