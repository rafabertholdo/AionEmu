/*     */ package com.aionemu.gameserver.utils.idfactory;
/*     */ 
/*     */ import com.aionemu.commons.database.dao.DAOManager;
/*     */ import com.aionemu.gameserver.dao.InventoryDAO;
/*     */ import com.aionemu.gameserver.dao.LegionDAO;
/*     */ import com.aionemu.gameserver.dao.MailDAO;
/*     */ import com.aionemu.gameserver.dao.PlayerDAO;
/*     */ import java.util.BitSet;
/*     */ import java.util.Iterator;
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
/*     */ public class IDFactory
/*     */ {
/*  40 */   private static final Logger log = Logger.getLogger(IDFactory.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final BitSet idList;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final ReentrantLock lock;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   private volatile int nextMinId = 0;
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
/*     */   private IDFactory() {
/*  67 */     this.idList = new BitSet();
/*  68 */     this.lock = new ReentrantLock();
/*  69 */     lockIds(new int[] { 0 });
/*     */ 
/*     */     
/*  72 */     lockIds(((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).getUsedIDs());
/*  73 */     lockIds(((InventoryDAO)DAOManager.getDAO(InventoryDAO.class)).getUsedIDs());
/*  74 */     lockIds(((LegionDAO)DAOManager.getDAO(LegionDAO.class)).getUsedIDs());
/*  75 */     lockIds(((MailDAO)DAOManager.getDAO(MailDAO.class)).getUsedIDs());
/*  76 */     log.info("IDFactory: " + getUsedCount() + " id's used.");
/*     */   }
/*     */ 
/*     */   
/*     */   public static final IDFactory getInstance() {
/*  81 */     return SingletonHolder.instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public int nextId() {
/*     */     try {
/*     */       int id;
/*  88 */       this.lock.lock();
/*     */ 
/*     */       
/*  91 */       if (this.nextMinId == Integer.MIN_VALUE) {
/*     */ 
/*     */ 
/*     */         
/*  95 */         id = Integer.MIN_VALUE;
/*     */       }
/*     */       else {
/*     */         
/*  99 */         id = this.idList.nextClearBit(this.nextMinId);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 104 */       if (id == Integer.MIN_VALUE)
/*     */       {
/* 106 */         throw new IDFactoryError("All id's are used, please clear your database");
/*     */       }
/* 108 */       this.idList.set(id);
/*     */ 
/*     */       
/* 111 */       this.nextMinId = id + 1;
/* 112 */       return id;
/*     */     }
/*     */     finally {
/*     */       
/* 116 */       this.lock.unlock();
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
/*     */   
/*     */   private void lockIds(int... ids) {
/*     */     try {
/* 132 */       this.lock.lock();
/* 133 */       for (int id : ids)
/*     */       {
/* 135 */         boolean status = this.idList.get(id);
/* 136 */         if (status)
/*     */         {
/* 138 */           throw new IDFactoryError("ID " + id + " is already taken, fatal error!!!");
/*     */         }
/* 140 */         this.idList.set(id);
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 145 */       this.lock.unlock();
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
/*     */   
/*     */   public void lockIds(Iterable<Integer> ids) {
/*     */     try {
/* 161 */       this.lock.lock();
/* 162 */       for (Iterator<Integer> i$ = ids.iterator(); i$.hasNext(); ) { int id = ((Integer)i$.next()).intValue();
/*     */         
/* 164 */         boolean status = this.idList.get(id);
/* 165 */         if (status)
/*     */         {
/* 167 */           throw new IDFactoryError("ID " + id + " is already taken, fatal error!!!");
/*     */         }
/* 169 */         this.idList.set(id); }
/*     */ 
/*     */     
/*     */     } finally {
/*     */       
/* 174 */       this.lock.unlock();
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
/*     */   
/*     */   public void releaseId(int id) {
/*     */     try {
/* 190 */       this.lock.lock();
/* 191 */       boolean status = this.idList.get(id);
/* 192 */       if (!status)
/*     */       {
/* 194 */         throw new IDFactoryError("ID " + id + " is not taken, can't release it.");
/*     */       }
/* 196 */       this.idList.clear(id);
/* 197 */       if (id < this.nextMinId || this.nextMinId == Integer.MIN_VALUE)
/*     */       {
/* 199 */         this.nextMinId = id;
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 204 */       this.lock.unlock();
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
/*     */   public int getUsedCount() {
/*     */     try {
/* 217 */       this.lock.lock();
/* 218 */       return this.idList.cardinality();
/*     */     }
/*     */     finally {
/*     */       
/* 222 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static class SingletonHolder
/*     */   {
/* 229 */     protected static final IDFactory instance = new IDFactory();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\idfactory\IDFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */