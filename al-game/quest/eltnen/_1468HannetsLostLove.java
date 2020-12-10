/*     */ package quest.eltnen;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
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
/*     */ public class _1468HannetsLostLove
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1468;
/*     */   
/*     */   public _1468HannetsLostLove() {
/*  40 */     super(Integer.valueOf(1468));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  46 */     this.qe.setNpcQuestData(790004).addOnQuestStart(1468);
/*  47 */     this.qe.setNpcQuestData(790004).addOnTalkEvent(1468);
/*  48 */     this.qe.setNpcQuestData(203184).addOnTalkEvent(1468);
/*  49 */     this.qe.setNpcQuestData(204007).addOnTalkEvent(1468);
/*  50 */     this.qe.setNpcQuestData(203969).addOnTalkEvent(1468);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  56 */     Player player = env.getPlayer();
/*  57 */     int targetId = 0;
/*  58 */     if (env.getVisibleObject() instanceof Npc)
/*  59 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  60 */     QuestState qs = player.getQuestStateList().getQuestState(1468);
/*  61 */     if (targetId == 790004) {
/*     */       
/*  63 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/*  65 */         if (env.getDialogId().intValue() == 25) {
/*  66 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  68 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  70 */       if (qs != null && qs.getStatus() == QuestStatus.START) {
/*     */         
/*  72 */         if (env.getDialogId().intValue() == 25)
/*  73 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  74 */         if (env.getDialogId().intValue() == 1009) {
/*     */           
/*  76 */           int targetObjectId = env.getVisibleObject().getObjectId();
/*  77 */           PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.EMOTE, 3, targetObjectId), true);
/*     */ 
/*     */           
/*  80 */           qs.setQuestVar(3);
/*  81 */           qs.setStatus(QuestStatus.REWARD);
/*  82 */           updateQuestStatus(player, qs);
/*  83 */           return defaultQuestEndDialog(env);
/*     */         } 
/*     */         
/*  86 */         return defaultQuestEndDialog(env);
/*     */       } 
/*  88 */       if (qs != null && qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/*  90 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     }
/*  93 */     else if (targetId == 203184) {
/*     */       
/*  95 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
/*     */       {
/*  97 */         if (env.getDialogId().intValue() == 25)
/*  98 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*  99 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/* 101 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 102 */           updateQuestStatus(player, qs);
/* 103 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 105 */           return true;
/*     */         } 
/*     */         
/* 108 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 111 */     } else if (targetId == 204007) {
/*     */       
/* 113 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
/*     */       {
/* 115 */         if (env.getDialogId().intValue() == 25)
/* 116 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 117 */         if (env.getDialogId().intValue() == 10001) {
/*     */           
/* 119 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 120 */           updateQuestStatus(player, qs);
/* 121 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 123 */           return true;
/*     */         } 
/*     */         
/* 126 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/* 129 */     } else if (targetId == 203969) {
/*     */       
/* 131 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2) {
/*     */         
/* 133 */         if (env.getDialogId().intValue() == 25)
/* 134 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 135 */         if (env.getDialogId().intValue() == 10002) {
/*     */           
/* 137 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 138 */           updateQuestStatus(player, qs);
/* 139 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 141 */           return true;
/*     */         } 
/*     */         
/* 144 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     } 
/* 147 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1468HannetsLostLove.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */