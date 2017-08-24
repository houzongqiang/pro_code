package com.hou.zq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MessageLisener implements MessageListener {
	private static final String url = "tcp://localhost:61616";
	private static final String QUEUE_NAME = "G2Queue";
	
	public void receiveMessage() {
		Connection connection = null;
		try {
			try {
				ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
				connection = connectionFactory.createConnection();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue(QUEUE_NAME);
			// 消息接收者，也就是消费者
			MessageConsumer consumer = session.createConsumer(destination);
			consumer.setMessageListener(new MessageLisener());
			//consumeMessagesAndClose(connection, session, consumer);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
	@Override
	public void onMessage(Message message) {
		System.out.println("topic收到的消息："+message);
        TextMessage textmessage = (TextMessage)message;
        try {
            System.out.println("message："+textmessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
		
	}
	public static void main(String args[]) {
		MessageLisener rm = new MessageLisener();
		rm.receiveMessage();
	}
}
