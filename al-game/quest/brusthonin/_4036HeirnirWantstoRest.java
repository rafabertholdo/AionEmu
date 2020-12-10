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
/*     */ public class _4036HeirnirWantstoRest
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 4036;
/*     */   
/*     */   public _4036HeirnirWantstoRest() {
/*  21 */     super(Integer.valueOf(4036));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  27 */     this.qe.setNpcQuestData(205187).addOnQuestStart(4036);
/*  28 */     this.qe.setNpcQuestData(205187).addOnTalkEvent(4036);
/*  29 */     this.qe.setNpcQuestData(205144).addOnTalkEvent(4036);
/*  30 */     this.qe.setNpcQuestData(205190).addOnTalkEvent(4036);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  36 */     Player player = env.getPlayer();
/*  37 */     int targetId = 0;
/*  38 */     if (env.getVisibleObject() instanceof Npc)
/*  39 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  40 */     QuestState qs = player.getQuestStateList().getQuestState(4036);
/*  41 */     if (targetId == 205187) {
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
/*     */           
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
/*  70 */     else if (targetId == 205144) {
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
/*     */           
/*  82 */           return true;
/*     */         } 
/*     */         
/*  85 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  88 */     } else if (targetId == 205190) {
/*     */       
/*  90 */       if (qs != null) {
/*     */         
/*  92 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/*  93 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  94 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  96 */           qs.setQuestVar(3);
/*  97 */           qs.setStatus(QuestStatus.REWARD);
/*  98 */           updateQuestStatus(player, qs);
/*  99 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/* 102 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 105 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\brusthonin\_4036HeirnirWantstoRest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */