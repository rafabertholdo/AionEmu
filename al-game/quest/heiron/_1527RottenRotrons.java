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
/*     */ public class _1527RottenRotrons
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1527;
/*     */   
/*     */   public _1527RottenRotrons() {
/*  38 */     super(Integer.valueOf(1527));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  44 */     this.qe.setNpcQuestData(204555).addOnQuestStart(1527);
/*  45 */     this.qe.setNpcQuestData(204555).addOnTalkEvent(1527);
/*  46 */     this.qe.setNpcQuestData(204562).addOnTalkEvent(1527);
/*  47 */     this.qe.setNpcQuestData(730024).addOnTalkEvent(1527);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  53 */     Player player = env.getPlayer();
/*  54 */     int targetId = 0;
/*  55 */     if (env.getVisibleObject() instanceof Npc)
/*  56 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  57 */     QuestState qs = player.getQuestStateList().getQuestState(1527);
/*  58 */     if (targetId == 204555) {
/*     */       
/*  60 */       if (qs == null || qs.getStatus() == QuestStatus.NONE)
/*     */       {
/*  62 */         if (env.getDialogId().intValue() == 25) {
/*  63 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  65 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  68 */     } else if (targetId == 204562) {
/*     */       
/*  70 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
/*     */       {
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
/*     */     
/*  86 */     } else if (targetId == 730024) {
/*     */       
/*  88 */       if (qs != null) {
/*     */         
/*  90 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/*  91 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  92 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  94 */           qs.setQuestVar(2);
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


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\heiron\_1527RottenRotrons.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */