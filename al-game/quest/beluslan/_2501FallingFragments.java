/*     */ package quest.beluslan;
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
/*     */ public class _2501FallingFragments
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2501;
/*     */   
/*     */   public _2501FallingFragments() {
/*  21 */     super(Integer.valueOf(2501));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  27 */     this.qe.setNpcQuestData(204701).addOnQuestStart(2501);
/*  28 */     this.qe.setNpcQuestData(204701).addOnTalkEvent(2501);
/*  29 */     this.qe.setNpcQuestData(204713).addOnTalkEvent(2501);
/*  30 */     this.qe.setNpcQuestData(204719).addOnTalkEvent(2501);
/*  31 */     this.qe.setNpcQuestData(204733).addOnTalkEvent(2501);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  37 */     Player player = env.getPlayer();
/*  38 */     int targetId = 0;
/*  39 */     if (env.getVisibleObject() instanceof Npc)
/*  40 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  41 */     QuestState qs = player.getQuestStateList().getQuestState(2501);
/*  42 */     if (targetId == 204701) {
/*     */       
/*  44 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/*  46 */         if (env.getDialogId().intValue() == 25) {
/*  47 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  49 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  51 */       if (qs != null && qs.getStatus() == QuestStatus.START) {
/*     */         
/*  53 */         if (env.getDialogId().intValue() == 25)
/*  54 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  55 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  57 */           qs.setStatus(QuestStatus.REWARD);
/*  58 */           updateQuestStatus(player, qs);
/*  59 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  60 */           return true;
/*     */         } 
/*     */         
/*  63 */         return defaultQuestEndDialog(env);
/*     */       } 
/*  65 */       if (qs != null && qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/*  67 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     }
/*  70 */     else if (targetId == 204713) {
/*     */       
/*  72 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
/*     */       {
/*  74 */         if (env.getDialogId().intValue() == 25)
/*  75 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  76 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  78 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  79 */           updateQuestStatus(player, qs);
/*  80 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  81 */           return true;
/*     */         } 
/*     */         
/*  84 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  87 */     } else if (targetId == 204719) {
/*     */       
/*  89 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
/*     */       {
/*  91 */         if (env.getDialogId().intValue() == 25)
/*  92 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*  93 */         if (env.getDialogId().intValue() == 10001) {
/*     */           
/*  95 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  96 */           updateQuestStatus(player, qs);
/*  97 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  98 */           return true;
/*     */         } 
/*     */         
/* 101 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 104 */     } else if (targetId == 204733) {
/*     */       
/* 106 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2)
/*     */       {
/* 108 */         if (env.getDialogId().intValue() == 25)
/* 109 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 110 */         if (env.getDialogId().intValue() == 10002) {
/*     */           
/* 112 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 113 */           updateQuestStatus(player, qs);
/* 114 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 115 */           return true;
/*     */         } 
/*     */         
/* 118 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 121 */     } else if (targetId == 204701) {
/*     */       
/* 123 */       if (qs != null) {
/*     */         
/* 125 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/* 126 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 127 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/* 129 */           qs.setQuestVar(3);
/* 130 */           qs.setStatus(QuestStatus.REWARD);
/* 131 */           updateQuestStatus(player, qs);
/* 132 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/* 135 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 138 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\beluslan\_2501FallingFragments.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */