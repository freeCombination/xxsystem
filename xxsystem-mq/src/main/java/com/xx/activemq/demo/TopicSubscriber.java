package com.dqgb.activemq.demo;
import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;


public class TopicSubscriber {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		        try {
					Connection connection = factory.createConnection();
					connection.start();
					Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
					Topic topic = session.createTopic("myTopic.messages");
					MessageConsumer consumer = session.createConsumer(topic);
					consumer.setMessageListener(new MessageListener() {
					    public void onMessage(Message message) {
					        TextMessage tm = (TextMessage) message;
					        try {
					            System.out.println("Received message: " + tm.getText());
					        } catch (JMSException e) {
					            e.printStackTrace();
					        }
					    }
					});
				} catch (JMSException e) {
					e.printStackTrace();
				}
	}

}
