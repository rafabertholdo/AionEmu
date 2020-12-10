/*    */ package com.aionemu.gameserver.utils.collections.cachemap;
/*    */ 
/*    */ import com.aionemu.gameserver.configs.main.CacheConfig;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CacheMapFactory
/*    */ {
/*    */   public static <K, V> CacheMap<K, V> createCacheMap(String cacheName, String valueName) {
/* 41 */     if (CacheConfig.SOFT_CACHE_MAP) {
/* 42 */       return createSoftCacheMap(cacheName, valueName);
/*    */     }
/* 44 */     return createWeakCacheMap(cacheName, valueName);
/*    */   }
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
/*    */   public static <K, V> CacheMap<K, V> createSoftCacheMap(String cacheName, String valueName) {
/* 59 */     return new SoftCacheMap<K, V>(cacheName, valueName);
/*    */   }
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
/*    */   public static <K, V> CacheMap<K, V> createWeakCacheMap(String cacheName, String valueName) {
/* 74 */     return new WeakCacheMap<K, V>(cacheName, valueName);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\collections\cachemap\CacheMapFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */