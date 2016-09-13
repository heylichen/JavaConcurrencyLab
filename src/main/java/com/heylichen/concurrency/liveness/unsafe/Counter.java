package com.heylichen.concurrency.liveness.unsafe;

public interface Counter {
    void increment();
    long getCounter();
}