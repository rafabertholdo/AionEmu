/*     */ package quest.eltnen;
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
/*     */ import com.aionemu.gameserver.services.ItemService;
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
/*     */ public class _1371FlowersForIsson
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1371;
/*     */   
/*     */   public _1371FlowersForIsson() {
/*  43 */     super(Integer.valueOf(1371));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  50 */     this.qe.setNpcQuestData(203949).addOnQuestStart(1371);
/*  51 */     this.qe.setNpcQuestData(203949).addOnTalkEvent(1371);
/*  52 */     this.qe.setNpcQuestData(730039).addOnTalkEvent(1371);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  58 */     final Player player = env.getPlayer();
/*  59 */     int targetId = 0;
/*  60 */     if (env.getVisibleObject() instanceof Npc)
/*  61 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  62 */     QuestState qs = player.getQuestStateList().getQuestState(1371);
/*  63 */     long itemCount = 0L;
/*  64 */     if (targetId == 203949) {
/*     */       
/*  66 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/*  68 */         if (env.getDialogId().intValue() == 25) {
/*  69 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  71 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */       
/*  74 */       if (qs.getStatus() == QuestStatus.START) {
/*     */         
/*  76 */         int var = qs.getQuestVarById(0);
/*  77 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/*  80 */             if (var == 0)
/*     */             {
/*  82 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */             }
/*     */           
/*     */           case 33:
/*  86 */             if (var == 0)
/*  87 */               itemCount = player.getInventory().getItemCountByItemId(152000601); 
/*  88 */             if (itemCount > 4L)
/*     */             {
/*  90 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1353);
/*     */             }
/*     */ 
/*     */             
/*  94 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1438);
/*     */           
/*     */           case 10000:
/*  97 */             ItemService.removeItemFromInventoryByItemId(player, 152000601);
/*  98 */             qs.setQuestVar(2);
/*  99 */             updateQuestStatus(player, qs);
/* 100 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 0);
/*     */         } 
/*     */ 
/*     */         
/* 104 */         return false;
/*     */       } 
/*     */       
/* 107 */       if (qs != null && qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/* 109 */         return defaultQuestEndDialog(env);
/*     */       
/*     */       }
/*     */     }
/* 113 */     else if (targetId == 730039) {
/*     */       
/* 115 */       int var = qs.getQuestVarById(0);
/* 116 */       if (qs.getStatus() == QuestStatus.START && var == 2) {
/*     */ 
/*     */         
/* 119 */         final int targetObjectId = env.getVisibleObject().getObjectId();
/* 120 */         qs.setStatus(QuestStatus.REWARD);
/* 121 */         updateQuestStatus(player, qs);
/*     */         
/* 123 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/*     */         
/* 125 */         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/*     */         
/* 127 */         ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 131 */                 if (player.getTarget() == null || player.getTarget().getObjectId() != targetObjectId)
/*     */                   return; 
/* 133 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/*     */                 
/* 135 */                 PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/*     */               }
/*     */             }3000L);
/*     */       } 
/*     */     } 
/* 140 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1371FlowersForIsson.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */