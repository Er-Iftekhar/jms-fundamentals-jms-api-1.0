package com.app;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstQueue {
    public static void main(String[] args) {
        // Initial context is to load all the JMS properties mentioned in the application.properties/yaml file
        InitialContext initialContext = null;
        Connection connection = null;
    try {
        initialContext = new InitialContext();
        // Need a connection factory to create connections
        ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
        // Need a connection to create a session
        connection = cf.createConnection();
        // Need a session to create either producer or consumer
        Session session = connection.createSession();
        // Queue is needed as we are developing P2P messaging model
        Queue queue = (Queue) initialContext.lookup("queue/myQueue");
        // Producer to send messages
        MessageProducer producer = session.createProducer(queue);
            TextMessage message = session.createTextMessage("Creator of own destiny");
            producer.send(message);
            System.out.println("Message sent: " + message.getText());
        // Consumer to receive messages
        MessageConsumer consumer = session.createConsumer(queue);
            connection.start();
            TextMessage messageReceived = (TextMessage) consumer.receive(5000);
            System.out.println("Message received: " + messageReceived.getText());
        } catch (NamingException | JMSException e) {
            throw new RuntimeException(e);
        } finally { // Close all the open resources for resource management
            if (initialContext != null) {
                try {
                    initialContext.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                   e.printStackTrace();
                }
            }
        }
    }
}
