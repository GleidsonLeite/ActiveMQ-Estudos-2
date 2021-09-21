package com.jms.log;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;


public class QueueConsumer {
    public static void main(String[] args) throws Exception {
        System.setProperty("org.apache.activemq.SERIALIZABLE_PACKAGES", "*");
        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        Connection connection = factory.createConnection("admin", "admin");
        connection.start();

        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination queue = (Destination) context.lookup("LOG");

        MessageConsumer consumer = session.createConsumer(queue);

        MessageListener messageListener = new MessageListener() {

            @Override
            public void onMessage(Message message) {
                // TODO Auto-generated method stub
                TextMessage textMessage = (TextMessage) message;
                try {
                    // message.acknowledge();
                    System.out.println(textMessage.getText());
                    // session.commit();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }

        };
        consumer.setMessageListener(messageListener);

        new Scanner(System.in).nextLine();

        session.close();
        connection.close();
        context.close();
    }
}
