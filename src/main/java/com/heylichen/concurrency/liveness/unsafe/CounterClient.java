package com.heylichen.concurrency.liveness.unsafe;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class CounterClient implements Runnable {
  private Counter c;
  private int num;
  private CyclicBarrier barrier;

  public CounterClient(Counter c, int num, CyclicBarrier barrier) {
    this.c = c;
    this.num = num;
    this.barrier = barrier;
  }

  @Override
  public void run() {
    try {
      barrier.await();
      for (int i = 0; i < num; i++) {
        c.increment();
      }
      barrier.await();
    } catch (BrokenBarrierException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}