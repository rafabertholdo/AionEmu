/*     */ package com.aionemu.gameserver.model.gameobjects.stats;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
/*     */ import com.aionemu.gameserver.services.LifeStatsRestoreService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import java.util.concurrent.Future;
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
/*     */ public abstract class CreatureLifeStats<T extends Creature>
/*     */ {
/*  36 */   private static final Logger log = Logger.getLogger(CreatureLifeStats.class);
/*     */   
/*     */   protected int currentHp;
/*     */   
/*     */   protected int currentMp;
/*     */   
/*     */   protected boolean alreadyDead = false;
/*     */   
/*     */   protected Creature owner;
/*  45 */   private final ReentrantLock hpLock = new ReentrantLock();
/*  46 */   private final ReentrantLock mpLock = new ReentrantLock();
/*     */ 
/*     */   
/*     */   protected Future<?> lifeRestoreTask;
/*     */ 
/*     */   
/*     */   public CreatureLifeStats(Creature owner, int currentHp, int currentMp) {
/*  53 */     this.owner = owner;
/*  54 */     this.currentHp = currentHp;
/*  55 */     this.currentMp = currentMp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Creature getOwner() {
/*  63 */     return this.owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentHp() {
/*  71 */     return this.currentHp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentMp() {
/*  79 */     return this.currentMp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxHp() {
/*  87 */     int maxHp = getOwner().getGameStats().getCurrentStat(StatEnum.MAXHP);
/*  88 */     if (maxHp == 0) {
/*     */       
/*  90 */       maxHp = 1;
/*  91 */       log.warn("CHECKPOINT: maxhp is 0 :" + getOwner().getGameStats());
/*     */     } 
/*  93 */     return maxHp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxMp() {
/* 101 */     return getOwner().getGameStats().getCurrentStat(StatEnum.MAXMP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAlreadyDead() {
/* 110 */     return this.alreadyDead;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int reduceHp(int value, Creature attacker) {
/* 121 */     this.hpLock.lock();
/*     */     
/*     */     try {
/* 124 */       int newHp = this.currentHp - value;
/*     */       
/* 126 */       if (newHp < 0) {
/*     */         
/* 128 */         newHp = 0;
/* 129 */         if (!this.alreadyDead)
/*     */         {
/* 131 */           this.alreadyDead = true;
/*     */         }
/*     */       } 
/* 134 */       this.currentHp = newHp;
/*     */       
/* 136 */       onReduceHp();
/*     */       
/* 138 */       if (this.alreadyDead)
/*     */       {
/* 140 */         getOwner().getController().onDie(attacker);
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 145 */       this.hpLock.unlock();
/*     */     } 
/*     */     
/* 148 */     return this.currentHp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int reduceMp(int value) {
/* 158 */     this.mpLock.lock();
/*     */     
/*     */     try {
/* 161 */       int newMp = this.currentMp - value;
/*     */       
/* 163 */       if (newMp < 0) {
/* 164 */         newMp = 0;
/*     */       }
/* 166 */       this.currentMp = newMp;
/*     */     }
/*     */     finally {
/*     */       
/* 170 */       this.mpLock.unlock();
/*     */     } 
/*     */     
/* 173 */     onReduceMp();
/*     */     
/* 175 */     return this.currentMp;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sendAttackStatusPacketUpdate(SM_ATTACK_STATUS.TYPE type, int value) {
/* 181 */     if (this.owner == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 186 */     PacketSendUtility.broadcastPacketAndReceive((VisibleObject)this.owner, (AionServerPacket)new SM_ATTACK_STATUS(this.owner, 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int increaseHp(SM_ATTACK_STATUS.TYPE type, int value) {
/* 196 */     this.hpLock.lock();
/*     */     
/*     */     try {
/* 199 */       if (isAlreadyDead())
/*     */       {
/* 201 */         return 0;
/*     */       }
/* 203 */       int newHp = this.currentHp + value;
/* 204 */       if (newHp > getMaxHp())
/*     */       {
/* 206 */         newHp = getMaxHp();
/*     */       }
/* 208 */       if (this.currentHp != newHp)
/*     */       {
/* 210 */         this.currentHp = newHp;
/* 211 */         onIncreaseHp(type, value);
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 216 */       this.hpLock.unlock();
/*     */     } 
/* 218 */     return this.currentHp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int increaseMp(SM_ATTACK_STATUS.TYPE type, int value) {
/* 228 */     this.mpLock.lock();
/*     */ 
/*     */     
/*     */     try {
/* 232 */       if (isAlreadyDead())
/*     */       {
/* 234 */         return 0;
/*     */       }
/* 236 */       int newMp = this.currentMp + value;
/* 237 */       if (newMp > getMaxMp())
/*     */       {
/* 239 */         newMp = getMaxMp();
/*     */       }
/* 241 */       if (this.currentMp != newMp)
/*     */       {
/* 243 */         this.currentMp = newMp;
/* 244 */         onIncreaseMp(type, value);
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 249 */       this.mpLock.unlock();
/*     */     } 
/*     */     
/* 252 */     return this.currentMp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void restoreHp() {
/* 260 */     increaseHp(SM_ATTACK_STATUS.TYPE.NATURAL_HP, getOwner().getGameStats().getCurrentStat(StatEnum.REGEN_HP));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void restoreMp() {
/* 268 */     increaseMp(SM_ATTACK_STATUS.TYPE.NATURAL_MP, getOwner().getGameStats().getCurrentStat(StatEnum.REGEN_MP));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void triggerRestoreTask() {
/* 276 */     if (this.lifeRestoreTask == null && !this.alreadyDead)
/*     */     {
/* 278 */       this.lifeRestoreTask = LifeStatsRestoreService.getInstance().scheduleRestoreTask(this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancelRestoreTask() {
/* 287 */     if (this.lifeRestoreTask != null && !this.lifeRestoreTask.isCancelled()) {
/*     */       
/* 289 */       this.lifeRestoreTask.cancel(false);
/* 290 */       this.lifeRestoreTask = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFullyRestoredHpMp() {
/* 300 */     return (getMaxHp() == this.currentHp && getMaxMp() == this.currentMp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFullyRestoredHp() {
/* 309 */     return (getMaxHp() == this.currentHp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void synchronizeWithMaxStats() {
/* 318 */     int maxHp = getMaxHp();
/* 319 */     if (this.currentHp != maxHp)
/* 320 */       this.currentHp = maxHp; 
/* 321 */     int maxMp = getMaxMp();
/* 322 */     if (this.currentMp != maxMp) {
/* 323 */       this.currentMp = maxMp;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateCurrentStats() {
/* 334 */     int maxHp = getMaxHp();
/* 335 */     if (maxHp < this.currentHp) {
/* 336 */       this.currentHp = maxHp;
/*     */     }
/* 338 */     int maxMp = getMaxMp();
/* 339 */     if (maxMp < this.currentMp) {
/* 340 */       this.currentMp = maxMp;
/*     */     }
/* 342 */     if (!isFullyRestoredHpMp()) {
/* 343 */       triggerRestoreTask();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHpPercentage() {
/* 352 */     return (int)(100L * this.currentHp / getMaxHp());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMpPercentage() {
/* 361 */     return 100 * this.currentMp / getMaxMp();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void onIncreaseMp(SM_ATTACK_STATUS.TYPE paramTYPE, int paramInt);
/*     */ 
/*     */   
/*     */   protected abstract void onReduceMp();
/*     */ 
/*     */   
/*     */   protected abstract void onIncreaseHp(SM_ATTACK_STATUS.TYPE paramTYPE, int paramInt);
/*     */ 
/*     */   
/*     */   protected abstract void onReduceHp();
/*     */ 
/*     */   
/*     */   public int increaseFp(int value) {
/* 379 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentFp() {
/* 387 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancelAllTasks() {
/* 395 */     cancelRestoreTask();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentHpPercent(int hpPercent) {
/* 406 */     this.hpLock.lock();
/*     */     
/*     */     try {
/* 409 */       int maxHp = getMaxHp();
/* 410 */       this.currentHp = (int)(maxHp * hpPercent / 100L);
/*     */       
/* 412 */       if (this.currentHp > 0) {
/* 413 */         this.alreadyDead = false;
/*     */       }
/*     */     } finally {
/*     */       
/* 417 */       this.hpLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentHp(int hp) {
/* 426 */     this.hpLock.lock();
/*     */     
/*     */     try {
/* 429 */       this.currentHp = hp;
/*     */       
/* 431 */       if (this.currentHp > 0) {
/* 432 */         this.alreadyDead = false;
/*     */       }
/* 434 */       if (this.currentHp < getMaxHp()) {
/* 435 */         onReduceHp();
/*     */       }
/*     */     } finally {
/*     */       
/* 439 */       this.hpLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int setCurrentMp(int value) {
/* 445 */     this.mpLock.lock();
/*     */     
/*     */     try {
/* 448 */       int newMp = value;
/*     */       
/* 450 */       if (newMp < 0) {
/* 451 */         newMp = 0;
/*     */       }
/* 453 */       this.currentMp = newMp;
/*     */     }
/*     */     finally {
/*     */       
/* 457 */       this.mpLock.unlock();
/*     */     } 
/*     */     
/* 460 */     onReduceMp();
/*     */     
/* 462 */     return this.currentMp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentMpPercent(int mpPercent) {
/* 472 */     this.mpLock.lock();
/*     */     
/*     */     try {
/* 475 */       int maxMp = getMaxMp();
/* 476 */       this.currentMp = maxMp * mpPercent / 100;
/*     */     }
/*     */     finally {
/*     */       
/* 480 */       this.mpLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void triggerRestoreOnRevive() {
/* 491 */     triggerRestoreTask();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\CreatureLifeStats.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */