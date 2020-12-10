/*     */ package com.aionemu.gameserver.model.gameobjects.player;
/*     */ 
/*     */ import com.aionemu.gameserver.model.PlayerClass;
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
/*     */ public class Friend
/*     */ {
/*     */   private final PlayerCommonData pcd;
/*     */   
/*     */   public Friend(PlayerCommonData pcd) {
/*  33 */     this.pcd = pcd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FriendList.Status getStatus() {
/*  42 */     if (this.pcd.getPlayer() == null || !this.pcd.isOnline())
/*     */     {
/*  44 */       return FriendList.Status.OFFLINE;
/*     */     }
/*  46 */     return this.pcd.getPlayer().getFriendList().getStatus();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  54 */     return this.pcd.getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLevel() {
/*  59 */     return this.pcd.getLevel();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNote() {
/*  64 */     return this.pcd.getNote();
/*     */   }
/*     */ 
/*     */   
/*     */   public PlayerClass getPlayerClass() {
/*  69 */     return this.pcd.getPlayerClass();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMapId() {
/*  74 */     return this.pcd.getPosition().getMapId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLastOnlineTime() {
/*  84 */     if (this.pcd.getLastOnline() == null || isOnline()) {
/*  85 */       return 0;
/*     */     }
/*  87 */     return (int)(this.pcd.getLastOnline().getTime() / 1000L);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOid() {
/*  92 */     return this.pcd.getPlayerObjId();
/*     */   }
/*     */ 
/*     */   
/*     */   public Player getPlayer() {
/*  97 */     return this.pcd.getPlayer();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOnline() {
/* 102 */     return this.pcd.isOnline();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\Friend.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */