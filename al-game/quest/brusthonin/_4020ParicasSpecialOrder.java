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
/*     */ public class _4020ParicasSpecialOrder
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 4020;
/*     */   
/*     */   public _4020ParicasSpecialOrder() {
/*  21 */     super(Integer.valueOf(4020));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  27 */     this.qe.setNpcQuestData(205120).addOnQuestStart(4020);
/*  28 */     this.qe.setNpcQuestData(205120).addOnTalkEvent(4020);
/*  29 */     this.qe.setNpcQuestData(205141).addOnTalkEvent(4020);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  35 */     Player player = env.getPlayer();
/*  36 */     int targetId = 0;
/*  37 */     if (env.getVisibleObject() instanceof Npc)
/*  38 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  39 */     QuestState qs = player.getQuestStateList().getQuestState(4020);
/*  40 */     if (targetId == 205120) {
/*     */       
/*  42 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/*  44 */         if (env.getDialogId().intValue() == 25) {
/*  45 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  47 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  49 */       if (qs != null && qs.getStatus() == QuestStatus.START) {
/*     */         
/*  51 */         if (env.getDialogId().intValue() == 25)
/*  52 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  53 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  55 */           qs.setStatus(QuestStatus.REWARD);
/*  56 */           updateQuestStatus(player, qs);
/*  57 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
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
/*  69 */     else if (targetId == 205141) {
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
/*     */           
/*  81 */           return true;
/*     */         } 
/*     */         
/*  84 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  87 */     } else if (targetId == 205120) {
/*     */       
/*  89 */       if (qs != null) {
/*     */         
/*  91 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/*  92 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  93 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  95 */           qs.setQuestVar(3);
/*  96 */           qs.setStatus(QuestStatus.REWARD);
/*  97 */           updateQuestStatus(player, qs);
/*  98 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/* 101 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 104 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\brusthonin\_4020ParicasSpecialOrder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */