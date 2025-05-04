package com.app;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstTopic {
    public static void main(String[] args) throws NamingException, JMSException {
        // Initial context to load the properties from properties file
        InitialContext initialContext = new InitialContext();
        // Create a topic from initial context
        Topic topic = (Topic) initialContext.lookup("topic/myTopic");
        // Create connectionFatctory to create connections
        ConnectionFactory connectionFactory = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
        // Create connections to create sessions
        Connection connection = connectionFactory.createConnection();
        // Create sessions to create producer and consumers
        Session session = connection.createSession();
        // Producer to send message
        MessageProducer producer = session.createProducer(topic);
        // Multiple consumers to consume the message
        MessageConsumer consumer1 = session.createConsumer(topic);
        MessageConsumer consumer2 = session.createConsumer(topic);
        // Message sent my the producer
        TextMessage senderMessage = session.createTextMessage("I can do it");
        producer.send(senderMessage);
        // This states that consumer is ready to receive messages
        connection.start();
        // Parsing message received by consumer 1
        TextMessage receivedMessage1 = (TextMessage) consumer1.receive();
        System.out.println("Message received1: " + receivedMessage1.getText());
        // Parsing message received by consumer 2
        TextMessage receivedMessage2 = (TextMessage) consumer2.receive();
        System.out.println("Message received2: " + receivedMessage2.getText());
        // Closing resources
        connection.close();
        initialContext.close();
    }
}
