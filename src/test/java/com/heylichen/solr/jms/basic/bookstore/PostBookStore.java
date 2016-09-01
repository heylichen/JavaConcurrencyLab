package com.heylichen.solr.jms.basic.bookstore;

import com.heylichen.solr.indexing.vo.bookstore.BookStore;
import com.heylichen.solr.jms.basic.FileHelper;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Created by lc on 2016/6/27.
 */
public class PostBookStore {
  private Logger logger = LoggerFactory.getLogger(PostBookStore.class);
  private int BATCH = 1;

  @Test
  public void testJSON(){
    List<BookStore> list = FileHelper.readAsList("data.json.solr.bookstore/bookStores.json", BookStore.class);

    String url = "http://localhost:8983/solr/BookStore";
    SolrClient solrClient = new HttpSolrClient.Builder(url).build();


    try {
      solrClient.addBeans(list);
      solrClient.commit();
    } catch (SolrServerException | IOException e) {
      e.printStackTrace();
    }
    logger.info("ok");
  }


}
