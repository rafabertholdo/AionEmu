/*     */ package quest.ishalgen;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
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
/*     */ public class _2107ReturntoSender
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2107;
/*     */   
/*     */   public _2107ReturntoSender() {
/*  43 */     super(Integer.valueOf(2107));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  49 */     this.qe.setNpcQuestData(203516).addOnTalkEvent(2107);
/*  50 */     this.qe.setNpcQuestData(203512).addOnTalkEvent(2107);
/*  51 */     this.qe.setQuestItemIds(182203107).add(2107);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  57 */     Player player = env.getPlayer();
/*  58 */     QuestState qs = player.getQuestStateList().getQuestState(2107);
/*     */     
/*  60 */     int targetId = 0;
/*  61 */     if (env.getVisibleObject() instanceof Npc)
/*  62 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  63 */     if (targetId == 0) {
/*     */       
/*  65 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 1002:
/*  68 */           QuestService.startQuest(env, QuestStatus.START);
/*  69 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
/*  70 */           return true;
/*     */         case 1003:
/*  72 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(0, 0));
/*  73 */           return true;
/*     */       } 
/*     */     
/*  76 */     } else if (targetId == 203516) {
/*     */       
/*  78 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
/*     */       {
/*  80 */         if (env.getDialogId().intValue() == 25)
/*  81 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  82 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  84 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  85 */           updateQuestStatus(player, qs);
/*  86 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  87 */           return true;
/*     */         } 
/*     */         
/*  90 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  93 */     } else if (targetId == 203512) {
/*     */       
/*  95 */       if (qs != null) {
/*     */         
/*  97 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/*  98 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  99 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/* 101 */           qs.setQuestVar(2);
/* 102 */           qs.setStatus(QuestStatus.REWARD);
/* 103 */           ItemService.removeItemFromInventoryByItemId(player, 182203107);
/* 104 */           updateQuestStatus(player, qs);
/* 105 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/* 108 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, Item item) {
/* 117 */     Player player = env.getPlayer();
/* 118 */     int id = item.getItemTemplate().getTemplateId();
/* 119 */     int itemObjId = item.getObjectId();
/* 120 */     QuestState qs = player.getQuestStateList().getQuestState(2107);
/*     */     
/* 122 */     if (id != 182203107)
/* 123 */       return false; 
/* 124 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 20, 1, 0), true);
/* 125 */     if (qs == null || qs.getStatus() == QuestStatus.NONE)
/* 126 */       sendQuestDialog(player, 0, 4); 
/* 127 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ishalgen\_2107ReturntoSender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */