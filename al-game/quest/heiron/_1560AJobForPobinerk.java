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
/*     */ public class _1560AJobForPobinerk
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1560;
/*     */   
/*     */   public _1560AJobForPobinerk() {
/*  38 */     super(Integer.valueOf(1560));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  44 */     this.qe.setNpcQuestData(798103).addOnQuestStart(1560);
/*  45 */     this.qe.setNpcQuestData(798103).addOnTalkEvent(1560);
/*  46 */     this.qe.setNpcQuestData(798026).addOnTalkEvent(1560);
/*  47 */     this.qe.setNpcQuestData(798357).addOnTalkEvent(1560);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  53 */     Player player = env.getPlayer();
/*  54 */     int targetId = 0;
/*  55 */     if (env.getVisibleObject() instanceof Npc)
/*  56 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  57 */     QuestState qs = player.getQuestStateList().getQuestState(1560);
/*  58 */     if (targetId == 798103) {
/*     */       
/*  60 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/*  62 */         if (env.getDialogId().intValue() == 25) {
/*  63 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  65 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  67 */       if (qs.getStatus() == QuestStatus.START) {
/*     */         
/*  69 */         if (env.getDialogId().intValue() == 25)
/*  70 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  71 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  73 */           qs.setQuestVar(3);
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
/*  86 */     else if (targetId == 798026) {
/*     */       
/*  88 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
/*     */       {
/*  90 */         if (env.getDialogId().intValue() == 25)
/*  91 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  92 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  94 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/*  95 */           updateQuestStatus(player, qs);
/*  96 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/*  98 */           return true;
/*     */         } 
/*     */         
/* 101 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 104 */     } else if (targetId == 798357) {
/*     */       
/* 106 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1) {
/*     */         
/* 108 */         if (env.getDialogId().intValue() == 25)
/* 109 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 110 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/* 112 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 113 */           updateQuestStatus(player, qs);
/* 114 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 116 */           return true;
/*     */         } 
/*     */         
/* 119 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     } 
/*     */     
/* 123 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\heiron\_1560AJobForPobinerk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */