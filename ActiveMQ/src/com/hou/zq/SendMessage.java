package com.hou.zq;

import java.util.Random;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class SendMessage {
	private static final String url = "tcp://localhost:61616";
	private static final String QUEUE_NAME = "G2Queue";

	public void sendMessage() throws JMSException {
		// JMS �ͻ��˵�JMSProvider ������
		Connection connection = null;
		try {
			// ���ӹ�����JMS ������������
			// ����ConnectionFactoryʵ�����󣬴˴�����ActiveMq��ʵ��jar
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
			connection = (Connection) connectionFactory.createConnection();
			// ��������
			connection.start();
			// Session�����ͻ������Ϣ���߳�
			// ��ȡsession
			Session session = (Session) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			// ��Ϣ��Ŀ�ĵأ���Ϣ���͵��Ǹ�����
			Destination destination = session.createQueue(QUEUE_NAME);
			//session.createTopic("");  ����TOPIC
			// MessageProducer����Ϣ�����ߣ������ߣ�
			// ������Ϣ������
			MessageProducer producer = session.createProducer(destination);
			// �����Ƿ�־û�
			// DeliveryMode.NON_PERSISTENT�����־û�
			// DeliveryMode.PERSISTENT���־û�
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);

			String msg = "";
			int i = 0;
			do {
				msg = "��" + i + "�η��͵���Ϣ��" + new Random();
				TextMessage message = session.createTextMessage(msg);
				Thread.sleep(1000);
				// ������Ϣ��Ŀ�ĵط�
				producer.send(message);
				System.out.println("������Ϣ��" + msg);
				i++;
			} while (i < 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SendMessage sndMsg = new SendMessage();
		try {
			sndMsg.sendMessage();
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}
}