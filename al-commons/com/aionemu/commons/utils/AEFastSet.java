/*     */ package com.aionemu.commons.utils;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import javolution.util.FastCollection;
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
/*     */ public class AEFastSet<E>
/*     */   extends AEFastCollection<E>
/*     */   implements Set<E>
/*     */ {
/*  31 */   private static final Object NULL = new Object();
/*     */   
/*     */   private final FastMap<E, Object> map;
/*     */ 
/*     */   
/*     */   public AEFastSet() {
/*  37 */     this.map = new FastMap();
/*     */   }
/*     */ 
/*     */   
/*     */   public AEFastSet(int capacity) {
/*  42 */     this.map = new FastMap(capacity);
/*     */   }
/*     */ 
/*     */   
/*     */   public AEFastSet(Set<? extends E> elements) {
/*  47 */     this.map = new FastMap(elements.size());
/*     */     
/*  49 */     addAll(elements);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isShared() {
/*  58 */     return this.map.isShared();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FastCollection.Record head() {
/*  64 */     return (FastCollection.Record)this.map.head();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public FastCollection.Record tail() {
/*  70 */     return (FastCollection.Record)this.map.tail();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public E valueOf(FastCollection.Record record) {
/*  76 */     return (E)((FastMap.Entry)record).getKey();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(FastCollection.Record record) {
/*  82 */     this.map.remove(((FastMap.Entry)record).getKey());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void delete(FastCollection.Record record, E value) {
/*  88 */     this.map.remove(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean add(E value) {
/*  93 */     return (this.map.put(value, NULL) == null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/*  98 */     this.map.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object o) {
/* 103 */     return this.map.containsKey(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 108 */     return this.map.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<E> iterator() {
/* 113 */     return this.map.keySet().iterator();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object o) {
/* 118 */     return (this.map.remove(o) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 123 */     return this.map.size();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 129 */     return super.toString() + "-" + this.map.keySet().toString();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\common\\utils\AEFastSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */