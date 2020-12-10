/*     */ package com.aionemu.gameserver.model.drop;
/*     */ 
/*     */ import com.aionemu.commons.utils.Rnd;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
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
/*     */ public class DropItem
/*     */ {
/*  29 */   private int index = 0;
/*     */   
/*  31 */   private long count = 0L;
/*     */   
/*     */   private DropTemplate dropTemplate;
/*     */   
/*  35 */   private int playerObjId = 0;
/*     */   
/*     */   private boolean isFreeForAll = false;
/*     */   
/*  39 */   private long highestValue = 0L;
/*     */   
/*  41 */   private Player winningPlayer = null;
/*     */   
/*     */   private boolean isItemWonNotCollected = false;
/*     */   
/*     */   private boolean isDistributeItem = false;
/*     */ 
/*     */   
/*     */   public DropItem(DropTemplate dropTemplate) {
/*  49 */     this.dropTemplate = dropTemplate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void calculateCount(float rate) {
/*  59 */     if (Rnd.get() * 100.0F < this.dropTemplate.getChance() * rate)
/*     */     {
/*  61 */       this.count = Rnd.get(this.dropTemplate.getMin(), this.dropTemplate.getMax());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex() {
/*  70 */     return this.index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIndex(int index) {
/*  78 */     this.index = index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getCount() {
/*  86 */     return this.count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCount(long count) {
/*  94 */     this.count = count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DropTemplate getDropTemplate() {
/* 102 */     return this.dropTemplate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPlayerObjId() {
/* 110 */     return this.playerObjId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerObjId(int playerObjId) {
/* 118 */     this.playerObjId = playerObjId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void isFreeForAll(boolean isFreeForAll) {
/* 126 */     this.isFreeForAll = isFreeForAll;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFreeForAll() {
/* 134 */     return this.isFreeForAll;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getHighestValue() {
/* 142 */     return this.highestValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHighestValue(long highestValue) {
/* 150 */     this.highestValue = highestValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWinningPlayer(Player winningPlayer) {
/* 158 */     this.winningPlayer = winningPlayer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getWinningPlayer() {
/* 167 */     return this.winningPlayer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void isItemWonNotCollected(boolean isItemWonNotCollected) {
/* 175 */     this.isItemWonNotCollected = isItemWonNotCollected;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemWonNotCollected() {
/* 183 */     return this.isItemWonNotCollected;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void isDistributeItem(boolean isDistributeItem) {
/* 191 */     this.isDistributeItem = isDistributeItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDistributeItem() {
/* 199 */     return this.isDistributeItem;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\drop\DropItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */