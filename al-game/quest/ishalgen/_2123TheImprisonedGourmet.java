/*     */ package quest.ishalgen;
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
/*     */ public class _2123TheImprisonedGourmet
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2123;
/*     */   
/*     */   public _2123TheImprisonedGourmet() {
/*  37 */     super(Integer.valueOf(2123));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  43 */     this.qe.setNpcQuestData(203550).addOnQuestStart(2123);
/*  44 */     this.qe.setNpcQuestData(203550).addOnTalkEvent(2123);
/*  45 */     this.qe.setNpcQuestData(700128).addOnTalkEvent(2123);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  51 */     final Player player = env.getPlayer();
/*  52 */     final QuestState qs = player.getQuestStateList().getQuestState(2123);
/*  53 */     int targetId = 0;
/*  54 */     if (player.getCommonData().getLevel() < 7)
/*  55 */       return false; 
/*  56 */     if (env.getVisibleObject() instanceof Npc)
/*  57 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  58 */     if (targetId == 203550) {
/*     */       
/*  60 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */ 
/*     */         
/*  63 */         if (env.getDialogId().intValue() == 25) {
/*  64 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  66 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */       
/*  69 */       if (qs.getStatus() == QuestStatus.START) {
/*     */ 
/*     */         
/*  72 */         if (env.getDialogId().intValue() == 25 && qs.getQuestVarById(0) == 0)
/*     */         {
/*  74 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */         }
/*  76 */         if (env.getDialogId().intValue() == 10000 && qs.getQuestVarById(0) == 0) {
/*     */           
/*  78 */           long itemCount = player.getInventory().getItemCountByItemId(182203121);
/*  79 */           if (itemCount > 0L) {
/*     */             
/*  81 */             ItemService.removeItemFromInventoryByItemId(player, 182004687);
/*  82 */             qs.setQuestVar(5);
/*  83 */             updateQuestStatus(player, qs);
/*  84 */             qs.setStatus(QuestStatus.REWARD);
/*  85 */             updateQuestStatus(player, qs);
/*  86 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */           } 
/*     */ 
/*     */           
/*  90 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
/*     */         } 
/*     */         
/*  93 */         if (env.getDialogId().intValue() == 10001 && qs.getQuestVarById(0) == 0) {
/*     */           
/*  95 */           long itemCount = player.getInventory().getItemCountByItemId(182203122);
/*  96 */           if (itemCount > 0L) {
/*     */             
/*  98 */             ItemService.removeItemFromInventoryByItemId(player, 182203122);
/*  99 */             qs.setQuestVar(6);
/* 100 */             updateQuestStatus(player, qs);
/* 101 */             qs.setStatus(QuestStatus.REWARD);
/* 102 */             updateQuestStatus(player, qs);
/* 103 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);
/*     */           } 
/*     */ 
/*     */           
/* 107 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
/*     */         } 
/*     */         
/* 110 */         if (env.getDialogId().intValue() == 10002 && qs.getQuestVarById(0) == 0) {
/*     */           
/* 112 */           long itemCount = player.getInventory().getItemCountByItemId(182203123);
/* 113 */           if (itemCount > 0L) {
/*     */             
/* 115 */             ItemService.removeItemFromInventoryByItemId(player, 182203123);
/* 116 */             qs.setStatus(QuestStatus.REWARD);
/* 117 */             updateQuestStatus(player, qs);
/* 118 */             qs.setQuestVar(7);
/* 119 */             updateQuestStatus(player, qs);
/* 120 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 7);
/*     */           } 
/*     */ 
/*     */           
/* 124 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
/*     */         } 
/*     */ 
/*     */         
/* 128 */         return defaultQuestEndDialog(env);
/*     */       } 
/* 130 */       if (qs.getStatus() == QuestStatus.REWARD) {
/*     */         
/* 132 */         if (env.getDialogId().intValue() == 25 && qs.getQuestVarById(0) == 5)
/*     */         {
/* 134 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */         }
/* 136 */         if (env.getDialogId().intValue() == 25 && qs.getQuestVarById(0) == 6)
/*     */         {
/* 138 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);
/*     */         }
/* 140 */         if (env.getDialogId().intValue() == 25 && qs.getQuestVarById(0) == 7)
/*     */         {
/* 142 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 7);
/*     */         }
/*     */ 
/*     */         
/* 146 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 151 */       return defaultQuestEndDialog(env);
/*     */     } 
/*     */     
/* 154 */     if (targetId == 700128) {
/*     */       
/* 156 */       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
/*     */         
/* 158 */         int targetObjectId = env.getVisibleObject().getObjectId();
/* 159 */         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 160 */         ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */             {
/*     */               
/*     */               public void run()
/*     */               {
/* 165 */                 qs.setQuestVar(0);
/* 166 */                 _2123TheImprisonedGourmet.this.updateQuestStatus(player, qs);
/*     */               }
/*     */             }3000L);
/* 169 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 173 */       return defaultQuestEndDialog(env);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 178 */     return defaultQuestEndDialog(env);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ishalgen\_2123TheImprisonedGourmet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */