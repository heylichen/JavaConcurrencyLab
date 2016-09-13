package com.heylichen.concurrency.liveness.unsafe.impl;

import com.heylichen.concurrency.liveness.unsafe.Counter;

public class StupidCounter implements Counter {
    private long counter = 0;

    @Override
    public void increment() {
        counter++;
    }

    @Override
    public long getCounter() {
        return counter;
    }
}