/*     */ package com.aionemu.commons.utils;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import javolution.util.FastCollection;
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
/*     */ public abstract class AEFastCollection<E>
/*     */   implements Collection<E>
/*     */ {
/*     */   public abstract FastCollection.Record head();
/*     */   
/*     */   public abstract FastCollection.Record tail();
/*     */   
/*     */   public abstract E valueOf(FastCollection.Record paramRecord);
/*     */   
/*     */   public abstract void delete(FastCollection.Record paramRecord);
/*     */   
/*     */   public abstract void delete(FastCollection.Record paramRecord, E paramE);
/*     */   
/*     */   public final E getFirst() {
/*  45 */     FastCollection.Record first = head().getNext();
/*  46 */     if (first == tail()) {
/*  47 */       return null;
/*     */     }
/*  49 */     return valueOf(first);
/*     */   }
/*     */ 
/*     */   
/*     */   public final E getLast() {
/*  54 */     FastCollection.Record last = tail().getPrevious();
/*  55 */     if (last == head()) {
/*  56 */       return null;
/*     */     }
/*  58 */     return valueOf(last);
/*     */   }
/*     */ 
/*     */   
/*     */   public final E removeFirst() {
/*  63 */     FastCollection.Record first = head().getNext();
/*  64 */     if (first == tail()) {
/*  65 */       return null;
/*     */     }
/*  67 */     E value = valueOf(first);
/*  68 */     delete(first, value);
/*  69 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public final E removeLast() {
/*  74 */     FastCollection.Record last = tail().getPrevious();
/*  75 */     if (last == head()) {
/*  76 */       return null;
/*     */     }
/*  78 */     E value = valueOf(last);
/*  79 */     delete(last, value);
/*  80 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(E[] c) {
/*  85 */     boolean modified = false;
/*     */     
/*  87 */     for (E e : c) {
/*  88 */       if (add(e))
/*  89 */         modified = true; 
/*     */     } 
/*  91 */     return modified;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Collection<? extends E> c) {
/*  96 */     return addAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addAll(Iterable<? extends E> c) {
/* 101 */     if (c instanceof java.util.RandomAccess && c instanceof List) {
/* 102 */       return addAll((List<? extends E>)c);
/*     */     }
/* 104 */     if (c instanceof FastCollection) {
/* 105 */       return addAll((FastCollection<? extends E>)c);
/*     */     }
/* 107 */     if (c instanceof AEFastCollection) {
/* 108 */       return addAll((AEFastCollection<? extends E>)c);
/*     */     }
/* 110 */     boolean modified = false;
/*     */     
/* 112 */     for (E e : c) {
/* 113 */       if (add(e))
/* 114 */         modified = true; 
/*     */     } 
/* 116 */     return modified;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean addAll(AEFastCollection<? extends E> c) {
/* 121 */     boolean modified = false;
/*     */     
/* 123 */     for (FastCollection.Record r = c.head(), end = c.tail(); (r = r.getNext()) != end;) {
/* 124 */       if (add(c.valueOf(r)))
/* 125 */         modified = true; 
/*     */     } 
/* 127 */     return modified;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean addAll(FastCollection<? extends E> c) {
/* 132 */     boolean modified = false;
/*     */     
/* 134 */     for (FastCollection.Record r = c.head(), end = c.tail(); (r = r.getNext()) != end;) {
/* 135 */       if (add((E)c.valueOf(r)))
/* 136 */         modified = true; 
/*     */     } 
/* 138 */     return modified;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean addAll(List<? extends E> c) {
/* 143 */     boolean modified = false;
/*     */     
/* 145 */     for (int i = 0, size = c.size(); i < size;) {
/* 146 */       if (add(c.get(i++)))
/* 147 */         modified = true; 
/*     */     } 
/* 149 */     return modified;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAll(Object[] c) {
/* 154 */     for (Object obj : c) {
/* 155 */       if (!contains(obj))
/* 156 */         return false; 
/*     */     } 
/* 158 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAll(Collection<?> c) {
/* 163 */     return containsAll(c);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean containsAll(Iterable<?> c) {
/* 168 */     if (c instanceof java.util.RandomAccess && c instanceof List) {
/* 169 */       return containsAll((List)c);
/*     */     }
/* 171 */     if (c instanceof FastCollection) {
/* 172 */       return containsAll((FastCollection)c);
/*     */     }
/* 174 */     if (c instanceof AEFastCollection) {
/* 175 */       return containsAll((AEFastCollection)c);
/*     */     }
/* 177 */     for (Object obj : c) {
/* 178 */       if (!contains(obj))
/* 179 */         return false; 
/*     */     } 
/* 181 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean containsAll(AEFastCollection<?> c) {
/* 186 */     for (FastCollection.Record r = c.head(), end = c.tail(); (r = r.getNext()) != end;) {
/* 187 */       if (!contains(c.valueOf(r)))
/* 188 */         return false; 
/*     */     } 
/* 190 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean containsAll(FastCollection<?> c) {
/* 195 */     for (FastCollection.Record r = c.head(), end = c.tail(); (r = r.getNext()) != end;) {
/* 196 */       if (!contains(c.valueOf(r)))
/* 197 */         return false; 
/*     */     } 
/* 199 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean containsAll(List<?> c) {
/* 204 */     for (int i = 0, size = c.size(); i < size;) {
/* 205 */       if (!contains(c.get(i++)))
/* 206 */         return false; 
/*     */     } 
/* 208 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeAll(Collection<?> c) {
/* 213 */     boolean modified = false;
/*     */     
/* 215 */     for (FastCollection.Record head = head(), r = tail().getPrevious(); r != head; r = previous) {
/*     */       
/* 217 */       FastCollection.Record previous = r.getPrevious();
/* 218 */       if (c.contains(valueOf(r))) {
/*     */         
/* 220 */         delete(r);
/* 221 */         modified = true;
/*     */       } 
/*     */     } 
/*     */     
/* 225 */     return modified;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean retainAll(Collection<?> c) {
/* 230 */     boolean modified = false;
/*     */     
/* 232 */     for (FastCollection.Record head = head(), r = tail().getPrevious(); r != head; r = previous) {
/*     */       
/* 234 */       FastCollection.Record previous = r.getPrevious();
/* 235 */       if (!c.contains(valueOf(r))) {
/*     */         
/* 237 */         delete(r);
/* 238 */         modified = true;
/*     */       } 
/*     */     } 
/*     */     
/* 242 */     return modified;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object[] toArray() {
/* 247 */     return toArray(new Object[size()]);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T[] toArray(T[] array) {
/* 252 */     int size = size();
/*     */     
/* 254 */     if (array.length != size) {
/* 255 */       array = (T[])Array.newInstance(array.getClass().getComponentType(), size);
/*     */     }
/* 257 */     if (size == 0 && array.length == 0) {
/* 258 */       return array;
/*     */     }
/* 260 */     int i = 0;
/* 261 */     for (FastCollection.Record r = head(), end = tail(); (r = r.getNext()) != end;) {
/* 262 */       array[i++] = (T)valueOf(r);
/*     */     }
/* 264 */     return array;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\common\\utils\AEFastCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */