package com.aionemu.gameserver.utils.collections.cachemap;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import org.apache.log4j.Logger;
























class SoftCacheMap<K, V>
  extends AbstractCacheMap<K, V>
  implements CacheMap<K, V>
{
  private static final Logger log = Logger.getLogger(SoftCacheMap.class);



  
  private class SoftEntry
    extends SoftReference<V>
  {
    private K key;


    
    SoftEntry(K key, V referent, ReferenceQueue<? super V> q) {
      super(referent, q);
      this.key = key;
    }

    
    K getKey() {
      return this.key;
    }
  }

  
  SoftCacheMap(String cacheName, String valueName) {
    super(cacheName, valueName, log);
  }



  
  protected synchronized void cleanQueue() {
    SoftEntry en = null;
    while ((en = (SoftEntry)this.refQueue.poll()) != null) {
      
      K key = en.getKey();
      if (log.isDebugEnabled())
        log.debug(this.cacheName + " : cleaned up " + this.valueName + " for key: " + key); 
      this.cacheMap.remove(key);
    } 
  }


  
  protected Reference<V> newReference(K key, V value, ReferenceQueue<V> vReferenceQueue) {
    return new SoftEntry(key, value, vReferenceQueue);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\collections\cachemap\SoftCacheMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
