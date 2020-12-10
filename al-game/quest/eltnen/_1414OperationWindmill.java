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
/*     */ public class _1414OperationWindmill
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1414;
/*     */   
/*     */   public _1414OperationWindmill() {
/*  46 */     super(Integer.valueOf(1414));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  52 */     this.qe.setNpcQuestData(203989).addOnQuestStart(1414);
/*  53 */     this.qe.setNpcQuestData(203989).addOnTalkEvent(1414);
/*  54 */     this.qe.setNpcQuestData(700175).addOnTalkEvent(1414);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  60 */     final Player player = env.getPlayer();
/*  61 */     int targetId = 0;
/*  62 */     if (env.getVisibleObject() instanceof Npc)
/*  63 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  64 */     QuestState qs = player.getQuestStateList().getQuestState(1414);
/*  65 */     if (qs == null || qs.getStatus() == QuestStatus.NONE) {
/*     */       
/*  67 */       if (targetId == 203989) {
/*     */         
/*  69 */         if (env.getDialogId().intValue() == 25)
/*  70 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  71 */         if (env.getDialogId().intValue() == 1002) {
/*     */           
/*  73 */           if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182201349, 1)))) {
/*  74 */             return defaultQuestStartDialog(env);
/*     */           }
/*  76 */           return true;
/*     */         } 
/*     */         
/*  79 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */     } else {
/*     */       
/*  83 */       if (qs != null && qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/*  85 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */ 
/*     */       
/*  89 */       if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0)
/*     */       {
/*  91 */         switch (targetId) {
/*     */ 
/*     */           
/*     */           case 700175:
/*  95 */             if (qs.getQuestVarById(0) == 0 && env.getDialogId().intValue() == -1) {
/*     */ 
/*     */               
/*  98 */               final int targetObjectId = env.getVisibleObject().getObjectId();
/*  99 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/*     */               
/* 101 */               PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/*     */               
/* 103 */               ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                   {
/*     */                     public void run()
/*     */                     {
/* 107 */                       if (player.getTarget() == null || player.getTarget().getObjectId() != targetObjectId)
/*     */                         return; 
/* 109 */                       QuestState qs = player.getQuestStateList().getQuestState(1414);
/* 110 */                       qs.setQuestVar(1);
/* 111 */                       qs.setStatus(QuestStatus.REWARD);
/* 112 */                       _1414OperationWindmill.this.updateQuestStatus(player, qs);
/* 113 */                       ItemService.removeItemFromInventoryByItemId(player, 182201349);
/* 114 */                       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/*     */                       
/* 116 */                       PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/*     */                     }
/*     */                   }3000L);
/*     */             } 
/*     */             break;
/*     */         } 
/*     */       }
/*     */     } 
/* 124 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1414OperationWindmill.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */