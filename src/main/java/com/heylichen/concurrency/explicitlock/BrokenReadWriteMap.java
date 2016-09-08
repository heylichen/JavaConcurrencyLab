package com.heylichen.concurrency.explicitlock;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * I think the BrokenReadWriteMap is not thread safe.
 * volatile can only guarentee visibility, but not operation atomicity.
 * Map's put method is not atomic, when put operation is in half, a calling to
 * get will see the map in a middle-way state.
 *
 * @param <K>
 * @param <V>
 */
public class BrokenReadWriteMap<K, V> {
  private volatile Map<K, V> map;
  private final ReadWriteLock lock = new ReentrantReadWriteLock();
  private final Lock w = lock.writeLock();

  public BrokenReadWriteMap(Map<K, V> map) {
    this.map = map;
  }

  public V put(K key, V value) {
    w.lock();
    try {
      return map.put(key, value);
    } finally {
      w.unlock();
    }
  }

  // Do the same for remove(), putAll(), clear()
  public V get(Object key) {
    return map.get(key);
  }
// Do the same for other read-only Map methods
}