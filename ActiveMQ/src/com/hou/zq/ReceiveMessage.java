package com.hou.zq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ReceiveMessage {
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
			// ��Ϣ�����ߣ�Ҳ����������
			MessageConsumer consumer = session.createConsumer(destination);

			consumeMessagesAndClose(connection, session, consumer);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	/**
	 * ���պ͹ر���Ϣ����������Ϣ����Ϊclose�򣬹ر�����
	 * 
	 * @param connection
	 *            JMS �ͻ��˵�JMSProvider ������
	 * @param session
	 *            ���ͻ������Ϣ���߳�
	 * @param consumer
	 *            ��Ϣ���ն���
	 * @throws JMSException
	 * @auther <ahref="mailto:252909344@qq.com">�Ե�</a> Apr 8, 2013 10:31:55 AM
	 */
	protected void consumeMessagesAndClose(Connection connection, Session session, MessageConsumer consumer)
			throws JMSException {
		do {
			Message message = consumer.receive(1000);
			if ("close".equals(message)) {
				consumer.close();
				session.close();
				connection.close();
			}
			if (message != null) {
				onMessage(message);
			}
		} while (true);

	}

	public void onMessage(Message message) {
		try {
			if (message instanceof TextMessage) {
				TextMessage txtMsg = (TextMessage) message;
				String msg = txtMsg.getText();
				System.out.println("Received:" + msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String args[]) {
		ReceiveMessage rm = new ReceiveMessage();
		rm.receiveMessage();
	}
}