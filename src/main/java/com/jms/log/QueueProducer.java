package com.jms.log;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueProducer {
  public static void main(String[] args) throws NamingException, JMSException {
    InitialContext context = new InitialContext();
    ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
    Connection connection = factory.createConnection("admin", "admin");
    connection.start();

    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    Destination queue = (Destination) context.lookup("LOG");

    MessageProducer producer = session.createProducer(queue);

    // for (int i = 0; i < 100; i++) {
    //   Message message = session.createTextMessage("<pedido><id>"+i+"</id></pedido>");
    //   producer.send(message);
    // }
    Message message = session.createTextMessage("WARN | Apache ActiveMQ 5.12.0");
    producer.send(message, DeliveryMode.NON_PERSISTENT, 7, 50000);
    session.close();
    connection.close();
    context.close();
  }
}
