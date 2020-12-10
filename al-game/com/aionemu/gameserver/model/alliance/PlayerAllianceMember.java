/*     */ package com.aionemu.gameserver.model.alliance;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.AionObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
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
/*     */ public class PlayerAllianceMember
/*     */   extends AionObject
/*     */ {
/*     */   private Player player;
/*     */   private String name;
/*     */   private int allianceId;
/*     */   private PlayerCommonData playerCommonData;
/*     */   
/*     */   public PlayerAllianceMember(Player player) {
/*  40 */     super(Integer.valueOf(player.getObjectId()));
/*  41 */     this.player = player;
/*  42 */     this.name = player.getName();
/*  43 */     this.playerCommonData = player.getCommonData();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  49 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getPlayer() {
/*  57 */     return this.player;
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
/*     */   public void onLogin(Player player) {
/*  69 */     this.player = player;
/*  70 */     this.playerCommonData = player.getCommonData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisconnect() {
/*  79 */     this.player = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOnline() {
/*  87 */     return (this.player != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerCommonData getCommonData() {
/*  95 */     return this.playerCommonData;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAllianceId() {
/* 103 */     return this.allianceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAllianceId(int allianceId) {
/* 111 */     this.allianceId = allianceId;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\alliance\PlayerAllianceMember.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */