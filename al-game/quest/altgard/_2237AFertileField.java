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
/*     */ import com.aionemu.gameserver.services.QuestService;
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
/*     */ public class _2237AFertileField
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2237;
/*     */   
/*     */   public _2237AFertileField() {
/*  47 */     super(Integer.valueOf(2237));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  53 */     this.qe.setNpcQuestData(203629).addOnQuestStart(2237);
/*  54 */     this.qe.setNpcQuestData(203629).addOnTalkEvent(2237);
/*  55 */     this.qe.setNpcQuestData(700145).addOnTalkEvent(2237);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  61 */     final Player player = env.getPlayer();
/*  62 */     int targetId = 0;
/*  63 */     if (env.getVisibleObject() instanceof Npc)
/*  64 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  65 */     QuestState qs = player.getQuestStateList().getQuestState(2237);
/*  66 */     if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */       
/*  68 */       if (targetId == 203629)
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
/*     */         case 700145:
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
/*  99 */                     ItemService.addItems(player, Collections.singletonList(new QuestItems(182203226, 1)));
/* 100 */                     ((Npc)player.getTarget()).getController().onDie(null);
/*     */                   }
/*     */                 }3000L);
/*     */           } 
/*     */ 
/*     */         
/*     */         case 203629:
/* 107 */           if (env.getDialogId().intValue() == 25)
/* 108 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 109 */           if (env.getDialogId().intValue() == 33) {
/* 110 */             if (QuestService.collectItemCheck(env, true)) {
/*     */               
/* 112 */               qs.setStatus(QuestStatus.REWARD);
/* 113 */               updateQuestStatus(player, qs);
/* 114 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */             } 
/*     */             
/* 117 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
/*     */           } 
/*     */           break;
/*     */       } 
/* 121 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 123 */       if (targetId == 203629)
/* 124 */         return defaultQuestEndDialog(env); 
/*     */     } 
/* 126 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2237AFertileField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */