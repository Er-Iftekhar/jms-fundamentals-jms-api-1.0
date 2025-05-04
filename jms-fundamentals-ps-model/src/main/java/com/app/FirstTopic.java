package com.app;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstTopic {
    public static void main(String[] args) throws NamingException, JMSException {
        InitialContext initialContext = new InitialContext();
        Topic topic = (Topic) initialContext.lookup("topic/myTopic");

        ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");

        Connection connection = connectionFactory.createConnection();

        Session session = connection.createSession();

        MessageProducer producer = session.createProducer(topic);

        MessageConsumer consumer1 = session.createConsumer(topic);
        MessageConsumer consumer2 = session.createConsumer(topic);

        TextMessage senderMessage = session.createTextMessage("I can do it");
        producer.send(senderMessage);

        connection.start();

        TextMessage receivedMessage1 = (TextMessage) consumer1.receive();
        System.out.println("Message received1: " + receivedMessage1.getText());

        TextMessage receivedMessage2 = (TextMessage) consumer2.receive();
        System.out.println("Message received2: " + receivedMessage2.getText());

        connection.close();
        initialContext.close();
    }
}
