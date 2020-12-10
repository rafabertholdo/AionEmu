/*     */ package quest.eltnen;
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
/*     */ public class _1041ADangerousArtifact
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1041;
/*     */   
/*     */   public _1041ADangerousArtifact() {
/*  49 */     super(Integer.valueOf(1041));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  55 */     this.qe.setNpcQuestData(203901).addOnTalkEvent(1041);
/*  56 */     this.qe.setNpcQuestData(204015).addOnTalkEvent(1041);
/*  57 */     this.qe.setNpcQuestData(700267).addOnTalkEvent(1041);
/*  58 */     this.qe.setNpcQuestData(203833).addOnTalkEvent(1041);
/*  59 */     this.qe.setNpcQuestData(278500).addOnTalkEvent(1041);
/*  60 */     this.qe.setNpcQuestData(204042).addOnTalkEvent(1041);
/*  61 */     this.qe.setNpcQuestData(700181).addOnTalkEvent(1041);
/*  62 */     this.qe.addQuestLvlUp(1041);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  68 */     Player player = env.getPlayer();
/*  69 */     QuestState qs = player.getQuestStateList().getQuestState(1041);
/*  70 */     boolean lvlCheck = QuestService.checkLevelRequirement(1041, player.getCommonData().getLevel());
/*  71 */     if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
/*  72 */       return false; 
/*  73 */     qs.setStatus(QuestStatus.START);
/*  74 */     updateQuestStatus(player, qs);
/*  75 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  81 */     final Player player = env.getPlayer();
/*  82 */     int targetId = 0;
/*  83 */     if (env.getVisibleObject() instanceof Npc)
/*  84 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/*  85 */     QuestState qs = player.getQuestStateList().getQuestState(1041);
/*  86 */     if (qs == null)
/*  87 */       return false; 
/*  88 */     if (targetId == 203901) {
/*     */       
/*  90 */       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
/*     */         
/*  92 */         if (env.getDialogId().intValue() == 25)
/*  93 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*  94 */         if (env.getDialogId().intValue() == 10000) {
/*     */           
/*  96 */           qs.setQuestVar(1);
/*  97 */           updateQuestStatus(player, qs);
/*  98 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 100 */           return true;
/*     */         } 
/*     */         
/* 103 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */       
/* 106 */       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 3) {
/*     */         
/* 108 */         if (env.getDialogId().intValue() == 25)
/* 109 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 110 */         if (env.getDialogId().intValue() == 10002) {
/*     */           
/* 112 */           qs.setQuestVar(4);
/* 113 */           updateQuestStatus(player, qs);
/* 114 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 116 */           return true;
/*     */         } 
/*     */         
/* 119 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */       
/* 122 */       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 6) {
/*     */         
/* 124 */         if (env.getDialogId().intValue() == 25)
/* 125 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/* 126 */         if (env.getDialogId().intValue() == 10005) {
/*     */           
/* 128 */           qs.setQuestVar(7);
/* 129 */           updateQuestStatus(player, qs);
/* 130 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 132 */           return true;
/*     */         } 
/*     */         
/* 135 */         return defaultQuestStartDialog(env);
/*     */       } 
/* 137 */       if (qs.getStatus() == QuestStatus.REWARD)
/*     */       {
/* 139 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     }
/* 142 */     else if (targetId == 204015) {
/*     */       
/* 144 */       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1)
/*     */       {
/* 146 */         if (env.getDialogId().intValue() == 25)
/* 147 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 148 */         if (env.getDialogId().intValue() == 10001) {
/*     */           
/* 150 */           qs.setQuestVar(2);
/* 151 */           updateQuestStatus(player, qs);
/* 152 */           QuestService.addNewSpawn(210020000, player.getInstanceId(), 700267, 2265.621F, 2357.8164F, 277.8047F, (byte)0, true);
/* 153 */           QuestService.addNewSpawn(210020000, player.getInstanceId(), 700267, 1827.1799F, 2537.9143F, 267.5F, (byte)0, true);
/* 154 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 156 */           return true;
/*     */         } 
/*     */         
/* 159 */         return defaultQuestStartDialog(env);
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 165 */     else if (targetId == 203833) {
/*     */       
/* 167 */       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 4)
/*     */       {
/* 169 */         if (env.getDialogId().intValue() == 25)
/* 170 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 171 */         if (env.getDialogId().intValue() == 10003) {
/*     */           
/* 173 */           qs.setQuestVar(5);
/* 174 */           updateQuestStatus(player, qs);
/* 175 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 177 */           return true;
/*     */         } 
/*     */         
/* 180 */         return defaultQuestStartDialog(env);
/*     */       
/*     */       }
/*     */     
/*     */     }
/* 185 */     else if (targetId == 278500) {
/*     */       
/* 187 */       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 5)
/*     */       {
/* 189 */         if (env.getDialogId().intValue() == 25)
/* 190 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 191 */         if (env.getDialogId().intValue() == 10004) {
/*     */           
/* 193 */           qs.setQuestVar(6);
/* 194 */           updateQuestStatus(player, qs);
/* 195 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 197 */           return true;
/*     */         } 
/*     */         
/* 200 */         return defaultQuestStartDialog(env);
/*     */       }
/*     */     
/*     */     }
/* 204 */     else if (targetId == 204042) {
/*     */       
/* 206 */       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 7) {
/*     */         
/* 208 */         if (env.getDialogId().intValue() == 25)
/* 209 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
/* 210 */         if (env.getDialogId().intValue() == 10006) {
/*     */           
/* 212 */           qs.setQuestVar(8);
/* 213 */           ItemService.addItems(player, Collections.singletonList(new QuestItems(182201011, 1)));
/* 214 */           updateQuestStatus(player, qs);
/* 215 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 217 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 37));
/* 218 */           return true;
/*     */         } 
/*     */         
/* 221 */         return defaultQuestStartDialog(env);
/*     */       } 
/*     */ 
/*     */       
/* 225 */       if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 9)
/*     */       {
/* 227 */         if (env.getDialogId().intValue() == 25)
/* 228 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398); 
/* 229 */         if (env.getDialogId().intValue() == 10007) {
/*     */           
/* 231 */           qs.setStatus(QuestStatus.REWARD);
/* 232 */           updateQuestStatus(player, qs);
/* 233 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */           
/* 235 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 38));
/* 236 */           return true;
/*     */         } 
/*     */         
/* 239 */         return defaultQuestStartDialog(env);
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 245 */     else if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 2) {
/*     */       
/* 247 */       switch (targetId) {
/*     */ 
/*     */         
/*     */         case 700267:
/* 251 */           if (qs.getQuestVarById(0) == 2 && env.getDialogId().intValue() == -1) {
/*     */ 
/*     */             
/* 254 */             final int targetObjectId = env.getVisibleObject().getObjectId();
/* 255 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/*     */             
/* 257 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/*     */             
/* 259 */             ThreadPoolManager.getInstance().schedule(new Runnable() {
/* 260 */                   final QuestState qs = player.getQuestStateList().getQuestState(1041);
/*     */ 
/*     */                   
/*     */                   public void run() {
/* 264 */                     this.qs.setQuestVar(3);
/* 265 */                     _1041ADangerousArtifact.this.updateQuestStatus(player, this.qs);
/* 266 */                     if (player.getTarget() == null || player.getTarget().getObjectId() != targetObjectId)
/*     */                       return; 
/* 268 */                     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/*     */                     
/* 270 */                     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/*     */                   }
/*     */                 }3000L);
/*     */           } 
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 280 */     } else if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 8) {
/*     */       
/* 282 */       switch (targetId) {
/*     */ 
/*     */         
/*     */         case 700181:
/* 286 */           if (qs.getQuestVarById(0) == 8 && env.getDialogId().intValue() == -1) {
/*     */ 
/*     */             
/* 289 */             final int targetObjectId = env.getVisibleObject().getObjectId();
/* 290 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/*     */             
/* 292 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/*     */             
/* 294 */             ThreadPoolManager.getInstance().schedule(new Runnable() {
/* 295 */                   final QuestState qs = player.getQuestStateList().getQuestState(1041);
/*     */ 
/*     */                   
/*     */                   public void run() {
/* 299 */                     this.qs.setQuestVar(9);
/* 300 */                     _1041ADangerousArtifact.this.updateQuestStatus(player, this.qs);
/* 301 */                     ItemService.decreaseItemCountByItemId(player, 182201014, 1L);
/* 302 */                     if (player.getTarget() == null || player.getTarget().getObjectId() != targetObjectId)
/*     */                       return; 
/* 304 */                     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/*     */                     
/* 306 */                     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/*     */                   }
/*     */                 }3000L);
/*     */           } 
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 315 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1041ADangerousArtifact.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */