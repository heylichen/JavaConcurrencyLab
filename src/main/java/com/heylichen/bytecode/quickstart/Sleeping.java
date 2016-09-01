package com.heylichen.bytecode.quickstart;

/**
 * Created by lc on 2016/7/12.
 */
public class Sleeping {
  public void randomSleep() throws InterruptedException {

    // randomly sleeps between 500ms and 1200s
    long randomSleepDuration = (long) (500 + Math.random() * 700);
    System.out.printf("Sleeping for %d ms ..\n", randomSleepDuration);
    Thread.sleep(randomSleepDuration);
  }
}
