/*     */ package quest.heiron;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
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
/*     */ public class _1537FishOnTheLine
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1537;
/*     */   
/*     */   public _1537FishOnTheLine() {
/*  42 */     super(Integer.valueOf(1537));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  48 */     this.qe.setNpcQuestData(204588).addOnQuestStart(1537);
/*  49 */     this.qe.setNpcQuestData(204588).addOnTalkEvent(1537);
/*  50 */     this.qe.setNpcQuestData(730189).addOnTalkEvent(1537);
/*  51 */     this.qe.setNpcQuestData(730190).addOnTalkEvent(1537);
/*  52 */     this.qe.setNpcQuestData(730191).addOnTalkEvent(1537);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  58 */     final Player player = env.getPlayer();
/*  59 */     int targetId = 0;
/*  60 */     if (env.getVisibleObject() instanceof Npc)
/*  61 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  62 */     final QuestState qs = player.getQuestStateList().getQuestState(1537);
/*  63 */     if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */       
/*  65 */       if (targetId == 204588)
/*     */       {
/*  67 */         if (env.getDialogId().intValue() == 25) {
/*  68 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  70 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  73 */     } else if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  75 */       switch (targetId) {
/*     */ 
/*     */         
/*     */         case 730189:
/*  79 */           if (qs.getQuestVarById(0) == 0 && env.getDialogId().intValue() == -1) {
/*     */             
/*  81 */             final int targetObjectId = env.getVisibleObject().getObjectId();
/*  82 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/*     */             
/*  84 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/*  85 */             ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/*  89 */                     if (!player.isTargeting(targetObjectId))
/*     */                       return; 
/*  91 */                     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/*     */                     
/*  93 */                     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/*     */ 
/*     */                     
/*  96 */                     qs.setQuestVarById(0, 1);
/*  97 */                     _1537FishOnTheLine.this.updateQuestStatus(player, qs);
/*     */                   }
/*     */                 }3000L);
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 730190:
/* 106 */           if (qs.getQuestVarById(0) == 1 && env.getDialogId().intValue() == -1) {
/*     */             
/* 108 */             final int targetObjectId = env.getVisibleObject().getObjectId();
/* 109 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/*     */             
/* 111 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 112 */             ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 116 */                     if (!player.isTargeting(targetObjectId))
/*     */                       return; 
/* 118 */                     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/*     */                     
/* 120 */                     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/*     */ 
/*     */                     
/* 123 */                     qs.setQuestVarById(0, 2);
/* 124 */                     _1537FishOnTheLine.this.updateQuestStatus(player, qs);
/*     */                   }
/*     */                 }3000L);
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 730191:
/* 133 */           if (qs.getQuestVarById(0) == 2 && env.getDialogId().intValue() == -1) {
/*     */             
/* 135 */             final int targetObjectId = env.getVisibleObject().getObjectId();
/* 136 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/*     */             
/* 138 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 139 */             ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 143 */                     if (!player.isTargeting(targetObjectId))
/*     */                       return; 
/* 145 */                     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/*     */                     
/* 147 */                     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/*     */                     
/* 149 */                     qs.setQuestVarById(0, 3);
/* 150 */                     qs.setStatus(QuestStatus.REWARD);
/* 151 */                     _1537FishOnTheLine.this.updateQuestStatus(player, qs);
/*     */                   }
/*     */                 }3000L);
/*     */           } 
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/* 159 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 161 */       if (targetId == 204588)
/* 162 */         return defaultQuestEndDialog(env); 
/*     */     } 
/* 164 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\heiron\_1537FishOnTheLine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */