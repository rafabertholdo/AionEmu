/*     */ package com.aionemu.gameserver.questEngine.handlers.template;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.CollectItem;
/*     */ import com.aionemu.gameserver.model.templates.quest.CollectItems;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.handlers.models.WorkOrdersData;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.QuestService;
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
/*     */ 
/*     */ 
/*     */ public class WorkOrders
/*     */   extends QuestHandler
/*     */ {
/*     */   private final WorkOrdersData workOrdersData;
/*     */   
/*     */   public WorkOrders(WorkOrdersData workOrdersData) {
/*  47 */     super(Integer.valueOf(workOrdersData.getId()));
/*  48 */     this.workOrdersData = workOrdersData;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  54 */     this.qe.setNpcQuestData(this.workOrdersData.getStartNpcId()).addOnQuestStart(this.workOrdersData.getId());
/*  55 */     this.qe.setNpcQuestData(this.workOrdersData.getStartNpcId()).addOnTalkEvent(this.workOrdersData.getId());
/*  56 */     this.qe.addOnQuestAbort(this.workOrdersData.getId());
/*  57 */     this.qe.addOnQuestFinish(this.workOrdersData.getId());
/*  58 */     int i = 0;
/*  59 */     CollectItems collectItems = DataManager.QUEST_DATA.getQuestById(this.workOrdersData.getId()).getCollectItems();
/*  60 */     int count = 0;
/*  61 */     if (collectItems != null)
/*     */     {
/*  63 */       count = collectItems.getCollectItem().size();
/*     */     }
/*  65 */     this.deletebleItems = new int[count + this.workOrdersData.getGiveComponent().size()];
/*  66 */     for (QuestItems questItem : this.workOrdersData.getGiveComponent())
/*     */     {
/*  68 */       this.deletebleItems[i++] = questItem.getItemId().intValue();
/*     */     }
/*  70 */     if (collectItems != null)
/*     */     {
/*  72 */       for (CollectItem item : collectItems.getCollectItem())
/*     */       {
/*  74 */         this.deletebleItems[i++] = item.getItemId().intValue();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  82 */     Player player = env.getPlayer();
/*  83 */     int targetId = 0;
/*  84 */     if (env.getVisibleObject() instanceof Npc)
/*  85 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  86 */     if (targetId != this.workOrdersData.getStartNpcId())
/*  87 */       return false; 
/*  88 */     QuestState qs = player.getQuestStateList().getQuestState(this.workOrdersData.getId());
/*  89 */     if (qs == null || qs.getStatus() == QuestStatus.NONE || qs.getStatus() == QuestStatus.COMPLETE) {
/*     */       
/*  91 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/*  94 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4);
/*     */         case 1002:
/*  96 */           if (QuestService.startQuest(env, QuestStatus.START)) {
/*     */             
/*  98 */             if (ItemService.addItems(player, this.workOrdersData.getGiveComponent())) {
/*     */               
/* 100 */               player.getRecipeList().addRecipe(player, DataManager.RECIPE_DATA.getRecipeTemplateById(this.workOrdersData.getRecipeId()));
/* 101 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/*     */             } 
/* 103 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 107 */     } else if (qs != null && qs.getStatus() == QuestStatus.START) {
/*     */       
/* 109 */       if (env.getDialogId().intValue() == 25)
/* 110 */         return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
/* 111 */       if (env.getDialogId().intValue() == 17)
/*     */       {
/* 113 */         if (QuestService.collectItemCheck(env, true)) {
/*     */ 
/*     */           
/* 116 */           qs.setStatus(QuestStatus.COMPLETE);
/* 117 */           abortQuest(env);
/* 118 */           qs.setCompliteCount(qs.getCompliteCount() + 1);
/* 119 */           updateQuestStatus(player, qs);
/* 120 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/* 121 */           return true;
/*     */         } 
/*     */       }
/*     */     } 
/* 125 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onQuestFinishEvent(QuestEnv env) {
/* 131 */     deleteQuestItems(env);
/* 132 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onQuestAbortEvent(QuestEnv env) {
/* 138 */     abortQuest(env);
/* 139 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void abortQuest(QuestEnv env) {
/* 144 */     env.getPlayer().getRecipeList().deleteRecipe(env.getPlayer(), this.workOrdersData.getRecipeId());
/* 145 */     deleteQuestItems(env);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\template\WorkOrders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */