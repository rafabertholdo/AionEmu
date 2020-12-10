/*     */ package quest.eltnen;
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
/*     */ public class _1363ThankingMabangtah
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1363;
/*     */   
/*     */   public _1363ThankingMabangtah() {
/*  38 */     super(Integer.valueOf(1363));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  44 */     this.qe.setNpcQuestData(203943).addOnQuestStart(1363);
/*  45 */     this.qe.setNpcQuestData(203943).addOnTalkEvent(1363);
/*  46 */     this.qe.setNpcQuestData(204020).addOnTalkEvent(1363);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  52 */     Player player = env.getPlayer();
/*  53 */     int targetId = 0;
/*  54 */     if (env.getVisibleObject() instanceof Npc)
/*  55 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  56 */     QuestState qs = player.getQuestStateList().getQuestState(1363);
/*  57 */     if (targetId == 203943) {
/*     */       
/*  59 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/*  61 */         if (env.getDialogId().intValue() == 25) {
/*  62 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  64 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  66 */       if (qs.getStatus() == QuestStatus.START) {
/*     */         
/*  68 */         if (env.getDialogId().intValue() == 25)
/*  69 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  70 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  72 */           qs.setQuestVar(2);
/*  73 */           qs.setStatus(QuestStatus.REWARD);
/*  74 */           updateQuestStatus(player, qs);
/*  75 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/*  78 */         return defaultQuestEndDialog(env);
/*     */       } 
/*  80 */       if (qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/*  82 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     }
/*  85 */     else if (targetId == 204020) {
/*     */       
/*  87 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
/*     */         
/*  89 */         if (env.getDialogId().intValue() == 25)
/*  90 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  91 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  93 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  94 */           updateQuestStatus(player, qs);
/*  95 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/*  97 */           return true;
/*     */         } 
/*     */         
/* 100 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     } 
/* 103 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1363ThankingMabangtah.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */