/*     */ package quest.eltnen;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
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
/*     */ public class _1487BalaurBones
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1487;
/*     */   
/*     */   public _1487BalaurBones() {
/*  42 */     super(Integer.valueOf(1487));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  49 */     this.qe.setNpcQuestData(798126).addOnQuestStart(1487);
/*  50 */     this.qe.setNpcQuestData(798126).addOnTalkEvent(1487);
/*  51 */     this.qe.setNpcQuestData(700313).addOnTalkEvent(1487);
/*  52 */     this.qe.setNpcQuestData(700314).addOnTalkEvent(1487);
/*  53 */     this.qe.setNpcQuestData(700315).addOnTalkEvent(1487);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  59 */     final Player player = env.getPlayer();
/*  60 */     int targetId = 0;
/*  61 */     if (env.getVisibleObject() instanceof Npc)
/*  62 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  63 */     QuestState qs = player.getQuestStateList().getQuestState(1487);
/*  64 */     if (targetId == 798126) {
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
/*     */ 
/*     */ 
/*     */         
/*  79 */         if (env.getDialogId().intValue() == 25 && qs.getQuestVarById(0) == 0)
/*     */         {
/*  81 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
/*     */         }
/*  83 */         if (env.getDialogId().intValue() == 33 && qs.getQuestVarById(0) == 0) {
/*     */           
/*  85 */           long itemCount = player.getInventory().getItemCountByItemId(182201407);
/*  86 */           long itemCount1 = player.getInventory().getItemCountByItemId(182201408);
/*  87 */           long itemCount2 = player.getInventory().getItemCountByItemId(182201409);
/*  88 */           if (itemCount > 0L && itemCount1 > 2L && itemCount2 > 1L) {
/*     */             
/*  90 */             ItemService.removeItemFromInventoryByItemId(player, 182201407);
/*  91 */             ItemService.removeItemFromInventoryByItemId(player, 182201408);
/*  92 */             ItemService.removeItemFromInventoryByItemId(player, 182201409);
/*  93 */             qs.setStatus(QuestStatus.REWARD);
/*  94 */             updateQuestStatus(player, qs);
/*  95 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */           } 
/*     */ 
/*     */           
/*  99 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
/*     */         } 
/*     */ 
/*     */         
/* 103 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */       
/* 106 */       if (qs != null && qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/* 108 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     } else {
/* 111 */       if (targetId == 700313) {
/*     */ 
/*     */         
/* 114 */         long itemCount = player.getInventory().getItemCountByItemId(182201407);
/* 115 */         if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0 && itemCount < 1L) {
/*     */           
/* 117 */           final int targetObjectId = env.getVisibleObject().getObjectId();
/* 118 */           PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 119 */           ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */               {
/*     */                 
/*     */                 public void run()
/*     */                 {
/* 124 */                   PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/*     */                 }
/*     */               }3000L);
/* 127 */           return true;
/*     */         } 
/*     */ 
/*     */         
/* 131 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */       
/* 134 */       if (targetId == 700314) {
/*     */ 
/*     */         
/* 137 */         long itemCount1 = player.getInventory().getItemCountByItemId(182201408);
/* 138 */         if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0 && itemCount1 < 3L) {
/*     */           
/* 140 */           final int targetObjectId = env.getVisibleObject().getObjectId();
/* 141 */           PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 142 */           ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */               {
/*     */                 
/*     */                 public void run()
/*     */                 {
/* 147 */                   PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/*     */                 }
/*     */               }3000L);
/* 150 */           return true;
/*     */         } 
/*     */ 
/*     */         
/* 154 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */       
/* 157 */       if (targetId == 700315) {
/*     */ 
/*     */         
/* 160 */         long itemCount2 = player.getInventory().getItemCountByItemId(182201409);
/* 161 */         if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0 && itemCount2 < 2L) {
/*     */           
/* 163 */           final int targetObjectId = env.getVisibleObject().getObjectId();
/* 164 */           PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 165 */           ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */               {
/*     */                 
/*     */                 public void run()
/*     */                 {
/* 170 */                   PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/*     */                 }
/*     */               }3000L);
/* 173 */           return true;
/*     */         } 
/*     */ 
/*     */         
/* 177 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 182 */       return defaultQuestEndDialog(env);
/*     */     } 
/* 184 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1487BalaurBones.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */