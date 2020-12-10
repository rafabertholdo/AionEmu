/*     */ package quest.altgard;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
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
/*     */ import java.util.Collections;
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
/*     */ 
/*     */ public class _2213PoisonRootPotentFruit
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2213;
/*     */   
/*     */   public _2213PoisonRootPotentFruit() {
/*  46 */     super(Integer.valueOf(2213));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  52 */     this.qe.setNpcQuestData(203604).addOnQuestStart(2213);
/*  53 */     this.qe.setNpcQuestData(203604).addOnTalkEvent(2213);
/*  54 */     this.qe.setNpcQuestData(700057).addOnTalkEvent(2213);
/*  55 */     this.qe.setNpcQuestData(203604).addOnTalkEvent(2213);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  61 */     final Player player = env.getPlayer();
/*  62 */     int targetId = 0;
/*  63 */     if (env.getVisibleObject() instanceof Npc)
/*  64 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  65 */     final QuestState qs = player.getQuestStateList().getQuestState(2213);
/*  66 */     if (qs == null) {
/*     */       
/*  68 */       if (targetId == 203604)
/*     */       {
/*  70 */         if (env.getDialogId().intValue() == 25) {
/*  71 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  73 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  76 */     } else if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  78 */       switch (targetId) {
/*     */ 
/*     */         
/*     */         case 700057:
/*  82 */           if (qs.getQuestVarById(0) == 0 && env.getDialogId().intValue() == -1) {
/*     */             
/*  84 */             final int targetObjectId = env.getVisibleObject().getObjectId();
/*  85 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/*     */             
/*  87 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/*     */             
/*  89 */             ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/*  93 */                     if (!player.isTargeting(targetObjectId))
/*     */                       return; 
/*  95 */                     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/*     */                     
/*  97 */                     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/*     */                     
/*  99 */                     if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182203208, 1)))) {
/*     */                       
/* 101 */                       qs.setQuestVarById(0, 1);
/* 102 */                       _2213PoisonRootPotentFruit.this.updateQuestStatus(player, qs);
/*     */                     } 
/*     */                   }
/*     */                 }3000L);
/*     */           } 
/*     */ 
/*     */         
/*     */         case 203604:
/* 110 */           if (qs.getQuestVarById(0) == 1) {
/*     */             
/* 112 */             if (env.getDialogId().intValue() == 25)
/* 113 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 114 */             if (env.getDialogId().intValue() == 1009) {
/*     */               
/* 116 */               ItemService.removeItemFromInventoryByItemId(player, 182203208);
/* 117 */               qs.setStatus(QuestStatus.REWARD);
/* 118 */               updateQuestStatus(player, qs);
/* 119 */               return defaultQuestEndDialog(env);
/*     */             } 
/*     */             
/* 122 */             return defaultQuestEndDialog(env);
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     
/* 127 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 129 */       if (targetId == 203604)
/* 130 */         return defaultQuestEndDialog(env); 
/*     */     } 
/* 132 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2213PoisonRootPotentFruit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */