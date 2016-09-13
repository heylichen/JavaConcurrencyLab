package com.heylichen.concurrency.liveness.unsafe;


import com.heylichen.concurrency.liveness.unsafe.impl.AtomicCounter;
import com.heylichen.concurrency.liveness.unsafe.impl.CASCounter;
import com.heylichen.concurrency.liveness.unsafe.impl.LockCounter;
import com.heylichen.concurrency.liveness.unsafe.impl.SyncCounter;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Chen Li on 2016/9/13.
 */
public class UnsafePerTest {
  public static void main(String[] args) throws Exception {
    run(StupidCounter.class);
    run(SyncCounter.class);
    run(LockCounter.class);
    run(AtomicCounter.class);
    run(CASCounter.class);
  }


  private static <T extends Counter> void run(Class<T> clazz) throws Exception {
    System.out.println("---->" + clazz.getSimpleName());
    int NUM_OF_THREADS = 1000;
    int NUM_OF_INCREMENTS = 100000;
    CyclicBarrier barrier = new CyclicBarrier(NUM_OF_THREADS + 1);


    ExecutorService service = Executors.newFixedThreadPool(NUM_OF_THREADS);
    Counter counter = clazz.newInstance(); // creating instance of specific counter
    long before = System.currentTimeMillis();
    for (int i = 0; i < NUM_OF_THREADS; i++) {
      service.submit(new CounterClient(counter, NUM_OF_INCREMENTS,barrier));
    }
    barrier.await();
    barrier.await();


    service.shutdown();
    service.awaitTermination(1, TimeUnit.MINUTES);
    long after = System.currentTimeMillis();
    System.out.println("Counter result: " + counter.getCounter());
    System.out.println("Time passed in ms:" + (after - before));
  }
}
