package com.heylichen.amq.jms.basic;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lc on 2016/6/14.
 */
public class FastJsonTest {
  Logger logger = LoggerFactory.getLogger(FastJsonTest.class);

  @Test
  public void enumTest() {
    Person p = new Person("张三", SexEnums.FEMALE);

    String json = JSON.toJSONString(p);
    logger.info("json:{}", json);

  }
}
