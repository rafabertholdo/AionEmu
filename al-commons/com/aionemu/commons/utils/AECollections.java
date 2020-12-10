/*     */ package com.aionemu.commons.utils;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class AECollections
/*     */ {
/*  32 */   private static final Object[] EMPTY_ARRAY = new Object[0];
/*     */   
/*     */   private static final class EmptyListIterator
/*     */     implements ListIterator<Object> {
/*  36 */     private static final ListIterator<Object> INSTANCE = new EmptyListIterator();
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasNext() {
/*  41 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object next() {
/*  47 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean hasPrevious() {
/*  53 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object previous() {
/*  59 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int nextIndex() {
/*  65 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int previousIndex() {
/*  71 */       return -1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void add(Object obj) {
/*  77 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void set(Object obj) {
/*  83 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void remove() {
/*  89 */       throw new UnsupportedOperationException();
/*     */     } }
/*     */   
/*     */   private static class EmptyCollection implements Collection<Object> {
/*     */     private EmptyCollection() {}
/*     */     
/*  95 */     private static final Collection<Object> INSTANCE = new EmptyCollection();
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean add(Object e) {
/* 100 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean addAll(Collection<? extends Object> c) {
/* 106 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void clear() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean contains(Object o) {
/* 117 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsAll(Collection<?> c) {
/* 123 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 129 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Iterator<Object> iterator() {
/* 135 */       return (Iterator)AECollections.emptyListIterator();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(Object o) {
/* 141 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean removeAll(Collection<?> c) {
/* 147 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean retainAll(Collection<?> c) {
/* 153 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int size() {
/* 159 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object[] toArray() {
/* 165 */       return AECollections.EMPTY_ARRAY;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public <T> T[] toArray(T[] a) {
/* 171 */       if (a.length != 0) {
/* 172 */         a = (T[])Array.newInstance(a.getClass().getComponentType(), 0);
/*     */       }
/* 174 */       return a;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final String toString() {
/* 180 */       return "[]";
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class EmptySet
/*     */     extends EmptyCollection implements Set<Object> {
/* 186 */     private static final Set<Object> INSTANCE = new EmptySet();
/*     */   }
/*     */   
/*     */   private static final class EmptyMap
/*     */     implements Map<Object, Object> {
/* 191 */     private static final Map<Object, Object> INSTANCE = new EmptyMap();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void clear() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsKey(Object key) {
/* 201 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean containsValue(Object value) {
/* 207 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<Map.Entry<Object, Object>> entrySet() {
/* 213 */       return AECollections.emptySet();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object get(Object key) {
/* 219 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isEmpty() {
/* 225 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Set<Object> keySet() {
/* 231 */       return AECollections.emptySet();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object put(Object key, Object value) {
/* 237 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void putAll(Map<? extends Object, ? extends Object> m) {
/* 243 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Object remove(Object key) {
/* 249 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int size() {
/* 255 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Collection<Object> values() {
/* 261 */       return (Collection)AECollections.emptyCollection();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 267 */       return "{}";
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> ListIterator<T> emptyListIterator() {
/* 273 */     return (ListIterator)EmptyListIterator.INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> Collection<T> emptyCollection() {
/* 278 */     return (Collection)EmptyCollection.INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T> Set<T> emptySet() {
/* 283 */     return (Set)EmptySet.INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public static <K, V> Map<K, V> emptyMap() {
/* 288 */     return (Map)EmptyMap.INSTANCE;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\common\\utils\AECollections.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */