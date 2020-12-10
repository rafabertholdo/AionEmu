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
/*     */ 
/*     */ public class _1097SwordofTranscendence
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1097;
/*     */   
/*     */   public _1097SwordofTranscendence() {
/*  43 */     super(Integer.valueOf(1097));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  49 */     this.qe.addQuestLvlUp(1097);
/*  50 */     this.qe.setNpcQuestData(790001).addOnQuestStart(1097);
/*  51 */     this.qe.setNpcQuestData(790001).addOnTalkEvent(1097);
/*  52 */     this.qe.setNpcQuestData(798316).addOnTalkEvent(1097);
/*  53 */     this.qe.setNpcQuestData(279034).addOnTalkEvent(1097);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  59 */     Player player = env.getPlayer();
/*  60 */     QuestState qs = player.getQuestStateList().getQuestState(1097);
/*  61 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED) {
/*  62 */       return false;
/*     */     }
/*  64 */     QuestState qs2 = player.getQuestStateList().getQuestState(1096);
/*  65 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/*  66 */       return false; 
/*  67 */     qs.setStatus(QuestStatus.START);
/*  68 */     updateQuestStatus(player, qs);
/*  69 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  75 */     Player player = env.getPlayer();
/*  76 */     int targetId = 0;
/*  77 */     if (env.getVisibleObject() instanceof Npc)
/*  78 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  79 */     QuestState qs = player.getQuestStateList().getQuestState(1097);
/*  80 */     if (targetId == 790001) {
/*     */       
/*  82 */       if (qs == null || qs.getStatus() == QuestStatus.START) {
/*     */         
/*  84 */         if (env.getDialogId().intValue() == 25)
/*  85 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  86 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  88 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  89 */           updateQuestStatus(player, qs);
/*  90 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/*  92 */           return true;
/*     */         } 
/*     */ 
/*     */         
/*  96 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  98 */       if (qs != null && qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/* 100 */         if (env.getDialogId().intValue() == 25)
/* 101 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
/* 102 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/* 104 */           qs.setQuestVar(3);
/* 105 */           qs.setStatus(QuestStatus.REWARD);
/* 106 */           updateQuestStatus(player, qs);
/* 107 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/* 110 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     
/*     */     }
/* 114 */     else if (targetId == 798316) {
/*     */ 
/*     */       
/* 117 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
/*     */       {
/* 119 */         if (env.getDialogId().intValue() == 25)
/* 120 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 121 */         if (env.getDialogId().intValue() == 10001) {
/*     */           
/* 123 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 124 */           updateQuestStatus(player, qs);
/* 125 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 127 */           return true;
/*     */         } 
/*     */         
/* 130 */         return defaultQuestStartDialog(env);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 135 */     else if (targetId == 279034) {
/*     */       
/* 137 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2) {
/*     */         
/* 139 */         if (env.getDialogId().intValue() == 25)
/* 140 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 141 */         if (env.getDialogId().intValue() == 33) {
/*     */           
/* 143 */           if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182206058, 1))));
/*     */           
/* 145 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 146 */           updateQuestStatus(player, qs);
/* 147 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 149 */           return true;
/*     */         } 
/*     */         
/* 152 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     } 
/* 155 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\poeta\_1097SwordofTranscendence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */