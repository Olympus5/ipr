package fr.istic.ipr.chat;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class MyRunnable implements Runnable {

	private Channel channel;
	private String exchange;
	private String topic;
	private boolean stop;
	
	public MyRunnable(Channel channel, String exchange, String topic) {
		this.channel = channel;
		this.exchange = exchange;
		this.topic = topic;
		this.stop = false;
	}
	
	public void stop() {
		this.stop = true;
	}

	@Override
	public void run() {
		String queueName;
		
		try {
			queueName = this.channel.queueDeclare().getQueue();
			channel.queueBind(queueName, this.exchange, this.topic);
			
			Consumer consumer = new DefaultConsumer(this.channel) {
				@Override
			      public void handleDelivery(String consumerTag, Envelope envelope,
			                                 AMQP.BasicProperties properties, byte[] body) throws IOException {
			        String message = new String(body, "UTF-8");
			        System.out.println(message);
			      }
			};
			
			while(!stop) {
				try {
					Chat.recevoirMessage(consumer, queueName, this.channel);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
