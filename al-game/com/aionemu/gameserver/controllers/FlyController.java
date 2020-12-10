/*     */ package com.aionemu.gameserver.controllers;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_STATS_INFO;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
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
/*     */ public class FlyController
/*     */ {
/*     */   private Player player;
/*     */   
/*     */   public FlyController(Player player) {
/*  36 */     this.player = player;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onStopGliding() {
/*  41 */     if (this.player.isInState(CreatureState.GLIDING)) {
/*     */       
/*  43 */       this.player.unsetState(CreatureState.GLIDING);
/*     */       
/*  45 */       if (this.player.isInState(CreatureState.FLYING)) {
/*     */         
/*  47 */         this.player.setFlyState(1);
/*     */       }
/*     */       else {
/*     */         
/*  51 */         this.player.setFlyState(0);
/*  52 */         this.player.getLifeStats().triggerFpRestore();
/*     */       } 
/*     */       
/*  55 */       PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_STATS_INFO(this.player));
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
/*     */   public void endFly() {
/*  68 */     if (this.player.isInState(CreatureState.FLYING) || this.player.isInState(CreatureState.GLIDING)) {
/*     */       
/*  70 */       PacketSendUtility.broadcastPacket(this.player, (AionServerPacket)new SM_EMOTION((Creature)this.player, EmotionType.LAND, 0, 0), true);
/*  71 */       this.player.unsetState(CreatureState.FLYING);
/*  72 */       this.player.unsetState(CreatureState.GLIDING);
/*  73 */       this.player.setFlyState(0);
/*     */ 
/*     */       
/*  76 */       PacketSendUtility.broadcastPacket(this.player, (AionServerPacket)new SM_EMOTION((Creature)this.player, EmotionType.START_EMOTE2, 0, 0), true);
/*  77 */       PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_STATS_INFO(this.player));
/*     */       
/*  79 */       this.player.getLifeStats().triggerFpRestore();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startFly() {
/*  89 */     this.player.setState(CreatureState.FLYING);
/*  90 */     this.player.setFlyState(1);
/*  91 */     this.player.getLifeStats().triggerFpReduce();
/*  92 */     PacketSendUtility.broadcastPacket(this.player, (AionServerPacket)new SM_EMOTION((Creature)this.player, EmotionType.START_EMOTE2, 0, 0), true);
/*  93 */     PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_STATS_INFO(this.player));
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
/*     */   
/*     */   public void switchToGliding() {
/* 108 */     if (!this.player.isInState(CreatureState.GLIDING)) {
/*     */       
/* 110 */       this.player.setState(CreatureState.GLIDING);
/* 111 */       if (this.player.getFlyState() == 0)
/* 112 */         this.player.getLifeStats().triggerFpReduce(); 
/* 113 */       this.player.setFlyState(2);
/*     */       
/* 115 */       PacketSendUtility.sendPacket(this.player, (AionServerPacket)new SM_STATS_INFO(this.player));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\FlyController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */