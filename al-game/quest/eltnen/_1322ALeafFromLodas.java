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
/*     */ public class _1322ALeafFromLodas
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1322;
/*     */   
/*     */   public _1322ALeafFromLodas() {
/*  38 */     super(Integer.valueOf(1322));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  44 */     this.qe.setNpcQuestData(730019).addOnQuestStart(1322);
/*  45 */     this.qe.setNpcQuestData(730019).addOnTalkEvent(1322);
/*  46 */     this.qe.setNpcQuestData(730008).addOnTalkEvent(1322);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  52 */     Player player = env.getPlayer();
/*  53 */     int targetId = 0;
/*  54 */     if (env.getVisibleObject() instanceof Npc)
/*  55 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  56 */     QuestState qs = player.getQuestStateList().getQuestState(1322);
/*  57 */     if (targetId == 730019) {
/*     */       
/*  59 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/*  61 */         if (env.getDialogId().intValue() == 25) {
/*  62 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  64 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  66 */       if (qs != null && qs.getStatus() == QuestStatus.START) {
/*     */         
/*  68 */         if (env.getDialogId().intValue() == 25)
/*  69 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  70 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  72 */           qs.setQuestVar(2);
/*  73 */           updateQuestStatus(player, qs);
/*  74 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/*  77 */         return defaultQuestEndDialog(env);
/*     */       } 
/*  79 */       if (qs != null && qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/*  81 */         if (env.getDialogId().intValue() == -1)
/*  82 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  83 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     
/*  86 */     } else if (targetId == 730008) {
/*     */       
/*  88 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
/*     */         
/*  90 */         if (env.getDialogId().intValue() == 25)
/*  91 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  92 */         if (env.getDialogId().intValue() == 10000 || env.getDialogId().intValue() == 10001) {
/*     */           
/*  94 */           qs.setStatus(QuestStatus.REWARD);
/*  95 */           updateQuestStatus(player, qs);
/*  96 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  97 */           return true;
/*     */         } 
/*     */         
/* 100 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     } 
/* 103 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1322ALeafFromLodas.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */