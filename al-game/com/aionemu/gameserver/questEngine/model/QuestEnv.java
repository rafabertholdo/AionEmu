/*     */ package com.aionemu.gameserver.questEngine.model;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QuestEnv
/*     */ {
/*     */   private VisibleObject visibleObject;
/*     */   private Player player;
/*     */   private Integer questId;
/*     */   private Integer dialogId;
/*     */   
/*     */   public QuestEnv(VisibleObject visibleObject, Player player, Integer questId, Integer dialogId) {
/*  44 */     this.visibleObject = visibleObject;
/*  45 */     this.player = player;
/*  46 */     this.questId = questId;
/*  47 */     this.dialogId = dialogId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VisibleObject getVisibleObject() {
/*  55 */     return this.visibleObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVisibleObject(VisibleObject visibleObject) {
/*  63 */     this.visibleObject = visibleObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getPlayer() {
/*  71 */     return this.player;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayer(Player player) {
/*  79 */     this.player = player;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getQuestId() {
/*  87 */     return this.questId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setQuestId(Integer questId) {
/*  95 */     this.questId = questId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getDialogId() {
/* 103 */     return this.dialogId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDialogId(Integer dialogId) {
/* 111 */     this.dialogId = dialogId;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\model\QuestEnv.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */