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
/*     */ public class _1422ABetterSword
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1422;
/*     */   
/*     */   public _1422ABetterSword() {
/*  38 */     super(Integer.valueOf(1422));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  44 */     this.qe.setNpcQuestData(203912).addOnQuestStart(1422);
/*  45 */     this.qe.setNpcQuestData(203912).addOnTalkEvent(1422);
/*  46 */     this.qe.setNpcQuestData(203731).addOnTalkEvent(1422);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  52 */     Player player = env.getPlayer();
/*  53 */     int targetId = 0;
/*  54 */     if (env.getVisibleObject() instanceof Npc)
/*  55 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  56 */     QuestState qs = player.getQuestStateList().getQuestState(1422);
/*  57 */     if (targetId == 203912) {
/*     */       
/*  59 */       if (qs == null) {
/*     */         
/*  61 */         if (env.getDialogId().intValue() == 25) {
/*  62 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*     */         
/*  65 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  67 */       if (qs.getStatus() == QuestStatus.START) {
/*     */         
/*  69 */         if (env.getDialogId().intValue() == 25)
/*  70 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  71 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  73 */           qs.setQuestVar(2);
/*  74 */           qs.setStatus(QuestStatus.REWARD);
/*  75 */           updateQuestStatus(player, qs);
/*  76 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/*  79 */         return defaultQuestEndDialog(env);
/*     */       } 
/*  81 */       if (qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/*  83 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     }
/*  86 */     else if (targetId == 203731) {
/*     */       
/*  88 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
/*     */         
/*  90 */         if (env.getDialogId().intValue() == 25)
/*  91 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  92 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  94 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*     */           
/*  96 */           updateQuestStatus(player, qs);
/*  97 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/*  99 */           return true;
/*     */         } 
/*     */         
/* 102 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     } 
/* 105 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1422ABetterSword.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */