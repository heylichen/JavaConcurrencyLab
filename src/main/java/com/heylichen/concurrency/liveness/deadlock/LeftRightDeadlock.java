package com.heylichen.concurrency.liveness.deadlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

// Warning: deadlock-prone!
public class LeftRightDeadlock {
  private static Logger logger = LoggerFactory.getLogger(LeftRightDeadlock.class);
  private final Object left = new Object();
  private final Object right = new Object();
  private Random rand = new Random();

  public void leftRight() {
    synchronized (left) {
      synchronized (right) {
        doSomething();
      }
    }
  }

  public void rightLeft() {
    synchronized (right) {
      synchronized (left) {
        doSomethingElse();
      }
    }
  }

  private void doSomething() {
    int duration = rand.nextInt(50);
    try {
      Thread.sleep(duration);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void doSomethingElse() {
    int duration = rand.nextInt(50);
    try {
      Thread.sleep(duration);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    LeftRightDeadlock lock = new LeftRightDeadlock();
    int count = 10;


    logger.info("main start");


    Thread leftRight = new Thread() {
      @Override
      public void run() {
        for (int i = 0; i < count; i++) {
          lock.leftRight();
        }
      }
    };
    Thread rightLeft = new Thread() {
      @Override
      public void run() {
        for (int i = 0; i < count; i++) {
          lock.rightLeft();
        }
      }
    };
    leftRight.start();
    rightLeft.start();
    logger.info("main end");
  }
}