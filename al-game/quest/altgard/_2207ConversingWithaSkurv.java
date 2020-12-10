/*     */ package quest.altgard;
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
/*     */ public class _2207ConversingWithaSkurv
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2207;
/*     */   
/*     */   public _2207ConversingWithaSkurv() {
/*  38 */     super(Integer.valueOf(2207));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  44 */     this.qe.setNpcQuestData(203590).addOnQuestStart(2207);
/*  45 */     this.qe.setNpcQuestData(203590).addOnTalkEvent(2207);
/*  46 */     this.qe.setNpcQuestData(203591).addOnTalkEvent(2207);
/*  47 */     this.qe.setNpcQuestData(203557).addOnTalkEvent(2207);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  53 */     Player player = env.getPlayer();
/*  54 */     int targetId = 0;
/*  55 */     if (env.getVisibleObject() instanceof Npc)
/*  56 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  57 */     QuestState qs = player.getQuestStateList().getQuestState(2207);
/*  58 */     if (targetId == 203590) {
/*     */       
/*  60 */       if (qs == null)
/*     */       {
/*  62 */         if (env.getDialogId().intValue() == 25) {
/*  63 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  65 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  68 */     } else if (targetId == 203591) {
/*     */       
/*  70 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
/*     */         
/*  72 */         if (env.getDialogId().intValue() == 25)
/*  73 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  74 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  76 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  77 */           updateQuestStatus(player, qs);
/*  78 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/*  80 */           return true;
/*     */         } 
/*     */         
/*  83 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  85 */       if (qs != null && (qs.getQuestVarById(0) == 2 || qs.getQuestVarById(0) == 3))
/*     */       {
/*  87 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/*  88 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  89 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  91 */           qs.setQuestVar(3);
/*  92 */           qs.setStatus(QuestStatus.REWARD);
/*  93 */           updateQuestStatus(player, qs);
/*  94 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/*  97 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     
/* 100 */     } else if (targetId == 203557) {
/*     */       
/* 102 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1) {
/*     */         
/* 104 */         if (env.getDialogId().intValue() == 25)
/* 105 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 106 */         if (env.getDialogId().intValue() == 10001) {
/*     */           
/* 108 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 109 */           updateQuestStatus(player, qs);
/* 110 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 112 */           return true;
/*     */         } 
/*     */         
/* 115 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     } 
/* 118 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2207ConversingWithaSkurv.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */