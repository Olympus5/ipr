package fr.istic.date.topic;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class EnvoyerDate {

	private static final String EXCHANGE_NAME = "date_topic";
	private static final String LOCAL_KEY = "date.local";
	private static final String UTC_KEY = "date.utc";

	public static void main(String[] argv) {
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = null;
		Channel channel = null;

		try {
			factory.setUri("amqp://lyfxesxf:aPjKUUBJuBdBcgqCeDg9z5O5Ptkt117Q@sheep.rmq.cloudamqp.com/lyfxesxf");

			connection = factory.newConnection();
			channel = connection.createChannel();
			boolean stop = false;

			channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

			String date = getDate();
			String dateUTC = getDateUTC();

			channel.basicPublish(EXCHANGE_NAME, LOCAL_KEY, null, date.getBytes("UTF-8"));
			System.out.println(" [x] Sent '" + date + "'");
			

			channel.basicPublish(EXCHANGE_NAME, UTC_KEY, null, dateUTC.getBytes("UTF-8"));
			System.out.println(" [x] Sent '" + dateUTC + "'");
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

	private static String getDateUTC() {
		return LocalDateTime.now(Clock.systemUTC()).toString();
	}
}
