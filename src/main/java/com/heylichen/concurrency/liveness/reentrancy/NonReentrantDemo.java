package com.heylichen.concurrency.liveness.reentrancy;

import EDU.oswego.cs.dl.util.concurrent.Mutex;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lc on 2016/9/4.
 * an example show the difference between reentrant lock and non-reentrant mutex.
 *
 */
public class NonReentrantDemo {


  private static class Factorial {
    //this is a nonreentrant mutex implemented by Doug Lea
    private Mutex mutex = new Mutex();


    public int calculate(int num) {
      if (num <= 1) {
        return 1;
      }
      try {
        mutex.acquire();
        try {
          return num * calculate(num - 1);
        } finally {
          mutex.release();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
        return 0;
      }
    }
  }

  private static class FactorialWithReentrantLock {
    private Lock mutex = new ReentrantLock();


    public int calculate(int num) {
      if (num <= 1) {
        return 1;
      }
      try {
        mutex.lockInterruptibly();
        try {
          return num * calculate(num - 1);
        } finally {
          mutex.unlock();
        }
      } catch (InterruptedException e) {
        e.printStackTrace();
        return 0;
      }
    }
  }

  public static void main(String[] args) {
    int param = 5;
    FactorialWithReentrantLock fac1 = new FactorialWithReentrantLock();
    //this will not cause deadlock, because the lock held is reentrant
    int value = fac1.calculate(param);
    //this should print
    System.out.println("fac("+param+")="+value);

    Factorial fac = new Factorial();
    //this will cause deadlock
    int value1 = fac.calculate(param);
    System.out.println("fac("+param+")="+value1);
  }

}
