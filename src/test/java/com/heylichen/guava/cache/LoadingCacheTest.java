package com.heylichen.guava.cache;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lc on 2016/8/13.
 */
public class LoadingCacheTest {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Test
  public void test() {
    LoadingCache<Integer, Person> personCache = CacheBuilder.newBuilder().maximumSize(20)
        .build(new PersonCacheLoader());
    try {
      for (int i = 0; i < 10; i++) {
        Person p = personCache.get(i);
        logger.info("{}", JSON.toJSONString(p));
      }

      List<Integer> ids = new ArrayList<>();

      for (int i = 0; i < 20; i++) {
        ids.add(i);
      }
      Map<Integer, Person> persons = personCache.getAll(ids);
      logger.info("{}", JSON.toJSONString(persons));
    } catch (Exception e) {
      e.printStackTrace();
    }


  }
}
