package com.heylichen.concurrency.liveness.unsafe.impl;

import com.heylichen.concurrency.liveness.unsafe.Counter;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicCounter implements Counter {
    AtomicLong counter = new AtomicLong(0);

    @Override
    public void increment() {
        counter.incrementAndGet();
    }

    @Override
    public long getCounter() {
        return counter.get();
    }
}