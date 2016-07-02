package com.heylichen.solr.jms.basic;

import com.heylichen.solr.indexing.vo.TweetMsg;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lc on 2016/6/27.
 */
public class PostTweetTest {
  private Logger logger = LoggerFactory.getLogger(PostTweetTest.class);
  private int BATCH = 100;

  @Test
  public void batchPost() {
    batchPost(BATCH, 1);
    try{
      logger.info("sleep");
      Thread.sleep(4000);
      logger.info("wake up");
    }catch (Exception e){
      e.printStackTrace();
    }

    batchPost(BATCH, 1 + BATCH);
  }

  @Test
  public void doCommit() throws Exception{
    String url = "http://localhost:8983/solr/TweetData";
    SolrClient solrClient = new HttpSolrClient.Builder(url).build();
    solrClient.commit();
  }

  @Test
  public void deleteAll() {
    int count = BATCH * 2;
    String url = "http://localhost:8983/solr/TweetData";
    SolrClient solrClient = new HttpSolrClient.Builder(url).build();
    List<String> ids = new ArrayList<>();
    try {
      for (int i = 0; i < count; i++) {
        ids.add(String.valueOf(1 + i));
      }
      solrClient.deleteById(ids);
      solrClient.commit();
    } catch (SolrServerException | IOException e) {
      e.printStackTrace();
    }
  }

  private void batchPost(int batch, int startId) {
    String url = "http://localhost:8983/solr/TweetData";
    SolrClient solrClient = new HttpSolrClient.Builder(url).build();
   /* deleteAll();
    logger.info("deleted!");*/
    List<TweetMsg>
        tweetList = new ArrayList<>();
    try {
      for (int i = 0; i < batch; i++) {
        TweetMsg msg = new TweetMsg();
        msg.setFavorites(10);
        msg.setLang("eng");
        msg.setScreenName("张三2");
        msg.setText("Today is a good day!");
        msg.setTimestamp(new Date());
        msg.setUserId("1");
        msg.setType("post");
        msg.setId(startId + i);
        solrClient.addBean(msg);
      }

      // solrClient.commit();
    } catch (SolrServerException | IOException e) {
      e.printStackTrace();
    }
  }
}
