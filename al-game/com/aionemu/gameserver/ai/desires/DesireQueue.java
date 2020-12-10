/*     */ package com.aionemu.gameserver.ai.desires;
/*     */ 
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.Iterator;
/*     */ import java.util.PriorityQueue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DesireQueue
/*     */ {
/*     */   protected PriorityQueue<Desire> queue;
/*     */   
/*     */   public synchronized Desire peek() {
/*  45 */     return (this.queue != null) ? this.queue.peek() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized Desire poll() {
/*  55 */     if (this.queue != null)
/*     */     {
/*  57 */       return this.queue.poll();
/*     */     }
/*     */     
/*  60 */     return null;
/*     */   }
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
/*     */   public synchronized void addDesire(Desire desire) {
/*  83 */     if (this.queue == null)
/*     */     {
/*  85 */       this.queue = new PriorityQueue<Desire>();
/*     */     }
/*     */ 
/*     */     
/*  89 */     Iterator<Desire> iterator = this.queue.iterator();
/*  90 */     while (iterator.hasNext()) {
/*     */       
/*  92 */       Desire iterated = iterator.next();
/*     */ 
/*     */       
/*  95 */       if (desire.equals(iterated)) {
/*     */ 
/*     */         
/*  98 */         iterator.remove();
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 103 */         if (desire != iterated)
/*     */         {
/* 105 */           desire.increaseDesirePower(iterated.getDesirePower());
/*     */         }
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*     */     
/* 113 */     this.queue.add(desire);
/*     */   }
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
/*     */   public synchronized boolean removeDesire(Desire desire) {
/* 126 */     return (this.queue != null && this.queue.remove(desire));
/*     */   }
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
/*     */   public synchronized void iterateDesires(DesireIteratorHandler handler, DesireIteratorFilter... filters) throws ConcurrentModificationException {
/* 156 */     if (this.queue == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 161 */     Iterator<Desire> iterator = this.queue.iterator();
/* 162 */     label20: while (iterator.hasNext()) {
/*     */       
/* 164 */       Desire desire = iterator.next();
/*     */       
/* 166 */       if (filters != null && filters.length > 0)
/*     */       {
/* 168 */         for (DesireIteratorFilter filter : filters) {
/*     */           
/* 170 */           if (!filter.isOk(desire)) {
/*     */             continue label20;
/*     */           }
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 177 */       handler.next(desire, iterator);
/*     */     } 
/*     */   }
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
/*     */   public synchronized boolean contains(Desire desire) {
/* 191 */     return this.queue.contains(desire);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized boolean isEmpty() {
/* 201 */     return (this.queue == null || this.queue.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void clear() {
/* 209 */     if (this.queue != null) {
/*     */       
/* 211 */       Desire desire = null;
/* 212 */       while ((desire = this.queue.poll()) != null) {
/* 213 */         desire.onClear();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int size() {
/* 224 */     return (this.queue == null) ? 0 : this.queue.size();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\desires\DesireQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */