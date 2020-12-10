/*     */ package com.aionemu.gameserver.questEngine.handlers.template;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.QuestTemplate;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import java.util.Collections;
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
/*     */ public class ReportTo
/*     */   extends QuestHandler
/*     */ {
/*     */   private final int questId;
/*     */   private final int startNpc;
/*     */   private final int endNpc;
/*     */   private final int itemId;
/*     */   
/*     */   public ReportTo(int questId, int startNpc, int endNpc, int itemId) {
/*  50 */     super(Integer.valueOf(questId));
/*  51 */     this.startNpc = startNpc;
/*  52 */     this.endNpc = endNpc;
/*  53 */     this.questId = questId;
/*  54 */     this.itemId = itemId;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  60 */     this.qe.setNpcQuestData(this.startNpc).addOnQuestStart(this.questId);
/*  61 */     this.qe.setNpcQuestData(this.startNpc).addOnTalkEvent(this.questId);
/*  62 */     this.qe.setNpcQuestData(this.endNpc).addOnTalkEvent(this.questId);
/*  63 */     this.deletebleItems = new int[] { this.itemId };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  69 */     Player player = env.getPlayer();
/*  70 */     int targetId = 0;
/*  71 */     if (env.getVisibleObject() instanceof Npc)
/*  72 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  73 */     QuestState qs = player.getQuestStateList().getQuestState(this.questId);
/*  74 */     QuestTemplate template = DataManager.QUEST_DATA.getQuestById(this.questId);
/*  75 */     if (targetId == this.startNpc) {
/*     */       
/*  77 */       if (qs == null || qs.getStatus() == QuestStatus.NONE || (qs.getStatus() == QuestStatus.COMPLETE && qs.getCompliteCount() <= template.getMaxRepeatCount().intValue()))
/*     */       {
/*  79 */         if (env.getDialogId().intValue() == 25)
/*  80 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  81 */         if (env.getDialogId().intValue() == 1002 && this.itemId != 0) {
/*     */           
/*  83 */           if (ItemService.addItems(player, Collections.singletonList(new QuestItems(this.itemId, 1))))
/*  84 */             return defaultQuestStartDialog(env); 
/*  85 */           return true;
/*     */         } 
/*     */         
/*  88 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  91 */     } else if (targetId == this.endNpc) {
/*     */       
/*  93 */       if (qs != null) {
/*     */         
/*  95 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/*  96 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  97 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  99 */           if (this.itemId != 0)
/* 100 */             ItemService.removeItemFromInventoryByItemId(player, this.itemId); 
/* 101 */           qs.setQuestVar(1);
/* 102 */           qs.setStatus(QuestStatus.REWARD);
/* 103 */           updateQuestStatus(player, qs);
/* 104 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/* 107 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 110 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\template\ReportTo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */