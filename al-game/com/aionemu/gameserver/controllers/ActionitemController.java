/*    */ package com.aionemu.gameserver.controllers;
/*    */ 
/*    */ import com.aionemu.gameserver.model.EmotionType;
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
/*    */ import com.aionemu.gameserver.questEngine.QuestEngine;
/*    */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*    */ import com.aionemu.gameserver.services.DropService;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*    */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ActionitemController
/*    */   extends NpcController
/*    */ {
/* 35 */   private Player lastActor = null;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDialogRequest(final Player player) {
/* 45 */     if (!QuestEngine.getInstance().onDialog(new QuestEnv((VisibleObject)getOwner(), player, Integer.valueOf(0), Integer.valueOf(-1))))
/*    */       return; 
/* 47 */     int defaultUseTime = 3000;
/* 48 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), getOwner().getObjectId(), 3000, 1));
/*    */     
/* 50 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_QUESTLOOT, 0, getOwner().getObjectId()), true);
/* 51 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*    */         {
/*    */           public void run()
/*    */           {
/* 55 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), ActionitemController.this.getOwner().getObjectId(), 3000, 0));
/*    */             
/* 57 */             ActionitemController.this.getOwner().setTarget((VisibleObject)player);
/* 58 */             ActionitemController.this.lastActor = player;
/* 59 */             ActionitemController.this.onDie((Creature)player);
/*    */           }
/*    */         }3000L);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void doReward() {
/* 67 */     if (this.lastActor == null) {
/*    */       return;
/*    */     }
/* 70 */     DropService.getInstance().registerDrop(getOwner(), this.lastActor, this.lastActor.getLevel());
/* 71 */     DropService.getInstance().requestDropList(this.lastActor, getOwner().getObjectId());
/*    */     
/* 73 */     this.lastActor = null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onRespawn() {
/* 79 */     super.onRespawn();
/* 80 */     DropService.getInstance().unregisterDrop(getOwner());
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\ActionitemController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */