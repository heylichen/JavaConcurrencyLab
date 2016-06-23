package com.helichen.camel.quickstart;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;

/**
 * Created by lc on 2016/6/21.
 */
public class FileCopyCamelQuickstart {
  @Test
  public void copy() throws Exception{
    CamelContext context = new DefaultCamelContext();
    context.addRoutes(new RouteBuilder() {
      @Override
      public void configure() throws Exception {
        from("file:D:/programs_data/temp/inbox?noop=true").to("file:D:/programs_data/temp/outbox");
      }
    });
    context.start();
    Thread.sleep(10000);
    context.stop();
  }
}
