/*     */ package com.aionemu.gameserver.utils.collections.cachemap;
/*     */ 
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class AbstractCacheMap<K, V>
/*     */   implements CacheMap<K, V>
/*     */ {
/*     */   private final Logger log;
/*     */   protected final String cacheName;
/*     */   protected final String valueName;
/*  42 */   protected final Map<K, Reference<V>> cacheMap = new HashMap<K, Reference<V>>();
/*     */   
/*  44 */   protected final ReferenceQueue<V> refQueue = new ReferenceQueue<V>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   AbstractCacheMap(String cacheName, String valueName, Logger log) {
/*  52 */     this.cacheName = "#CACHE  [" + cacheName + "]#  ";
/*  53 */     this.valueName = valueName;
/*  54 */     this.log = log;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(K key, V value) {
/*  61 */     cleanQueue();
/*     */     
/*  63 */     if (this.cacheMap.containsKey(key)) {
/*  64 */       throw new IllegalArgumentException("Key: " + key + " already exists in map");
/*     */     }
/*  66 */     Reference<V> entry = newReference(key, value, this.refQueue);
/*     */     
/*  68 */     this.cacheMap.put(key, entry);
/*     */     
/*  70 */     if (this.log.isDebugEnabled()) {
/*  71 */       this.log.debug(this.cacheName + " : added " + this.valueName + " for key: " + key);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V get(K key) {
/*  78 */     cleanQueue();
/*     */     
/*  80 */     Reference<V> reference = this.cacheMap.get(key);
/*     */     
/*  82 */     if (reference == null) {
/*  83 */       return null;
/*     */     }
/*  85 */     V res = reference.get();
/*     */     
/*  87 */     if (res != null && this.log.isDebugEnabled()) {
/*  88 */       this.log.debug(this.cacheName + " : obtained " + this.valueName + " for key: " + key);
/*     */     }
/*  90 */     return res;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(K key) {
/*  96 */     cleanQueue();
/*  97 */     return this.cacheMap.containsKey(key);
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void cleanQueue();
/*     */ 
/*     */   
/*     */   public void remove(K key) {
/* 105 */     this.cacheMap.remove(key);
/*     */   }
/*     */   
/*     */   protected abstract Reference<V> newReference(K paramK, V paramV, ReferenceQueue<V> paramReferenceQueue);
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\collections\cachemap\AbstractCacheMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */