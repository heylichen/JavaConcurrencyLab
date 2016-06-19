package com.heylichen.amq.jms.basic;

/**
 * Created by lc on 2016/6/14.
 */
public enum SexEnums {
  MALE(1, "男"), FEMALE(2, "女");
  private int value;
  private String name;

  SexEnums(int value, String name) {
    this.value = value;
    this.name = name;
  }
}
