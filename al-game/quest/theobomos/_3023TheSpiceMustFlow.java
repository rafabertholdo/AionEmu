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
/*     */ public class _3023TheSpiceMustFlow
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 3023;
/*     */   
/*     */   public _3023TheSpiceMustFlow() {
/*  21 */     super(Integer.valueOf(3023));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  27 */     this.qe.setNpcQuestData(798138).addOnQuestStart(3023);
/*  28 */     this.qe.setNpcQuestData(798138).addOnTalkEvent(3023);
/*  29 */     this.qe.setNpcQuestData(203785).addOnTalkEvent(3023);
/*  30 */     this.qe.setNpcQuestData(798222).addOnTalkEvent(3023);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  36 */     Player player = env.getPlayer();
/*  37 */     int targetId = 0;
/*  38 */     if (env.getVisibleObject() instanceof Npc)
/*  39 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  40 */     QuestState qs = player.getQuestStateList().getQuestState(3023);
/*  41 */     if (targetId == 798138) {
/*     */       
/*  43 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/*  45 */         if (env.getDialogId().intValue() == 25) {
/*  46 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  48 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  50 */       if (qs != null && qs.getStatus() == QuestStatus.START) {
/*     */         
/*  52 */         if (env.getDialogId().intValue() == 25)
/*  53 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  54 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  56 */           qs.setStatus(QuestStatus.REWARD);
/*  57 */           updateQuestStatus(player, qs);
/*  58 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  59 */           return true;
/*     */         } 
/*     */         
/*  62 */         return defaultQuestEndDialog(env);
/*     */       } 
/*  64 */       if (qs != null && qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/*  66 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     }
/*  69 */     else if (targetId == 203785) {
/*     */       
/*  71 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
/*     */       {
/*  73 */         if (env.getDialogId().intValue() == 25)
/*  74 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  75 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  77 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  78 */           updateQuestStatus(player, qs);
/*  79 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  80 */           return true;
/*     */         } 
/*     */         
/*  83 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  86 */     } else if (targetId == 798222) {
/*     */       
/*  88 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
/*     */       {
/*  90 */         if (env.getDialogId().intValue() == 25)
/*  91 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*  92 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  94 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  95 */           updateQuestStatus(player, qs);
/*  96 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  97 */           return true;
/*     */         } 
/*     */         
/* 100 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 103 */     } else if (targetId == 798138) {
/*     */       
/* 105 */       if (qs != null) {
/*     */         
/* 107 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/* 108 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 109 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/* 111 */           qs.setQuestVar(3);
/* 112 */           qs.setStatus(QuestStatus.REWARD);
/* 113 */           updateQuestStatus(player, qs);
/* 114 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/* 117 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 120 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\theobomos\_3023TheSpiceMustFlow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */