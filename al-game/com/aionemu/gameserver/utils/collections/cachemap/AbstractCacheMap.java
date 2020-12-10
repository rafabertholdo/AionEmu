package com.aionemu.gameserver.utils.collections.cachemap;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;




























abstract class AbstractCacheMap<K, V>
  implements CacheMap<K, V>
{
  private final Logger log;
  protected final String cacheName;
  protected final String valueName;
  protected final Map<K, Reference<V>> cacheMap = new HashMap<K, Reference<V>>();
  
  protected final ReferenceQueue<V> refQueue = new ReferenceQueue<V>();





  
  AbstractCacheMap(String cacheName, String valueName, Logger log) {
    this.cacheName = "#CACHE  [" + cacheName + "]#  ";
    this.valueName = valueName;
    this.log = log;
  }



  
  public void put(K key, V value) {
    cleanQueue();
    
    if (this.cacheMap.containsKey(key)) {
      throw new IllegalArgumentException("Key: " + key + " already exists in map");
    }
    Reference<V> entry = newReference(key, value, this.refQueue);
    
    this.cacheMap.put(key, entry);
    
    if (this.log.isDebugEnabled()) {
      this.log.debug(this.cacheName + " : added " + this.valueName + " for key: " + key);
    }
  }


  
  public V get(K key) {
    cleanQueue();
    
    Reference<V> reference = this.cacheMap.get(key);
    
    if (reference == null) {
      return null;
    }
    V res = reference.get();
    
    if (res != null && this.log.isDebugEnabled()) {
      this.log.debug(this.cacheName + " : obtained " + this.valueName + " for key: " + key);
    }
    return res;
  }


  
  public boolean contains(K key) {
    cleanQueue();
    return this.cacheMap.containsKey(key);
  }

  
  protected abstract void cleanQueue();

  
  public void remove(K key) {
    this.cacheMap.remove(key);
  }
  
  protected abstract Reference<V> newReference(K paramK, V paramV, ReferenceQueue<V> paramReferenceQueue);
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\collections\cachemap\AbstractCacheMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
