package com.heylichen.amq.jms.basic;

import com.heylichen.amq.jms.connectors.ConnectorPublisher;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by lichen2 on 2016/6/10.
 * test pub-sub using different connectors
 */
public class DropSessionTest {

  private static final Logger logger = LoggerFactory.getLogger(DropSessionTest.class);
  private ExecutorService exec = Executors.newFixedThreadPool(6);

  /**
   * activemq support various connectors.
   *
   * @throws Exception
   */
  @Test
  public void variousConnectors() throws Exception {
    Map<String, String> urls = new HashMap<>();
    urls.put("tcp", "tcp://localhost:61616");

    String url = urls.get("tcp");
    ConnectorPublisher publisher = new ConnectorPublisher(url);
    DropSessionSubscriber subscriber = new DropSessionSubscriber(url);
    exec.submit(publisher);
    exec.submit(subscriber);
    Thread.sleep(9000);
  }

}
