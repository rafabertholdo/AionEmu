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
/*     */ public class _1324ConfidentialOrders
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1324;
/*     */   
/*     */   public _1324ConfidentialOrders() {
/*  38 */     super(Integer.valueOf(1324));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  44 */     this.qe.setNpcQuestData(203904).addOnQuestStart(1324);
/*  45 */     this.qe.setNpcQuestData(203904).addOnTalkEvent(1324);
/*  46 */     this.qe.setNpcQuestData(204031).addOnTalkEvent(1324);
/*  47 */     this.qe.setNpcQuestData(203940).addOnTalkEvent(1324);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  53 */     Player player = env.getPlayer();
/*  54 */     int targetId = 0;
/*  55 */     if (env.getVisibleObject() instanceof Npc)
/*  56 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  57 */     QuestState qs = player.getQuestStateList().getQuestState(1324);
/*  58 */     if (targetId == 203904) {
/*     */       
/*  60 */       if (qs == null || qs.getStatus() == QuestStatus.NONE)
/*     */       {
/*  62 */         if (env.getDialogId().intValue() == 25) {
/*  63 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  65 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  68 */     } else if (targetId == 204031) {
/*     */       
/*  70 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
/*     */       {
/*  72 */         if (env.getDialogId().intValue() == 25)
/*  73 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  74 */         if (env.getDialogId().intValue() == 10000) {
/*     */ 
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
/*  87 */     } else if (targetId == 203940) {
/*     */       
/*  89 */       if (qs != null) {
/*     */         
/*  91 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/*  92 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  93 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  95 */           qs.setQuestVar(2);
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


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1324ConfidentialOrders.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */