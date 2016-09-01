package com.heylichen.solr.jms.basic;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

/**
 * Created by lc on 2016/7/17.
 */
public class FileHelper {
  private static Logger logger = LoggerFactory.getLogger(FileHelper.class);

  public static String readAsString(String path) throws IOException {
    ClassPathResource clr = new ClassPathResource(path);
    return FileUtils.readFileToString(clr.getFile(), StandardCharsets.UTF_8);
  }

  public static <T> List<T> readAsList(String path, Class<T> clazz) {
    try {
      String json = readAsString(path);
      return JSON.parseArray(json, clazz);
    } catch (Exception e) {
      logger.error("parse error", e);
      return Collections.EMPTY_LIST;
    }

  }
}
