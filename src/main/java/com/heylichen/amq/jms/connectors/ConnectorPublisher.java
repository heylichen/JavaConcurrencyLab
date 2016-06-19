package com.heylichen.amq.jms.connectors;

import com.heylichen.amq.jms.commons.TopicProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;

/**
 * Created by lc on 2016/6/10.
 */
public class ConnectorPublisher implements Runnable {
  private static final Logger logger = LoggerFactory.getLogger(TopicProducer.class);
  private String url;
  public static final String TOPIC = "Connector.Topic";
  private int count = 5;


  public ConnectorPublisher(String url) {
    logger.info("using url {}", url);
    this.url = url;
  }

  public ConnectorPublisher(String url, int count) {
    this.url = url;
    this.count = count;
  }

  @Override
  public void run() {
    TopicProducer publisher = new TopicProducer(url, TOPIC);
    try {
      for (int i = 0; i < count; i++) {
        String text = "msg" + i;
        publisher.sendMsg(text);
        Thread.sleep(1000);
      }
    } catch (JMSException |InterruptedException e) {
      logger.error("error run.", e);
    }

    publisher.close();
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }


}
