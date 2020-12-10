/*     */ package com.aionemu.commons.network;
/*     */ 
/*     */ import com.aionemu.commons.network.packet.BaseClientPacket;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.concurrent.locks.Condition;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketProcessor<T extends AConnection>
/*     */ {
/*  45 */   private static final Logger log = Logger.getLogger(PacketProcessor.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int reduceThreshold = 3;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int increaseThreshold = 50;
/*     */ 
/*     */ 
/*     */   
/*  58 */   private final Lock lock = new ReentrantLock();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  63 */   private final Condition notEmpty = this.lock.newCondition();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private final List<BaseClientPacket<T>> packets = new LinkedList<BaseClientPacket<T>>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   private final List<Thread> threads = new ArrayList<Thread>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int minThreads;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int maxThreads;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PacketProcessor(int minThreads, int maxThreads) {
/*  95 */     if (minThreads <= 0)
/*  96 */       minThreads = 1; 
/*  97 */     if (maxThreads < minThreads) {
/*  98 */       maxThreads = minThreads;
/*     */     }
/* 100 */     this.minThreads = minThreads;
/* 101 */     this.maxThreads = maxThreads;
/*     */     
/* 103 */     if (minThreads != maxThreads) {
/* 104 */       startCheckerThread();
/*     */     }
/* 106 */     for (int i = 0; i < minThreads; i++) {
/* 107 */       newThread();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void startCheckerThread() {
/* 116 */     (new Thread(new CheckerTask(), "PacketProcessor:Checker")).start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean newThread() {
/* 126 */     if (this.threads.size() >= this.maxThreads) {
/* 127 */       return false;
/*     */     }
/* 129 */     String name = "PacketProcessor:" + this.threads.size();
/* 130 */     log.debug("Creating new PacketProcessor Thread: " + name);
/*     */     
/* 132 */     Thread t = new Thread(new PacketProcessorTask(), name);
/* 133 */     this.threads.add(t);
/* 134 */     t.start();
/*     */     
/* 136 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void killThread() {
/* 144 */     if (this.threads.size() < this.minThreads) {
/*     */       
/* 146 */       Thread t = this.threads.remove(this.threads.size() - 1);
/* 147 */       log.debug("Killing PacketProcessor Thread: " + t.getName());
/* 148 */       t.interrupt();
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
/*     */   public final void executePacket(BaseClientPacket<T> packet) {
/* 160 */     this.lock.lock();
/*     */     
/*     */     try {
/* 163 */       this.packets.add(packet);
/* 164 */       this.notEmpty.signal();
/*     */     }
/*     */     finally {
/*     */       
/* 168 */       this.lock.unlock();
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
/*     */   private BaseClientPacket<T> getFirstAviable() {
/*     */     while (true) {
/* 182 */       while (this.packets.isEmpty()) {
/* 183 */         this.notEmpty.awaitUninterruptibly();
/*     */       }
/* 185 */       ListIterator<BaseClientPacket<T>> it = this.packets.listIterator();
/* 186 */       while (it.hasNext()) {
/*     */         
/* 188 */         BaseClientPacket<T> packet = it.next();
/* 189 */         if (packet.getConnection().tryLockConnection()) {
/*     */           
/* 191 */           it.remove();
/* 192 */           return packet;
/*     */         } 
/*     */       } 
/* 195 */       this.notEmpty.awaitUninterruptibly();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final class PacketProcessorTask
/*     */     implements Runnable
/*     */   {
/*     */     private PacketProcessorTask() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/* 214 */       BaseClientPacket<T> packet = null;
/*     */       
/*     */       while (true) {
/* 217 */         PacketProcessor.this.lock.lock();
/*     */         
/*     */         try {
/* 220 */           if (packet != null) {
/* 221 */             packet.getConnection().unlockConnection();
/*     */           }
/*     */           
/* 224 */           if (Thread.interrupted()) {
/*     */             return;
/*     */           }
/* 227 */           packet = PacketProcessor.this.getFirstAviable();
/*     */         }
/*     */         finally {
/*     */           
/* 231 */           PacketProcessor.this.lock.unlock();
/*     */         } 
/* 233 */         packet.run();
/*     */       } 
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
/*     */   private final class CheckerTask
/*     */     implements Runnable
/*     */   {
/* 249 */     private final int sleepTime = 60000;
/*     */ 
/*     */ 
/*     */     
/* 253 */     private int lastSize = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       try {
/* 264 */         Thread.sleep(60000L);
/*     */       }
/* 266 */       catch (InterruptedException e) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 272 */       int sizeNow = PacketProcessor.this.packets.size();
/*     */       
/* 274 */       if (sizeNow < this.lastSize) {
/*     */         
/* 276 */         if (sizeNow < 3)
/*     */         {
/*     */           
/* 279 */           PacketProcessor.this.killThread();
/*     */         }
/*     */       }
/* 282 */       else if (sizeNow > this.lastSize && sizeNow > 50) {
/*     */ 
/*     */         
/* 285 */         if (!PacketProcessor.this.newThread() && sizeNow >= 150) {
/* 286 */           PacketProcessor.log.info("Lagg detected! [" + sizeNow + " client packets are waiting for execution]. You should consider increasing PacketProcessor maxThreads or hardware upgrade.");
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 291 */       this.lastSize = sizeNow;
/*     */     }
/*     */     
/*     */     private CheckerTask() {}
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\network\PacketProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */