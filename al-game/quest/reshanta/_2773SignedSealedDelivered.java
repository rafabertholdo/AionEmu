/*     */ package quest.reshanta;
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
/*     */ public class _2773SignedSealedDelivered
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2773;
/*     */   
/*     */   public _2773SignedSealedDelivered() {
/*  21 */     super(Integer.valueOf(2773));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  27 */     this.qe.setNpcQuestData(279019).addOnQuestStart(2773);
/*  28 */     this.qe.setNpcQuestData(279019).addOnTalkEvent(2773);
/*  29 */     this.qe.setNpcQuestData(798110).addOnTalkEvent(2773);
/*  30 */     this.qe.setNpcQuestData(204734).addOnTalkEvent(2773);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  36 */     Player player = env.getPlayer();
/*  37 */     int targetId = 0;
/*  38 */     if (env.getVisibleObject() instanceof Npc)
/*  39 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  40 */     QuestState qs = player.getQuestStateList().getQuestState(2773);
/*  41 */     if (targetId == 279019) {
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
/*  69 */     else if (targetId == 798110) {
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
/*  86 */     } else if (targetId == 204734) {
/*     */       
/*  88 */       if (qs != null) {
/*     */         
/*  90 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/*  91 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  92 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  94 */           qs.setQuestVar(3);
/*  95 */           qs.setStatus(QuestStatus.REWARD);
/*  96 */           updateQuestStatus(player, qs);
/*  97 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/* 100 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 103 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\reshanta\_2773SignedSealedDelivered.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */