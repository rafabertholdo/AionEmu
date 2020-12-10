/*     */ package quest.brusthonin;
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
/*     */ public class _4052BukmirsOldFriend
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 4052;
/*     */   
/*     */   public _4052BukmirsOldFriend() {
/*  21 */     super(Integer.valueOf(4052));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  27 */     this.qe.setNpcQuestData(730152).addOnQuestStart(4052);
/*  28 */     this.qe.setNpcQuestData(730152).addOnTalkEvent(4052);
/*  29 */     this.qe.setNpcQuestData(205179).addOnTalkEvent(4052);
/*  30 */     this.qe.setNpcQuestData(205166).addOnTalkEvent(4052);
/*  31 */     this.qe.setNpcQuestData(205197).addOnTalkEvent(4052);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  37 */     Player player = env.getPlayer();
/*  38 */     int targetId = 0;
/*  39 */     if (env.getVisibleObject() instanceof Npc)
/*  40 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  41 */     QuestState qs = player.getQuestStateList().getQuestState(4052);
/*  42 */     if (targetId == 730152) {
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
/*     */           
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
/*  71 */     else if (targetId == 205179) {
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
/*     */           
/*  83 */           return true;
/*     */         } 
/*     */         
/*  86 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  89 */     } else if (targetId == 205166) {
/*     */       
/*  91 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
/*     */       {
/*  93 */         if (env.getDialogId().intValue() == 25)
/*  94 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  95 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  97 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  98 */           updateQuestStatus(player, qs);
/*  99 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 101 */           return true;
/*     */         } 
/*     */         
/* 104 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 107 */     } else if (targetId == 205197) {
/*     */       
/* 109 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2)
/*     */       {
/* 111 */         if (env.getDialogId().intValue() == 25)
/* 112 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 113 */         if (env.getDialogId().intValue() == 10002) {
/*     */           
/* 115 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 116 */           updateQuestStatus(player, qs);
/* 117 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 119 */           return true;
/*     */         } 
/*     */         
/* 122 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 125 */     } else if (targetId == 730152) {
/*     */       
/* 127 */       if (qs != null) {
/*     */         
/* 129 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/* 130 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 131 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/* 133 */           qs.setQuestVar(3);
/* 134 */           qs.setStatus(QuestStatus.REWARD);
/* 135 */           updateQuestStatus(player, qs);
/* 136 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/* 139 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 142 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\brusthonin\_4052BukmirsOldFriend.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */