package com.heylichen.solr.indexing.vo;


import com.alibaba.fastjson.annotation.JSONField;
import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

/**
 * Created by l on 2016/6/25.
 */
public class TweetMsg {
  @JSONField(name="id")
  @Field
  private Integer id;
  @Field("screen_name")
  @JSONField(name="screen_name")
  private String screenName;
  @Field
  @JSONField(name="type")
  private String type;
  @Field
  @JSONField(name="timestamp",format="yyyy-MM-dd'T'HH:mm:ss'Z'")
  private Date timestamp;
  @Field
  @JSONField(name="lang")
  private String lang;
  /*@Field("user_id")
  @JSONField(name="user_id")
  private String userId;*/
  @Field("favorites_count")
  @JSONField(name="favourites_count")
  private Integer favoritesCount;
  @Field
  @JSONField(name="text")
  private String text;
  /**/
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getScreenName() {
    return screenName;
  }

  public void setScreenName(String screenName) {
    this.screenName = screenName;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getLang() {
    return lang;
  }

  public void setLang(String lang) {
    this.lang = lang;
  }

/*  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }*/

  public Integer getFavoritesCount() {
    return favoritesCount;
  }

  public void setFavoritesCount(Integer favoritesCount) {
    this.favoritesCount = favoritesCount;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
