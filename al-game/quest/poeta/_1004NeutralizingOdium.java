/*     */ package quest.poeta;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
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
/*     */ public class _1004NeutralizingOdium
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1004;
/*     */   
/*     */   public _1004NeutralizingOdium() {
/*  49 */     super(Integer.valueOf(1004));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  55 */     this.qe.addQuestLvlUp(1004);
/*  56 */     this.qe.setNpcQuestData(203082).addOnTalkEvent(1004);
/*  57 */     this.qe.setNpcQuestData(700030).addOnTalkEvent(1004);
/*  58 */     this.qe.setNpcQuestData(790001).addOnTalkEvent(1004);
/*  59 */     this.qe.setNpcQuestData(203067).addOnTalkEvent(1004);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  65 */     final Player player = env.getPlayer();
/*  66 */     QuestState qs = player.getQuestStateList().getQuestState(1004);
/*  67 */     if (qs == null) {
/*  68 */       return false;
/*     */     }
/*  70 */     int var = qs.getQuestVarById(0);
/*  71 */     int targetId = 0;
/*  72 */     if (env.getVisibleObject() instanceof Npc) {
/*  73 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  75 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  77 */       if (targetId == 203082) {
/*     */         
/*  79 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/*  82 */             if (var == 0)
/*  83 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  84 */             if (var == 5)
/*  85 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */           case 1013:
/*  87 */             if (var == 0)
/*  88 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 19)); 
/*  89 */             return false;
/*     */           case 10000:
/*  91 */             qs.setQuestVarById(0, var + 1);
/*  92 */             updateQuestStatus(player, qs);
/*  93 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  94 */             return true;
/*     */           case 10002:
/*  96 */             if (var == 5) {
/*     */               
/*  98 */               qs.setStatus(QuestStatus.REWARD);
/*  99 */               updateQuestStatus(player, qs);
/* 100 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 101 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/* 105 */       } else if ((targetId == 700030 && var == 1) || var == 4) {
/*     */         final int targetObjectId;
/* 107 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case -1:
/* 110 */             targetObjectId = env.getVisibleObject().getObjectId();
/* 111 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/* 112 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 113 */             ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 117 */                     if (!player.isTargeting(targetObjectId)) {
/*     */                       return;
/*     */                     }
/* 120 */                     if (player.getTarget() == null || !(player.getTarget() instanceof Creature)) {
/*     */                       
/* 122 */                       PacketSendUtility.sendMessage(player, "Invalid target selected.");
/*     */                       return;
/*     */                     } 
/* 125 */                     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/* 126 */                     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/* 127 */                     QuestState qs = player.getQuestStateList().getQuestState(1004);
/* 128 */                     if (qs.getQuestVarById(0) == 1)
/* 129 */                       ItemService.addItems(player, Collections.singletonList(new QuestItems(182200005, 1))); 
/* 130 */                     if (qs.getQuestVarById(0) == 4)
/* 131 */                       ItemService.decreaseItemCountByItemId(player, 182200006, 1L); 
/* 132 */                     qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 133 */                     _1004NeutralizingOdium.this.updateQuestStatus(player, qs);
/* 134 */                     PacketSendUtility.broadcastPacket(player.getTarget(), (AionServerPacket)new SM_EMOTION((Creature)player.getTarget(), EmotionType.EMOTE, 128, 0));
/*     */                   }
/*     */                 }3000L);
/* 137 */             return false;
/*     */         } 
/*     */       
/* 140 */       } else if (targetId == 790001) {
/*     */         
/* 142 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/* 145 */             if (var == 2)
/* 146 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 147 */             if (var == 3)
/* 148 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 149 */             if (var == 11)
/* 150 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1694); 
/*     */           case 10001:
/* 152 */             if (var == 2) {
/*     */               
/* 154 */               qs.setQuestVarById(0, var + 1);
/* 155 */               updateQuestStatus(player, qs);
/* 156 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 157 */               return true;
/*     */             } 
/*     */           case 10002:
/* 160 */             if (var == 11) {
/*     */               
/* 162 */               qs.setQuestVarById(0, 4);
/* 163 */               updateQuestStatus(player, qs);
/* 164 */               ItemService.decreaseItemCountByItemId(player, 182200005, 1L);
/* 165 */               ItemService.addItems(player, Collections.singletonList(new QuestItems(182200006, 1)));
/* 166 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 167 */               return true;
/*     */             } 
/*     */           
/*     */           case 33:
/* 171 */             if (QuestService.collectItemCheck(env, true)) {
/*     */               
/* 173 */               qs.setQuestVarById(0, 11);
/* 174 */               updateQuestStatus(player, qs);
/* 175 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1694);
/*     */             } 
/*     */             
/* 178 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1779);
/*     */         } 
/*     */ 
/*     */       
/*     */       } 
/* 183 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 185 */       if (targetId == 203067)
/* 186 */         return defaultQuestEndDialog(env); 
/*     */     } 
/* 188 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 194 */     Player player = env.getPlayer();
/* 195 */     QuestState qs = player.getQuestStateList().getQuestState(1004);
/* 196 */     if (qs == null) {
/* 197 */       return false;
/*     */     }
/* 199 */     boolean lvlCheck = QuestService.checkLevelRequirement(1004, player.getCommonData().getLevel());
/* 200 */     if (!lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
/* 201 */       return false; 
/* 202 */     qs.setStatus(QuestStatus.START);
/* 203 */     updateQuestStatus(player, qs);
/* 204 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\poeta\_1004NeutralizingOdium.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */