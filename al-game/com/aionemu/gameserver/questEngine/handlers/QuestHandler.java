/*     */ package com.aionemu.gameserver.questEngine.handlers;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestWorkItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACCEPTED;
/*     */ import com.aionemu.gameserver.questEngine.QuestEngine;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.QuestService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.world.zone.ZoneName;
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
/*     */ public class QuestHandler
/*     */ {
/*     */   private final Integer questId;
/*     */   protected QuestEngine qe;
/*     */   protected int[] deletebleItems;
/*     */   
/*     */   protected QuestHandler(Integer questId) {
/*  55 */     this.questId = questId;
/*  56 */     this.qe = QuestEngine.getInstance();
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteQuestItems(QuestEnv env) {
/*  61 */     QuestWorkItems qwi = DataManager.QUEST_DATA.getQuestById(env.getQuestId().intValue()).getQuestWorkItems();
/*     */     
/*  63 */     Player player = env.getPlayer();
/*  64 */     if (qwi != null)
/*     */     {
/*  66 */       for (QuestItems qi : qwi.getQuestWorkItem()) {
/*     */         
/*  68 */         if (qi != null)
/*     */         {
/*  70 */           ItemService.removeItemFromInventoryByItemId(player, qi.getItemId().intValue());
/*     */         }
/*     */       } 
/*     */     }
/*  74 */     for (int itemId : this.deletebleItems)
/*     */     {
/*  76 */       ItemService.removeItemFromInventoryByItemId(player, itemId);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void updateQuestStatus(Player player, QuestState qs) {
/*  82 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_QUEST_ACCEPTED(this.questId.intValue(), qs.getStatus(), qs.getQuestVars().getQuestVars()));
/*  83 */     if (qs.getStatus() == QuestStatus.COMPLETE) {
/*  84 */       player.getController().updateNearbyQuests();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean sendQuestDialog(Player player, int objId, int dialogId) {
/*  89 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(objId, dialogId, this.questId.intValue()));
/*  90 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean defaultQuestStartDialog(QuestEnv env) {
/*  96 */     Player player = env.getPlayer();
/*     */     
/*  98 */     int targetObjId = (env.getVisibleObject() == null) ? 0 : env.getVisibleObject().getObjectId();
/*  99 */     switch (env.getDialogId().intValue()) {
/*     */       
/*     */       case 1007:
/* 102 */         return sendQuestDialog(player, targetObjId, 4);
/*     */       case 1002:
/* 104 */         if (QuestService.startQuest(env, QuestStatus.START)) {
/* 105 */           return sendQuestDialog(player, targetObjId, 1003);
/*     */         }
/* 107 */         return false;
/*     */       case 1003:
/* 109 */         return sendQuestDialog(player, targetObjId, 1004);
/*     */     } 
/* 111 */     return false;
/*     */   }
/*     */   
/*     */   public boolean defaultQuestEndDialog(QuestEnv env) {
/*     */     QuestState qs;
/* 116 */     Player player = env.getPlayer();
/* 117 */     int targetObjId = (env.getVisibleObject() == null) ? 0 : env.getVisibleObject().getObjectId();
/* 118 */     switch (env.getDialogId().intValue()) {
/*     */       
/*     */       case 8:
/*     */       case 9:
/*     */       case 10:
/*     */       case 11:
/*     */       case 12:
/*     */       case 13:
/*     */       case 14:
/*     */       case 15:
/*     */       case 16:
/*     */       case 17:
/* 130 */         if (QuestService.questFinish(env)) {
/*     */           
/* 132 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(targetObjId, 10));
/* 133 */           return true;
/*     */         } 
/* 135 */         return false;
/*     */       case -1:
/*     */       case 1009:
/* 138 */         qs = player.getQuestStateList().getQuestState(this.questId.intValue());
/* 139 */         if (qs != null && qs.getStatus() == QuestStatus.REWARD)
/*     */         {
/* 141 */           return sendQuestDialog(player, targetObjId, 5); } 
/*     */         break;
/*     */     } 
/* 144 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getQuestId() {
/* 151 */     return this.questId;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv questEnv) {
/* 156 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onEnterWorldEvent(QuestEnv questEnv) {
/* 161 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onEnterZoneEvent(QuestEnv questEnv, ZoneName zoneName) {
/* 166 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv questEnv, Item item) {
/* 171 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv questEnv) {
/* 176 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onAttackEvent(QuestEnv questEnv) {
/* 181 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv questEnv) {
/* 186 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onDieEvent(QuestEnv questEnv) {
/* 191 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onMovieEndEvent(QuestEnv questEnv, int movieId) {
/* 196 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onQuestFinishEvent(QuestEnv questEnv) {
/* 201 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onQuestAbortEvent(QuestEnv questEnv) {
/* 206 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onQuestTimerEndEvent(QuestEnv questEnv) {
/* 211 */     return false;
/*     */   }
/*     */   
/*     */   public void register() {}
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\QuestHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */