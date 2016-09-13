package com.heylichen.concurrency.liveness.performance.map;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class ReadWriteFullMap<K, V> implements Map<K, V> {
  private final Map<K, V> map = new HashMap<>();
  private final ReadWriteLock lock = new ReentrantReadWriteLock();
  private final Lock r = lock.readLock();
  private final Lock w = lock.writeLock();


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
    r.lock();
    try {
      return map.get(key);
    } finally {
      r.unlock();
    }
  }

// Do the same for other read-only Map methods

  public V putIfAbsent(K key, V value) {
    w.lock();
    try {
      return map.putIfAbsent(key, value);
    } finally {
      w.unlock();
    }



    /*r.lock();
    try {
      if (map.containsKey(key)) {
        return map.get(key);
      }

      w.lock();
      try {
        return map.put(key, value);
      } finally {
        w.unlock();
      }

    } finally {
      r.unlock();
    }*/
  }

  @Override
  public void clear() {
    w.lock();
    try {
      map.clear();
    } finally {
      w.unlock();
    }
  }


  @Override
  public V remove(Object key) {
    w.lock();
    try {
      return map.remove(key);
    } finally {
      w.unlock();
    }

  }

  @Override
  public void putAll(Map<? extends K, ? extends V> m) {
    map.putAll(m);
  }

  @Override
  public Set<K> keySet() {
    return map.keySet();
  }

  @Override
  public Collection<V> values() {
    return map.values();
  }

  @Override
  public Set<Entry<K, V>> entrySet() {
    return map.entrySet();
  }

  @Override
  public boolean equals(Object o) {
    return map.equals(o);
  }

  @Override
  public int hashCode() {
    return map.hashCode();
  }

  @Override
  public V getOrDefault(Object key, V defaultValue) {
    return map.getOrDefault(key, defaultValue);
  }

  @Override
  public void forEach(BiConsumer<? super K, ? super V> action) {
    map.forEach(action);
  }

  @Override
  public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
    map.replaceAll(function);
  }


  @Override
  public boolean remove(Object key, Object value) {
    return map.remove(key, value);
  }

  @Override
  public boolean replace(K key, V oldValue, V newValue) {
    return map.replace(key, oldValue, newValue);
  }

  @Override
  public V replace(K key, V value) {
    return map.replace(key, value);
  }

  @Override
  public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
    return map.computeIfAbsent(key, mappingFunction);
  }

  @Override
  public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
    return map.computeIfPresent(key, remappingFunction);
  }

  @Override
  public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
    return map.compute(key, remappingFunction);
  }

  @Override
  public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
    return map.merge(key, value, remappingFunction);
  }

  @Override
  public int size() {
    return map.size();
  }

  @Override
  public boolean isEmpty() {
    return map.isEmpty();
  }

  @Override
  public boolean containsKey(Object key) {
    return map.containsKey(key);
  }

  @Override
  public boolean containsValue(Object value) {
    return map.containsValue(value);
  }
}