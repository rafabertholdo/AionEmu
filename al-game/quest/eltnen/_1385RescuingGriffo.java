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
/*     */ public class _1385RescuingGriffo
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1385;
/*     */   
/*     */   public _1385RescuingGriffo() {
/*  38 */     super(Integer.valueOf(1385));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  44 */     this.qe.setNpcQuestData(212057).addOnQuestStart(1385);
/*  45 */     this.qe.setNpcQuestData(212057).addOnTalkEvent(1385);
/*  46 */     this.qe.setNpcQuestData(204029).addOnTalkEvent(1385);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  52 */     Player player = env.getPlayer();
/*  53 */     int targetId = 0;
/*  54 */     if (env.getVisibleObject() instanceof Npc)
/*  55 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  56 */     QuestState qs = player.getQuestStateList().getQuestState(1385);
/*  57 */     if (targetId == 212057) {
/*     */       
/*  59 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/*  61 */         if (env.getDialogId().intValue() == 25) {
/*  62 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*     */ 
/*     */         
/*  66 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  68 */       if (qs != null && qs.getStatus() == QuestStatus.START) {
/*     */         
/*  70 */         if (env.getDialogId().intValue() == 25)
/*  71 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  72 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  74 */           qs.setQuestVar(2);
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
/*  87 */     else if (targetId == 204029) {
/*     */       
/*  89 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
/*     */         
/*  91 */         if (env.getDialogId().intValue() == 25)
/*  92 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  93 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  95 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  96 */           qs.setStatus(QuestStatus.REWARD);
/*  97 */           updateQuestStatus(player, qs);
/*  98 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 100 */           return true;
/*     */         } 
/*     */         
/* 103 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     } 
/* 106 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1385RescuingGriffo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */