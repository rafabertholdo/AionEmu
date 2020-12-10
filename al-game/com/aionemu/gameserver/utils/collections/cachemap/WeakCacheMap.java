/*    */ package com.aionemu.gameserver.utils.collections.cachemap;
/*    */ 
/*    */ import java.lang.ref.Reference;
/*    */ import java.lang.ref.ReferenceQueue;
/*    */ import java.lang.ref.WeakReference;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class WeakCacheMap<K, V>
/*    */   extends AbstractCacheMap<K, V>
/*    */   implements CacheMap<K, V>
/*    */ {
/* 36 */   private static final Logger log = Logger.getLogger(WeakCacheMap.class);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private class Entry
/*    */     extends WeakReference<V>
/*    */   {
/*    */     private K key;
/*    */ 
/*    */ 
/*    */     
/*    */     Entry(K key, V referent, ReferenceQueue<? super V> q) {
/* 49 */       super(referent, q);
/* 50 */       this.key = key;
/*    */     }
/*    */ 
/*    */     
/*    */     K getKey() {
/* 55 */       return this.key;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   WeakCacheMap(String cacheName, String valueName) {
/* 61 */     super(cacheName, valueName, log);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected synchronized void cleanQueue() {
/* 68 */     Entry en = null;
/* 69 */     while ((en = (Entry)this.refQueue.poll()) != null) {
/*    */       
/* 71 */       K key = en.getKey();
/* 72 */       if (log.isDebugEnabled())
/* 73 */         log.debug(this.cacheName + " : cleaned up " + this.valueName + " for key: " + key); 
/* 74 */       this.cacheMap.remove(key);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected Reference<V> newReference(K key, V value, ReferenceQueue<V> vReferenceQueue) {
/* 81 */     return new Entry(key, value, vReferenceQueue);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\collections\cachemap\WeakCacheMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */