/*     */ package com.aionemu.gameserver.model.gameobjects.stats;
/*     */ 
/*     */ import com.aionemu.gameserver.configs.administration.AdminConfig;
/*     */ import com.aionemu.gameserver.model.alliance.PlayerAllianceEvent;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
/*     */ import com.aionemu.gameserver.model.group.GroupEvent;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_FLY_TIME;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_STATUPDATE_HP;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_STATUPDATE_MP;
/*     */ import com.aionemu.gameserver.services.AllianceService;
/*     */ import com.aionemu.gameserver.services.LifeStatsRestoreService;
/*     */ import com.aionemu.gameserver.taskmanager.tasks.PacketBroadcaster;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.locks.ReentrantLock;
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
/*     */ public class PlayerLifeStats
/*     */   extends CreatureLifeStats<Player>
/*     */ {
/*     */   protected int currentFp;
/*  44 */   private final ReentrantLock fpLock = new ReentrantLock();
/*     */   
/*     */   private Future<?> flyRestoreTask;
/*     */   
/*     */   private Future<?> flyReduceTask;
/*     */   
/*     */   public PlayerLifeStats(Player owner, int currentHp, int currentMp, int currentFp) {
/*  51 */     super((Creature)owner, currentHp, currentMp);
/*  52 */     this.currentFp = currentFp;
/*     */   }
/*     */ 
/*     */   
/*     */   public PlayerLifeStats(Player owner) {
/*  57 */     super((Creature)owner, owner.getGameStats().getCurrentStat(StatEnum.MAXHP), owner.getGameStats().getCurrentStat(StatEnum.MAXMP));
/*     */     
/*  59 */     this.currentFp = owner.getGameStats().getCurrentStat(StatEnum.FLY_TIME);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onReduceHp() {
/*  65 */     sendHpPacketUpdate();
/*  66 */     triggerRestoreTask();
/*  67 */     sendGroupPacketUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onReduceMp() {
/*  73 */     sendMpPacketUpdate();
/*  74 */     triggerRestoreTask();
/*  75 */     sendGroupPacketUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onIncreaseMp(SM_ATTACK_STATUS.TYPE type, int value) {
/*  81 */     sendMpPacketUpdate();
/*  82 */     sendAttackStatusPacketUpdate(type, value);
/*  83 */     sendGroupPacketUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onIncreaseHp(SM_ATTACK_STATUS.TYPE type, int value) {
/*  89 */     if (isFullyRestoredHp())
/*     */     {
/*     */       
/*  92 */       this.owner.getAggroList().clear();
/*     */     }
/*  94 */     sendHpPacketUpdate();
/*  95 */     sendAttackStatusPacketUpdate(type, value);
/*  96 */     sendGroupPacketUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   private void sendGroupPacketUpdate() {
/* 101 */     Player owner = getOwner();
/* 102 */     if (owner.isInGroup())
/* 103 */       owner.getPlayerGroup().updateGroupUIToEvent(owner, GroupEvent.MOVEMENT); 
/* 104 */     if (owner.isInAlliance()) {
/* 105 */       AllianceService.getInstance().updateAllianceUIToEvent(owner, PlayerAllianceEvent.MOVEMENT);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Player getOwner() {
/* 111 */     return (Player)super.getOwner();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void restoreHp() {
/* 117 */     int currentRegenHp = getOwner().getGameStats().getCurrentStat(StatEnum.REGEN_HP);
/* 118 */     if (getOwner().isInState(CreatureState.RESTING))
/* 119 */       currentRegenHp *= 8; 
/* 120 */     increaseHp(SM_ATTACK_STATUS.TYPE.NATURAL_HP, currentRegenHp);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void restoreMp() {
/* 126 */     int currentRegenMp = getOwner().getGameStats().getCurrentStat(StatEnum.REGEN_MP);
/* 127 */     if (getOwner().isInState(CreatureState.RESTING))
/* 128 */       currentRegenMp *= 8; 
/* 129 */     increaseMp(SM_ATTACK_STATUS.TYPE.NATURAL_MP, currentRegenMp);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void synchronizeWithMaxStats() {
/* 135 */     if (isAlreadyDead()) {
/*     */       return;
/*     */     }
/* 138 */     super.synchronizeWithMaxStats();
/* 139 */     int maxFp = getMaxFp();
/* 140 */     if (this.currentFp != maxFp) {
/* 141 */       this.currentFp = maxFp;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateCurrentStats() {
/* 147 */     super.updateCurrentStats();
/*     */     
/* 149 */     if (getMaxFp() < this.currentFp) {
/* 150 */       this.currentFp = getMaxFp();
/*     */     }
/* 152 */     if (!this.owner.isInState(CreatureState.FLYING)) {
/* 153 */       triggerFpRestore();
/*     */     }
/*     */   }
/*     */   
/*     */   public void sendHpPacketUpdate() {
/* 158 */     this.owner.addPacketBroadcastMask(PacketBroadcaster.BroadcastMode.UPDATE_PLAYER_HP_STAT);
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendHpPacketUpdateImpl() {
/* 163 */     if (this.owner == null) {
/*     */       return;
/*     */     }
/* 166 */     PacketSendUtility.sendPacket((Player)this.owner, (AionServerPacket)new SM_STATUPDATE_HP(this.currentHp, getMaxHp()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendMpPacketUpdate() {
/* 171 */     this.owner.addPacketBroadcastMask(PacketBroadcaster.BroadcastMode.UPDATE_PLAYER_MP_STAT);
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendMpPacketUpdateImpl() {
/* 176 */     if (this.owner == null) {
/*     */       return;
/*     */     }
/* 179 */     PacketSendUtility.sendPacket((Player)this.owner, (AionServerPacket)new SM_STATUPDATE_MP(this.currentMp, getMaxMp()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentFp() {
/* 189 */     return this.currentFp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxFp() {
/* 198 */     return this.owner.getGameStats().getCurrentStat(StatEnum.FLY_TIME);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFpPercentage() {
/* 206 */     return 100 * this.currentFp / getMaxFp();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int increaseFp(int value) {
/* 217 */     this.fpLock.lock();
/*     */ 
/*     */     
/*     */     try {
/* 221 */       if (isAlreadyDead())
/*     */       {
/* 223 */         return 0;
/*     */       }
/* 225 */       int newFp = this.currentFp + value;
/* 226 */       if (newFp > getMaxFp())
/*     */       {
/* 228 */         newFp = getMaxFp();
/*     */       }
/* 230 */       if (this.currentFp != newFp)
/*     */       {
/* 232 */         this.currentFp = newFp;
/* 233 */         onIncreaseFp();
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 238 */       this.fpLock.unlock();
/*     */     } 
/*     */     
/* 241 */     return this.currentFp;
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
/*     */   public int reduceFp(int value) {
/* 253 */     this.fpLock.lock();
/*     */     
/*     */     try {
/* 256 */       int newFp = this.currentFp - value;
/*     */       
/* 258 */       if (newFp < 0) {
/* 259 */         newFp = 0;
/*     */       }
/* 261 */       this.currentFp = newFp;
/*     */     }
/*     */     finally {
/*     */       
/* 265 */       this.fpLock.unlock();
/*     */     } 
/*     */     
/* 268 */     onReduceFp();
/*     */     
/* 270 */     return this.currentFp;
/*     */   }
/*     */ 
/*     */   
/*     */   public int setCurrentFp(int value) {
/* 275 */     this.fpLock.lock();
/*     */     
/*     */     try {
/* 278 */       int newFp = value;
/*     */       
/* 280 */       if (newFp < 0) {
/* 281 */         newFp = 0;
/*     */       }
/* 283 */       this.currentFp = newFp;
/*     */     }
/*     */     finally {
/*     */       
/* 287 */       this.fpLock.unlock();
/*     */     } 
/*     */     
/* 290 */     onReduceFp();
/*     */     
/* 292 */     return this.currentFp;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onIncreaseFp() {
/* 297 */     this.owner.addPacketBroadcastMask(PacketBroadcaster.BroadcastMode.UPDATE_PLAYER_FLY_TIME);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onReduceFp() {
/* 302 */     this.owner.addPacketBroadcastMask(PacketBroadcaster.BroadcastMode.UPDATE_PLAYER_FLY_TIME);
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendFpPacketUpdateImpl() {
/* 307 */     if (this.owner == null) {
/*     */       return;
/*     */     }
/* 310 */     PacketSendUtility.sendPacket((Player)this.owner, (AionServerPacket)new SM_FLY_TIME(this.currentFp, getMaxFp()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void restoreFp() {
/* 319 */     increaseFp(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void triggerFpRestore() {
/* 324 */     cancelFpReduce();
/*     */     
/* 326 */     if (this.flyRestoreTask == null && !this.alreadyDead && !isFlyTimeFullyRestored())
/*     */     {
/* 328 */       this.flyRestoreTask = LifeStatsRestoreService.getInstance().scheduleFpRestoreTask(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void cancelFpRestore() {
/* 334 */     if (this.flyRestoreTask != null && !this.flyRestoreTask.isCancelled()) {
/*     */       
/* 336 */       this.flyRestoreTask.cancel(false);
/* 337 */       this.flyRestoreTask = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void triggerFpReduce() {
/* 343 */     cancelFpRestore();
/*     */     
/* 345 */     if (this.flyReduceTask == null && !this.alreadyDead && getOwner().getAccessLevel() < AdminConfig.GM_FLIGHT_UNLIMITED)
/*     */     {
/*     */       
/* 348 */       this.flyReduceTask = LifeStatsRestoreService.getInstance().scheduleFpReduceTask(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void cancelFpReduce() {
/* 354 */     if (this.flyReduceTask != null && !this.flyReduceTask.isCancelled()) {
/*     */       
/* 356 */       this.flyReduceTask.cancel(false);
/* 357 */       this.flyReduceTask = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFlyTimeFullyRestored() {
/* 363 */     return (getMaxFp() == this.currentFp);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancelAllTasks() {
/* 369 */     super.cancelAllTasks();
/* 370 */     cancelFpReduce();
/* 371 */     cancelFpRestore();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void triggerRestoreOnRevive() {
/* 377 */     super.triggerRestoreOnRevive();
/* 378 */     triggerFpRestore();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void sendAttackStatusPacketUpdate(SM_ATTACK_STATUS.TYPE type, int value) {
/* 384 */     if (this.owner == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 389 */     PacketSendUtility.sendPacket((Player)this.owner, (AionServerPacket)new SM_ATTACK_STATUS(this.owner, type, 0, value));
/* 390 */     PacketSendUtility.broadcastPacket((VisibleObject)this.owner, (AionServerPacket)new SM_ATTACK_STATUS(this.owner, 0));
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\PlayerLifeStats.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */