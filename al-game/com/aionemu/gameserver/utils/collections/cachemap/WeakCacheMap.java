package com.aionemu.gameserver.utils.collections.cachemap;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import org.apache.log4j.Logger;

class WeakCacheMap<K, V> extends AbstractCacheMap<K, V> implements CacheMap<K, V> {
  private static final Logger log = Logger.getLogger(WeakCacheMap.class);

  private class Entry extends WeakReference<V> {
    private K key;

    Entry(K key, V referent, ReferenceQueue<? super V> q) {
      super(referent, q);
      this.key = key;
    }

    K getKey() {
      return this.key;
    }
  }

  WeakCacheMap(String cacheName, String valueName) {
    super(cacheName, valueName, log);
  }

  protected synchronized void cleanQueue() {
    Entry en = null;
    while ((en = (Entry) this.refQueue.poll()) != null) {

      K key = en.getKey();
      if (log.isDebugEnabled())
        log.debug(this.cacheName + " : cleaned up " + this.valueName + " for key: " + key);
      this.cacheMap.remove(key);
    }
  }

  protected Reference<V> newReference(K key, V value, ReferenceQueue<V> vReferenceQueue) {
    return new Entry(key, value, vReferenceQueue);
  }
}
