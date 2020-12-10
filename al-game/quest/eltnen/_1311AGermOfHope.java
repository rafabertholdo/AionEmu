/*     */ package quest.eltnen;
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
/*     */ public class _1311AGermOfHope
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1311;
/*     */   
/*     */   public _1311AGermOfHope() {
/*  46 */     super(Integer.valueOf(1311));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  52 */     this.qe.setNpcQuestData(203997).addOnQuestStart(1311);
/*  53 */     this.qe.setNpcQuestData(203997).addOnTalkEvent(1311);
/*  54 */     this.qe.setNpcQuestData(700164).addOnTalkEvent(1311);
/*  55 */     this.qe.setNpcQuestData(203997).addOnTalkEvent(1311);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  61 */     final Player player = env.getPlayer();
/*  62 */     int targetId = 0;
/*  63 */     if (env.getVisibleObject() instanceof Npc)
/*  64 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  65 */     final QuestState qs = player.getQuestStateList().getQuestState(1311);
/*  66 */     if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */       
/*  68 */       if (targetId == 203997)
/*     */       {
/*  70 */         if (env.getDialogId().intValue() == 25)
/*  71 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  72 */         if (env.getDialogId().intValue() == 1013) {
/*     */           
/*  74 */           if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182201305, 1)))) {
/*  75 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4);
/*     */           }
/*  77 */           return true;
/*     */         } 
/*     */         
/*  80 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*  83 */     } else if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  85 */       switch (targetId) {
/*     */ 
/*     */         
/*     */         case 700164:
/*  89 */           if (qs.getQuestVarById(0) == 0 && env.getDialogId().intValue() == -1) {
/*     */             
/*  91 */             final int targetObjectId = env.getVisibleObject().getObjectId();
/*  92 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/*     */             
/*  94 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/*     */             
/*  96 */             ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 100 */                     if (!player.isTargeting(targetObjectId))
/*     */                       return; 
/* 102 */                     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/*     */                     
/* 104 */                     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/*     */                     
/* 106 */                     ItemService.removeItemFromInventoryByItemId(player, 182201305);
/* 107 */                     qs.setStatus(QuestStatus.REWARD);
/* 108 */                     qs.setQuestVarById(0, 1);
/* 109 */                     _1311AGermOfHope.this.updateQuestStatus(player, qs);
/*     */                   }
/*     */                 }3000L);
/*     */           } 
/*     */ 
/*     */ 
/*     */         
/*     */         case 203997:
/* 117 */           if (qs.getQuestVarById(0) == 1) {
/*     */             
/* 119 */             if (env.getDialogId().intValue() == 25)
/* 120 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 121 */             if (env.getDialogId().intValue() == 33) {
/*     */               
/* 123 */               ItemService.removeItemFromInventoryByItemId(player, 182201305);
/* 124 */               qs.setStatus(QuestStatus.REWARD);
/* 125 */               updateQuestStatus(player, qs);
/* 126 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */             } 
/*     */             
/* 129 */             return defaultQuestEndDialog(env);
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     
/* 134 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 136 */       if (targetId == 203997)
/* 137 */         return defaultQuestEndDialog(env); 
/*     */     } 
/* 139 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1311AGermOfHope.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */