/*     */ package com.aionemu.commons.utils;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javolution.util.FastMap;
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
/*     */ public final class SingletonMap<K, V>
/*     */   implements Map<K, V>
/*     */ {
/*     */   private boolean initialized = false;
/*  31 */   private Map<K, V> map = AECollections.emptyMap();
/*     */ 
/*     */   
/*     */   private boolean shared = false;
/*     */ 
/*     */   
/*     */   private void init() {
/*  38 */     if (!this.initialized)
/*     */     {
/*  40 */       synchronized (this) {
/*     */         
/*  42 */         if (!this.initialized) {
/*     */           
/*  44 */           this.map = (Map<K, V>)(new FastMap()).setShared(this.shared);
/*  45 */           this.initialized = true;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SingletonMap<K, V> setShared() {
/*  54 */     this.shared = true;
/*     */     
/*  56 */     synchronized (this) {
/*     */       
/*  58 */       if (this.initialized)
/*     */       {
/*  60 */         ((FastMap)this.map).setShared(true);
/*     */       }
/*     */     } 
/*     */     
/*  64 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/*  70 */     this.map.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsKey(Object key) {
/*  76 */     return this.map.containsKey(key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsValue(Object value) {
/*  82 */     return this.map.containsValue(value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Map.Entry<K, V>> entrySet() {
/*  88 */     return this.map.entrySet();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V get(Object key) {
/*  94 */     return this.map.get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 100 */     return this.map.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<K> keySet() {
/* 106 */     return this.map.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V put(K key, V value) {
/* 112 */     init();
/*     */     
/* 114 */     return this.map.put(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAll(Map<? extends K, ? extends V> m) {
/* 120 */     init();
/*     */     
/* 122 */     this.map.putAll(m);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public V remove(Object key) {
/* 128 */     return this.map.remove(key);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 134 */     return this.map.size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<V> values() {
/* 140 */     return this.map.values();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 146 */     return super.toString() + "-" + this.map.toString();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\common\\utils\SingletonMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */