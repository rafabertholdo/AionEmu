/*     */ package quest.poeta;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.QuestService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
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
/*     */ public class _1111InsomniaMedicine
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1111;
/*     */   
/*     */   public _1111InsomniaMedicine() {
/*  43 */     super(Integer.valueOf(1111));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  49 */     this.qe.setNpcQuestData(203075).addOnQuestStart(1111);
/*  50 */     this.qe.setNpcQuestData(203075).addOnTalkEvent(1111);
/*  51 */     this.qe.setNpcQuestData(203061).addOnTalkEvent(1111);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  57 */     Player player = env.getPlayer();
/*  58 */     int targetId = 0;
/*  59 */     if (env.getVisibleObject() instanceof Npc)
/*  60 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  61 */     QuestState qs = player.getQuestStateList().getQuestState(1111);
/*  62 */     if (targetId == 203075) {
/*     */       
/*  64 */       if (qs == null) {
/*     */         
/*  66 */         if (env.getDialogId().intValue() == 25) {
/*  67 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  69 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  71 */       if (qs.getStatus() == QuestStatus.REWARD) {
/*     */         
/*  73 */         if (env.getDialogId().intValue() == -1) {
/*     */           
/*  75 */           if (qs.getQuestVarById(0) == 2) {
/*     */             
/*  77 */             ItemService.removeItemFromInventoryByItemId(player, 182200222);
/*  78 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
/*     */           } 
/*  80 */           if (qs.getQuestVarById(0) == 3) {
/*     */             
/*  82 */             ItemService.removeItemFromInventoryByItemId(player, 182200221);
/*  83 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
/*     */           } 
/*  85 */           return false;
/*     */         } 
/*  87 */         if (env.getDialogId().intValue() == 1009)
/*  88 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), qs.getQuestVarById(0) + 3); 
/*  89 */         if (env.getDialogId().intValue() == 17)
/*     */         {
/*  91 */           QuestService.questFinish(env, qs.getQuestVarById(0) - 2);
/*  92 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  93 */           return true;
/*     */         }
/*     */       
/*     */       } 
/*  97 */     } else if (targetId == 203061) {
/*     */       
/*  99 */       if (env.getDialogId().intValue() == 25) {
/*     */         
/* 101 */         if (qs.getQuestVarById(0) == 0)
/* 102 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 103 */         if (qs.getQuestVarById(0) == 1)
/* 104 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1353); 
/* 105 */         return false;
/*     */       } 
/* 107 */       if (env.getDialogId().intValue() == 33) {
/*     */         
/* 109 */         if (QuestService.collectItemCheck(env, true)) {
/*     */           
/* 111 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 112 */           updateQuestStatus(player, qs);
/* 113 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1353);
/*     */         } 
/*     */         
/* 116 */         return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
/*     */       } 
/* 118 */       if (env.getDialogId().intValue() == 10000) {
/*     */         
/* 120 */         qs.setQuestVarById(0, 2);
/* 121 */         qs.setStatus(QuestStatus.REWARD);
/* 122 */         updateQuestStatus(player, qs);
/* 123 */         ItemService.addItems(player, Collections.singletonList(new QuestItems(182200222, 1)));
/* 124 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 125 */         return true;
/*     */       } 
/* 127 */       if (env.getDialogId().intValue() == 10001) {
/*     */         
/* 129 */         qs.setQuestVarById(0, 3);
/* 130 */         qs.setStatus(QuestStatus.REWARD);
/* 131 */         updateQuestStatus(player, qs);
/* 132 */         ItemService.addItems(player, Collections.singletonList(new QuestItems(182200221, 1)));
/* 133 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 134 */         return true;
/*     */       } 
/*     */     } 
/* 137 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\poeta\_1111InsomniaMedicine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */