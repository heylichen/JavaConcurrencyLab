package com.heylichen.solr.jms.basic;

/**
 * Created by lc on 2016/6/14.
 */
public class Person {
  private String name;
  private SexEnums sex;

  public Person(String name, SexEnums sex) {
    this.name = name;
    this.sex = sex;
  }

  public Person() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public SexEnums getSex() {
    return sex;
  }

  public void setSex(SexEnums sex) {
    this.sex = sex;
  }
}
