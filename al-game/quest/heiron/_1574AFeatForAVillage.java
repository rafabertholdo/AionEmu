/*     */ package quest.heiron;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
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
/*     */ public class _1574AFeatForAVillage
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1574;
/*     */   
/*     */   public _1574AFeatForAVillage() {
/*  38 */     super(Integer.valueOf(1574));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  44 */     this.qe.setNpcQuestData(730025).addOnQuestStart(1574);
/*  45 */     this.qe.setNpcQuestData(730025).addOnTalkEvent(1574);
/*  46 */     this.qe.setNpcQuestData(204560).addOnTalkEvent(1574);
/*  47 */     this.qe.setNpcQuestData(204561).addOnTalkEvent(1574);
/*  48 */     this.qe.setNpcQuestData(204562).addOnTalkEvent(1574);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  54 */     Player player = env.getPlayer();
/*  55 */     int targetId = 0;
/*  56 */     if (env.getVisibleObject() instanceof Npc)
/*  57 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  58 */     QuestState qs = player.getQuestStateList().getQuestState(1574);
/*  59 */     if (targetId == 730025) {
/*     */       
/*  61 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/*  63 */         if (env.getDialogId().intValue() == 25) {
/*  64 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  66 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  68 */       if (qs.getStatus() == QuestStatus.START) {
/*     */         
/*  70 */         if (env.getDialogId().intValue() == 25)
/*  71 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  72 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  74 */           qs.setQuestVar(3);
/*  75 */           qs.setStatus(QuestStatus.REWARD);
/*  76 */           updateQuestStatus(player, qs);
/*  77 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/*  80 */         return defaultQuestEndDialog(env);
/*     */       } 
/*  82 */       if (qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/*  84 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     }
/*  87 */     else if (targetId == 204560) {
/*     */       
/*  89 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
/*     */       {
/*  91 */         if (env.getDialogId().intValue() == 25)
/*  92 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  93 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  95 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  96 */           updateQuestStatus(player, qs);
/*  97 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/*  99 */           return true;
/*     */         } 
/*     */         
/* 102 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 105 */     } else if (targetId == 204561) {
/*     */       
/* 107 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
/*     */       {
/* 109 */         if (env.getDialogId().intValue() == 25)
/* 110 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 111 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/* 113 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 114 */           updateQuestStatus(player, qs);
/* 115 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 117 */           return true;
/*     */         } 
/*     */         
/* 120 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 123 */     } else if (targetId == 204562) {
/*     */       
/* 125 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2) {
/*     */         
/* 127 */         if (env.getDialogId().intValue() == 25)
/* 128 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 129 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/* 131 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 132 */           updateQuestStatus(player, qs);
/* 133 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 135 */           return true;
/*     */         } 
/*     */         
/* 138 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     } 
/*     */     
/* 142 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\heiron\_1574AFeatForAVillage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */