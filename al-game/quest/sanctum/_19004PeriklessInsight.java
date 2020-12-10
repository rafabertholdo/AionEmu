/*     */ package quest.sanctum;
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
/*     */ public class _19004PeriklessInsight
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 19004;
/*     */   
/*     */   public _19004PeriklessInsight() {
/*  38 */     super(Integer.valueOf(19004));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  44 */     this.qe.setNpcQuestData(203757).addOnQuestStart(19004);
/*  45 */     this.qe.setNpcQuestData(203757).addOnTalkEvent(19004);
/*  46 */     this.qe.setNpcQuestData(203752).addOnTalkEvent(19004);
/*  47 */     this.qe.setNpcQuestData(203701).addOnTalkEvent(19004);
/*  48 */     this.qe.setNpcQuestData(798500).addOnTalkEvent(19004);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  54 */     Player player = env.getPlayer();
/*  55 */     int targetId = 0;
/*  56 */     if (env.getVisibleObject() instanceof Npc)
/*  57 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  58 */     QuestState qs = player.getQuestStateList().getQuestState(19004);
/*  59 */     if (targetId == 203757)
/*     */     {
/*  61 */       if (qs == null) {
/*     */         
/*  63 */         if (env.getDialogId().intValue() == 25) {
/*  64 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  66 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     }
/*     */     
/*  70 */     if (qs == null) {
/*  71 */       return false;
/*     */     }
/*  73 */     int var = qs.getQuestVarById(0);
/*     */     
/*  75 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  77 */       if (targetId == 203752 && var == 0) {
/*     */         
/*  79 */         if (env.getDialogId().intValue() == 25)
/*  80 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  81 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  83 */           qs.setQuestVar(++var);
/*  84 */           updateQuestStatus(player, qs);
/*  85 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/*  87 */           return true;
/*     */         } 
/*     */         
/*  90 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  92 */       if (targetId == 203701 && var == 1) {
/*     */         
/*  94 */         if (env.getDialogId().intValue() == 25)
/*  95 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  96 */         if (env.getDialogId().intValue() == 10001) {
/*     */           
/*  98 */           qs.setQuestVar(++var);
/*  99 */           updateQuestStatus(player, qs);
/* 100 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 102 */           return true;
/*     */         } 
/*     */         
/* 105 */         return defaultQuestStartDialog(env);
/*     */       } 
/* 107 */       if (targetId == 798500 && var == 2)
/*     */       {
/* 109 */         if (env.getDialogId().intValue() == 25)
/* 110 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 111 */         if (env.getDialogId().intValue() == 10255) {
/*     */           
/* 113 */           qs.setQuestVar(++var);
/* 114 */           updateQuestStatus(player, qs);
/* 115 */           qs.setStatus(QuestStatus.REWARD);
/* 116 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 118 */           return true;
/*     */         } 
/*     */         
/* 121 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 124 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 126 */       if (env.getDialogId().intValue() == -1 && qs.getStatus() == QuestStatus.REWARD)
/* 127 */         return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
/* 128 */       if (env.getDialogId().intValue() == 1009) {
/*     */         
/* 130 */         qs.setQuestVar(4);
/* 131 */         updateQuestStatus(player, qs);
/* 132 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */       
/* 135 */       return defaultQuestEndDialog(env);
/*     */     } 
/* 137 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\sanctum\_19004PeriklessInsight.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */