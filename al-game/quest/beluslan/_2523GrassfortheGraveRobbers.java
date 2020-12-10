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
/*     */ public class _2523GrassfortheGraveRobbers
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2523;
/*     */   
/*     */   public _2523GrassfortheGraveRobbers() {
/*  21 */     super(Integer.valueOf(2523));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  27 */     this.qe.setNpcQuestData(204802).addOnQuestStart(2523);
/*  28 */     this.qe.setNpcQuestData(204802).addOnTalkEvent(2523);
/*  29 */     this.qe.setNpcQuestData(798117).addOnTalkEvent(2523);
/*  30 */     this.qe.setNpcQuestData(798118).addOnTalkEvent(2523);
/*  31 */     this.qe.setNpcQuestData(798119).addOnTalkEvent(2523);
/*  32 */     this.qe.setNpcQuestData(204734).addOnTalkEvent(2523);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  38 */     Player player = env.getPlayer();
/*  39 */     int targetId = 0;
/*  40 */     if (env.getVisibleObject() instanceof Npc)
/*  41 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  42 */     QuestState qs = player.getQuestStateList().getQuestState(2523);
/*  43 */     if (targetId == 204802) {
/*     */       
/*  45 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/*  47 */         if (env.getDialogId().intValue() == 25) {
/*  48 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  50 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  52 */       if (qs != null && qs.getStatus() == QuestStatus.START) {
/*     */         
/*  54 */         if (env.getDialogId().intValue() == 25)
/*  55 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  56 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  58 */           qs.setStatus(QuestStatus.REWARD);
/*  59 */           updateQuestStatus(player, qs);
/*  60 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  61 */           return true;
/*     */         } 
/*     */         
/*  64 */         return defaultQuestEndDialog(env);
/*     */       } 
/*  66 */       if (qs != null && qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/*  68 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     }
/*  71 */     else if (targetId == 798117) {
/*     */       
/*  73 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
/*     */       {
/*  75 */         if (env.getDialogId().intValue() == 25)
/*  76 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  77 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  79 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  80 */           updateQuestStatus(player, qs);
/*  81 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  82 */           return true;
/*     */         } 
/*     */         
/*  85 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  88 */     } else if (targetId == 798118) {
/*     */       
/*  90 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
/*     */       {
/*  92 */         if (env.getDialogId().intValue() == 25)
/*  93 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*  94 */         if (env.getDialogId().intValue() == 10001) {
/*     */           
/*  96 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  97 */           updateQuestStatus(player, qs);
/*  98 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  99 */           return true;
/*     */         } 
/*     */         
/* 102 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 105 */     } else if (targetId == 798119) {
/*     */       
/* 107 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2)
/*     */       {
/* 109 */         if (env.getDialogId().intValue() == 25)
/* 110 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 111 */         if (env.getDialogId().intValue() == 10002) {
/*     */           
/* 113 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 114 */           updateQuestStatus(player, qs);
/* 115 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 116 */           return true;
/*     */         } 
/*     */         
/* 119 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 122 */     } else if (targetId == 204734) {
/*     */       
/* 124 */       if (qs != null) {
/*     */         
/* 126 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/* 127 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 128 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/* 130 */           qs.setQuestVar(3);
/* 131 */           qs.setStatus(QuestStatus.REWARD);
/* 132 */           updateQuestStatus(player, qs);
/* 133 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/* 136 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 139 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\beluslan\_2523GrassfortheGraveRobbers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */