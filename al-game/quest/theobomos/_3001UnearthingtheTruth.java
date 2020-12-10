/*     */ package quest.theobomos;
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
/*     */ public class _3001UnearthingtheTruth
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 3001;
/*     */   
/*     */   public _3001UnearthingtheTruth() {
/*  37 */     super(Integer.valueOf(3001));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  43 */     this.qe.setNpcQuestData(798132).addOnQuestStart(3001);
/*  44 */     this.qe.setNpcQuestData(798132).addOnTalkEvent(3001);
/*  45 */     this.qe.setNpcQuestData(798133).addOnTalkEvent(3001);
/*  46 */     this.qe.setNpcQuestData(798136).addOnTalkEvent(3001);
/*  47 */     this.qe.setNpcQuestData(798139).addOnTalkEvent(3001);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  53 */     Player player = env.getPlayer();
/*  54 */     int targetId = 0;
/*  55 */     if (env.getVisibleObject() instanceof Npc)
/*  56 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  57 */     QuestState qs = player.getQuestStateList().getQuestState(3001);
/*  58 */     if (targetId == 798132) {
/*     */       
/*  60 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/*  62 */         if (env.getDialogId().intValue() == 25) {
/*  63 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  65 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  67 */       if (qs != null && qs.getStatus() == QuestStatus.START) {
/*     */         
/*  69 */         if (env.getDialogId().intValue() == 25)
/*  70 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  71 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  73 */           qs.setQuestVar(3);
/*  74 */           updateQuestStatus(player, qs);
/*  75 */           qs.setStatus(QuestStatus.REWARD);
/*  76 */           updateQuestStatus(player, qs);
/*  77 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/*  80 */         return defaultQuestEndDialog(env);
/*     */       } 
/*  82 */       if (qs != null && qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/*  84 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     }
/*  87 */     else if (targetId == 798133) {
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
/*  98 */           return true;
/*     */         } 
/*     */         
/* 101 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 104 */     } else if (targetId == 798139) {
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
/* 121 */     } else if (targetId == 798136) {
/*     */       
/* 123 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1) {
/*     */         
/* 125 */         if (env.getDialogId().intValue() == 25)
/* 126 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 127 */         if (env.getDialogId().intValue() == 10001) {
/*     */           
/* 129 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 130 */           updateQuestStatus(player, qs);
/* 131 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 132 */           return true;
/*     */         } 
/*     */         
/* 135 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     } 
/* 138 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\theobomos\_3001UnearthingtheTruth.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */