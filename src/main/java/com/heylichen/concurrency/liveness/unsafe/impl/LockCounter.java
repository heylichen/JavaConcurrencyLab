package com.heylichen.concurrency.liveness.unsafe.impl;

import com.heylichen.concurrency.liveness.unsafe.Counter;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockCounter implements Counter {
    private long counter = 0;
    private ReentrantReadWriteLock.WriteLock lock = new ReentrantReadWriteLock().writeLock();

    @Override
    public void increment() {
        lock.lock();
        try{
            counter++;
        }finally {
            lock.unlock();
        }
    }

    @Override
    public long getCounter() {
        return counter;
    }
}