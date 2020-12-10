/*     */ package quest.brusthonin;
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
/*     */ public class _4015TheMissingLaborers
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 4015;
/*     */   
/*     */   public _4015TheMissingLaborers() {
/*  42 */     super(Integer.valueOf(4015));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  48 */     this.qe.setNpcQuestData(205130).addOnQuestStart(4015);
/*  49 */     this.qe.setNpcQuestData(205130).addOnTalkEvent(4015);
/*  50 */     this.qe.setNpcQuestData(730107).addOnTalkEvent(4015);
/*  51 */     this.qe.setNpcQuestData(205130).addOnTalkEvent(4015);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  57 */     final Player player = env.getPlayer();
/*  58 */     int targetId = 0;
/*  59 */     if (env.getVisibleObject() instanceof Npc)
/*  60 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  61 */     final QuestState qs = player.getQuestStateList().getQuestState(4015);
/*  62 */     if (qs == null) {
/*     */       
/*  64 */       if (targetId == 205130)
/*     */       {
/*  66 */         if (env.getDialogId().intValue() == 25) {
/*  67 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  69 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  72 */     } else if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  74 */       switch (targetId) {
/*     */ 
/*     */         
/*     */         case 730107:
/*  78 */           if (qs.getQuestVarById(0) == 0 && env.getDialogId().intValue() == -1) {
/*     */             
/*  80 */             final int targetObjectId = env.getVisibleObject().getObjectId();
/*  81 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/*     */             
/*  83 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/*     */             
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
/*  95 */                     qs.setQuestVarById(0, 1);
/*  96 */                     _4015TheMissingLaborers.this.updateQuestStatus(player, qs);
/*     */                   }
/*     */                 }3000L);
/*     */           } 
/*     */ 
/*     */         
/*     */         case 205130:
/* 103 */           if (qs.getQuestVarById(0) == 1) {
/*     */             
/* 105 */             if (env.getDialogId().intValue() == 25)
/* 106 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 107 */             if (env.getDialogId().intValue() == 1009) {
/*     */               
/* 109 */               qs.setStatus(QuestStatus.REWARD);
/* 110 */               updateQuestStatus(player, qs);
/* 111 */               return defaultQuestEndDialog(env);
/*     */             } 
/*     */             
/* 114 */             return defaultQuestEndDialog(env);
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     
/* 119 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 121 */       if (targetId == 205130)
/* 122 */         return defaultQuestEndDialog(env); 
/*     */     } 
/* 124 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\brusthonin\_4015TheMissingLaborers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */