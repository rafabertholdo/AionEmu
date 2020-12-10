/*     */ package com.aionemu.gameserver.model.gameobjects;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class DropNpc
/*     */ {
/*  29 */   private List<Integer> allowedList = new ArrayList<Integer>();
/*  30 */   private List<Player> inRangePlayers = new ArrayList<Player>();
/*  31 */   private List<Player> playerStatus = new ArrayList<Player>();
/*  32 */   private Player lootingPlayer = null;
/*  33 */   private int distributionType = 0;
/*     */   private boolean inUse = false;
/*  35 */   private int currentIndex = 0;
/*  36 */   private int groupSize = 0;
/*     */ 
/*     */   
/*     */   public DropNpc(List<Integer> allowedList) {
/*  40 */     this.allowedList = allowedList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFreeLooting() {
/*  48 */     this.allowedList = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsKey(int playerObjId) {
/*  56 */     if (this.allowedList == null)
/*  57 */       return true; 
/*  58 */     return this.allowedList.contains(Integer.valueOf(playerObjId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBeingLooted(Player player) {
/*  67 */     this.lootingPlayer = player;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getBeingLooted() {
/*  75 */     return this.lootingPlayer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBeingLooted() {
/*  83 */     return (this.lootingPlayer != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDistributionType(int distributionType) {
/*  91 */     this.distributionType = distributionType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDistributionType() {
/*  99 */     return this.distributionType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void isInUse(boolean inUse) {
/* 107 */     this.inUse = inUse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInUse() {
/* 115 */     return this.inUse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentIndex(int currentIndex) {
/* 123 */     this.currentIndex = currentIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentIndex() {
/* 131 */     return this.currentIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGroupSize(int groupSize) {
/* 139 */     this.groupSize = groupSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getGroupSize() {
/* 147 */     return this.groupSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInRangePlayers(List<Player> inRangePlayers) {
/* 155 */     this.inRangePlayers = inRangePlayers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Player> getInRangePlayers() {
/* 163 */     return this.inRangePlayers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPlayerStatus(Player player) {
/* 171 */     this.playerStatus.add(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void delPlayerStatus(Player player) {
/* 179 */     this.playerStatus.remove(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Player> getPlayerStatus() {
/* 187 */     return this.playerStatus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsPlayerStatus(Player player) {
/* 195 */     return this.playerStatus.contains(player);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\DropNpc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */