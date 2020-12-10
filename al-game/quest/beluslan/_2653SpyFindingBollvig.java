/*     */ package quest.beluslan;
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
/*     */ public class _2653SpyFindingBollvig
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2653;
/*     */   
/*     */   public _2653SpyFindingBollvig() {
/*  38 */     super(Integer.valueOf(2653));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  44 */     this.qe.setNpcQuestData(204650).addOnQuestStart(2653);
/*  45 */     this.qe.setNpcQuestData(204650).addOnTalkEvent(2653);
/*  46 */     this.qe.setNpcQuestData(204655).addOnTalkEvent(2653);
/*  47 */     this.qe.setNpcQuestData(204775).addOnTalkEvent(2653);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  52 */     Player player = env.getPlayer();
/*  53 */     QuestState qs = player.getQuestStateList().getQuestState(2653);
/*  54 */     QuestState qs2 = player.getQuestStateList().getQuestState(2652);
/*  55 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/*  56 */       return false; 
/*  57 */     qs.setStatus(QuestStatus.START);
/*  58 */     updateQuestStatus(player, qs);
/*  59 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  65 */     Player player = env.getPlayer();
/*  66 */     int targetId = 0;
/*  67 */     if (env.getVisibleObject() instanceof Npc)
/*  68 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  69 */     QuestState qs = player.getQuestStateList().getQuestState(2653);
/*  70 */     if (targetId == 204650) {
/*     */       
/*  72 */       if (qs == null) {
/*     */         
/*  74 */         if (env.getDialogId().intValue() == 25) {
/*  75 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  77 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  79 */     } else if (targetId == 204655) {
/*     */       
/*  81 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
/*     */         
/*  83 */         if (env.getDialogId().intValue() == -1) {
/*     */           
/*  85 */           PacketSendUtility.sendMessage(player, "25");
/*  86 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */         } 
/*  88 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  90 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  91 */           updateQuestStatus(player, qs);
/*  92 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  93 */           return true;
/*     */         } 
/*     */         
/*  96 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  98 */     } else if (targetId == 204775) {
/*     */       
/* 100 */       if (qs != null) {
/*     */         
/* 102 */         if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
/* 103 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 104 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/* 106 */           qs.setQuestVar(3);
/* 107 */           qs.setStatus(QuestStatus.REWARD);
/* 108 */           updateQuestStatus(player, qs);
/* 109 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/* 112 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 115 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\beluslan\_2653SpyFindingBollvig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */