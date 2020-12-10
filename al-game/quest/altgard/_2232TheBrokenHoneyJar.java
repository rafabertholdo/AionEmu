/*     */ package quest.altgard;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
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
/*     */ public class _2232TheBrokenHoneyJar
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2232;
/*     */   
/*     */   public _2232TheBrokenHoneyJar() {
/*  48 */     super(Integer.valueOf(2232));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  54 */     this.qe.setNpcQuestData(203613).addOnQuestStart(2232);
/*  55 */     this.qe.setNpcQuestData(203613).addOnTalkEvent(2232);
/*  56 */     this.qe.setNpcQuestData(203622).addOnTalkEvent(2232);
/*  57 */     this.qe.setNpcQuestData(700061).addOnTalkEvent(2232);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  63 */     final Player player = env.getPlayer();
/*  64 */     int targetId = 0;
/*  65 */     if (env.getVisibleObject() instanceof Npc)
/*  66 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  67 */     QuestState qs = player.getQuestStateList().getQuestState(2232);
/*  68 */     if (targetId == 203613) {
/*     */       
/*  70 */       if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */         
/*  72 */         if (env.getDialogId().intValue() == 25) {
/*  73 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */         }
/*  75 */         return defaultQuestStartDialog(env);
/*     */       } 
/*  77 */       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1) {
/*     */         
/*  79 */         if (env.getDialogId().intValue() == 25)
/*  80 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*  81 */         if (env.getDialogId().intValue() == 33)
/*     */         {
/*  83 */           if (QuestService.collectItemCheck(env, true)) {
/*     */             
/*  85 */             qs.setStatus(QuestStatus.REWARD);
/*  86 */             updateQuestStatus(player, qs);
/*  87 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */           } 
/*     */           
/*  90 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
/*     */         }
/*     */       
/*  93 */       } else if (qs.getStatus() == QuestStatus.REWARD && qs.getQuestVarById(0) == 1) {
/*     */         
/*  95 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     
/*     */     }
/*  99 */     else if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1) {
/*     */       
/* 101 */       switch (targetId) {
/*     */         
/*     */         case 700061:
/* 104 */           qs.setQuestVarById(0, 1);
/* 105 */           updateQuestStatus(player, qs);
/*     */           
/* 107 */           if (qs.getQuestVarById(0) == 1 && env.getDialogId().intValue() == -1) {
/*     */             
/* 109 */             final int targetObjectId = env.getVisibleObject().getObjectId();
/* 110 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/*     */             
/* 112 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/*     */             
/* 114 */             ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 118 */                     if (!player.isTargeting(targetObjectId))
/*     */                       return; 
/* 120 */                     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/*     */                     
/* 122 */                     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/*     */                     
/* 124 */                     if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182203224, 1)))) {
/* 125 */                       ((Npc)player.getTarget()).getController().onDie(null);
/*     */                     }
/*     */                   }
/*     */                 }3000L);
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     
/* 133 */     } else if (targetId == 203622) {
/*     */       
/* 135 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
/*     */         
/* 137 */         if (env.getDialogId().intValue() == 25)
/* 138 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 139 */         if (env.getDialogId().intValue() == 10000)
/*     */         {
/* 141 */           qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 142 */           updateQuestStatus(player, qs);
/* 143 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 144 */           return true;
/*     */         }
/*     */       
/*     */       } 
/* 148 */     } else if (targetId == 203622) {
/*     */       
/* 150 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0 && env.getDialogId().intValue() == -1) {
/* 151 */         return true;
/*     */       }
/*     */     } 
/* 154 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\altgard\_2232TheBrokenHoneyJar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */