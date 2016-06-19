package com.heylichen.amq.jms.basic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by lc on 2016/6/10.
 */
public class DropSessionConsumer {
  private Connection connection = null;
  private MessageProducer producer = null;
  private String connectorUrI;
  private MessageConsumer consumer = null;
  private String topic;
  private boolean durable;
  private MessageListener listener;

  public DropSessionConsumer(String connectorUrI, String topic, boolean durable, MessageListener listener) {
    this.connectorUrI = connectorUrI;
    this.topic = topic;
    this.durable = durable;
    this.listener = listener;

    this.connectorUrI = connectorUrI;
    this.topic = topic;
    try {
      // Create a ConnectionFactory
      ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(connectorUrI);

      // Create a Connection
      connection = connectionFactory.createConnection();
      connection.setClientID( "TopicProducerClient");
      connection.start();

      // Create a Session
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

      // Create the destination (Topic or Queue)
      Topic destination = session.createTopic(topic);

      // Create a MessageProducer from the Session to the Topic or Queue
      if (durable) {
        consumer = session.createDurableSubscriber(destination, topic + ".Durable.Consumer");
      } else {
        consumer = session.createConsumer(destination);
      }
      consumer.setMessageListener(listener);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void close(){
    if (connection != null) {
      try {
        connection.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
