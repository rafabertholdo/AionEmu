package com.aionemu.commons.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import javolution.util.FastMap;

public final class SingletonMap<K, V> implements Map<K, V> {
  private boolean initialized = false;
  private Map<K, V> map = AECollections.emptyMap();

  private boolean shared = false;

  private void init() {
    if (!this.initialized) {
      synchronized (this) {

        if (!this.initialized) {

          this.map = (Map<K, V>) (new FastMap()).setShared(this.shared);
          this.initialized = true;
        }
      }
    }
  }

  public SingletonMap<K, V> setShared() {
    this.shared = true;

    synchronized (this) {

      if (this.initialized) {
        ((FastMap) this.map).setShared(true);
      }
    }

    return this;
  }

  public void clear() {
    this.map.clear();
  }

  public boolean containsKey(Object key) {
    return this.map.containsKey(key);
  }

  public boolean containsValue(Object value) {
    return this.map.containsValue(value);
  }

  public Set<Map.Entry<K, V>> entrySet() {
    return this.map.entrySet();
  }

  public V get(Object key) {
    return this.map.get(key);
  }

  public boolean isEmpty() {
    return this.map.isEmpty();
  }

  public Set<K> keySet() {
    return this.map.keySet();
  }

  public V put(K key, V value) {
    init();

    return this.map.put(key, value);
  }

  public void putAll(Map<? extends K, ? extends V> m) {
    init();

    this.map.putAll(m);
  }

  public V remove(Object key) {
    return this.map.remove(key);
  }

  public int size() {
    return this.map.size();
  }

  public Collection<V> values() {
    return this.map.values();
  }

  public String toString() {
    return super.toString() + "-" + this.map.toString();
  }
}
