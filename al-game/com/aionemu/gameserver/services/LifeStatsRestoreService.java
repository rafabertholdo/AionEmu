/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.CreatureLifeStats;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.PlayerLifeStats;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import java.util.concurrent.Future;
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
/*     */ public class LifeStatsRestoreService
/*     */ {
/*     */   private static final int DEFAULT_DELAY = 6000;
/*     */   private static final int DEFAULT_FPREDUCE_DELAY = 1000;
/*     */   private static final int DEFAULT_FPRESTORE_DELAY = 2000;
/*  36 */   private static LifeStatsRestoreService instance = new LifeStatsRestoreService();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Future<?> scheduleRestoreTask(CreatureLifeStats<? extends Creature> lifeStats) {
/*  46 */     return ThreadPoolManager.getInstance().scheduleAtFixedRate(new HpMpRestoreTask(lifeStats), 1700L, 6000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Future<?> scheduleHpRestoreTask(CreatureLifeStats<? extends Creature> lifeStats) {
/*  57 */     return ThreadPoolManager.getInstance().scheduleAtFixedRate(new HpRestoreTask(lifeStats), 1700L, 6000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Future<?> scheduleFpReduceTask(PlayerLifeStats lifeStats) {
/*  67 */     return ThreadPoolManager.getInstance().scheduleAtFixedRate(new FpReduceTask(lifeStats), 2000L, 1000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Future<?> scheduleFpRestoreTask(PlayerLifeStats lifeStats) {
/*  78 */     return ThreadPoolManager.getInstance().scheduleAtFixedRate(new FpRestoreTask(lifeStats), 2000L, 2000L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static LifeStatsRestoreService getInstance() {
/*  84 */     return instance;
/*     */   }
/*     */   
/*     */   private static class HpRestoreTask
/*     */     implements Runnable
/*     */   {
/*     */     private CreatureLifeStats<?> lifeStats;
/*     */     
/*     */     private HpRestoreTask(CreatureLifeStats<?> lifeStats) {
/*  93 */       this.lifeStats = lifeStats;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/*  99 */       if (this.lifeStats.isAlreadyDead() || this.lifeStats.isFullyRestoredHp()) {
/*     */         
/* 101 */         this.lifeStats.cancelRestoreTask();
/*     */       }
/*     */       else {
/*     */         
/* 105 */         this.lifeStats.restoreHp();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class HpMpRestoreTask
/*     */     implements Runnable
/*     */   {
/*     */     private CreatureLifeStats<?> lifeStats;
/*     */     
/*     */     private HpMpRestoreTask(CreatureLifeStats<?> lifeStats) {
/* 116 */       this.lifeStats = lifeStats;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/* 122 */       if (this.lifeStats.isAlreadyDead() || this.lifeStats.isFullyRestoredHpMp()) {
/*     */         
/* 124 */         this.lifeStats.cancelRestoreTask();
/*     */       }
/*     */       else {
/*     */         
/* 128 */         this.lifeStats.restoreHp();
/* 129 */         this.lifeStats.restoreMp();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class FpReduceTask
/*     */     implements Runnable
/*     */   {
/*     */     private PlayerLifeStats lifeStats;
/*     */     
/*     */     private FpReduceTask(PlayerLifeStats lifeStats) {
/* 140 */       this.lifeStats = lifeStats;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/* 146 */       if (this.lifeStats.isAlreadyDead()) {
/* 147 */         this.lifeStats.cancelFpReduce();
/*     */       }
/* 149 */       if (this.lifeStats.getCurrentFp() == 0) {
/*     */         
/* 151 */         if (this.lifeStats.getOwner().getFlyState() > 0)
/*     */         {
/* 153 */           this.lifeStats.getOwner().getFlyController().endFly();
/*     */         }
/*     */         else
/*     */         {
/* 157 */           this.lifeStats.triggerFpRestore();
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 162 */         this.lifeStats.reduceFp(1);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static class FpRestoreTask
/*     */     implements Runnable
/*     */   {
/*     */     private PlayerLifeStats lifeStats;
/*     */     
/*     */     private FpRestoreTask(PlayerLifeStats lifeStats) {
/* 173 */       this.lifeStats = lifeStats;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/* 179 */       if (this.lifeStats.isAlreadyDead() || this.lifeStats.isFlyTimeFullyRestored()) {
/*     */         
/* 181 */         this.lifeStats.cancelFpRestore();
/*     */       }
/*     */       else {
/*     */         
/* 185 */         this.lifeStats.restoreFp();
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\LifeStatsRestoreService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */