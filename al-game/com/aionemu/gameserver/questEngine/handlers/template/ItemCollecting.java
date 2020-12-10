/*     */ package com.aionemu.gameserver.questEngine.handlers.template;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.QuestTemplate;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.QuestService;
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
/*     */ public class ItemCollecting
/*     */   extends QuestHandler
/*     */ {
/*     */   private final int questId;
/*     */   private final int startNpcId;
/*     */   private final int actionItemId;
/*     */   private final int endNpcId;
/*     */   
/*     */   public ItemCollecting(int questId, int startNpcId, int actionItemId, int endNpcId) {
/*  49 */     super(Integer.valueOf(questId));
/*  50 */     this.questId = questId;
/*  51 */     this.startNpcId = startNpcId;
/*  52 */     this.actionItemId = actionItemId;
/*  53 */     if (endNpcId != 0) {
/*  54 */       this.endNpcId = endNpcId;
/*     */     } else {
/*  56 */       this.endNpcId = startNpcId;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void register() {
/*  62 */     this.qe.setNpcQuestData(this.startNpcId).addOnQuestStart(this.questId);
/*  63 */     this.qe.setNpcQuestData(this.startNpcId).addOnTalkEvent(this.questId);
/*  64 */     if (this.actionItemId != 0)
/*  65 */       this.qe.setNpcQuestData(this.actionItemId).addOnTalkEvent(this.questId); 
/*  66 */     if (this.endNpcId != this.startNpcId) {
/*  67 */       this.qe.setNpcQuestData(this.endNpcId).addOnTalkEvent(this.questId);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  73 */     Player player = env.getPlayer();
/*  74 */     int targetId = 0;
/*  75 */     if (env.getVisibleObject() instanceof Npc)
/*  76 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  77 */     QuestState qs = player.getQuestStateList().getQuestState(this.questId);
/*  78 */     QuestTemplate template = DataManager.QUEST_DATA.getQuestById(this.questId);
/*  79 */     if (qs == null || qs.getStatus() == QuestStatus.NONE || (qs.getStatus() == QuestStatus.COMPLETE && qs.getCompliteCount() <= template.getMaxRepeatCount().intValue())) {
/*     */       
/*  81 */       if (targetId == this.startNpcId)
/*     */       {
/*  83 */         if (env.getDialogId().intValue() == 25) {
/*  84 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  86 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  89 */     } else if (qs != null && qs.getStatus() == QuestStatus.START) {
/*     */       
/*  91 */       if (targetId == this.endNpcId) {
/*     */         
/*  93 */         if (env.getDialogId().intValue() == 25)
/*  94 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  95 */         if (env.getDialogId().intValue() == 33)
/*     */         {
/*  97 */           if (QuestService.collectItemCheck(env, true)) {
/*     */             
/*  99 */             qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 100 */             qs.setStatus(QuestStatus.REWARD);
/* 101 */             updateQuestStatus(player, qs);
/* 102 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */           } 
/*     */           
/* 105 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
/*     */         }
/*     */       
/* 108 */       } else if (targetId == this.actionItemId && targetId != 0) {
/* 109 */         return true;
/*     */       } 
/* 111 */     } else if (qs.getStatus() == QuestStatus.REWARD && targetId == this.endNpcId) {
/*     */       
/* 113 */       return defaultQuestEndDialog(env);
/*     */     } 
/* 115 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\template\ItemCollecting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */