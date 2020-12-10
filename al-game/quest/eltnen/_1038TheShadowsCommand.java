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
/*     */ public class _1038TheShadowsCommand
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1038;
/*  45 */   private static final int[] npc_ids = new int[] { 203933, 700172, 203991, 700162 };
/*     */ 
/*     */   
/*     */   public _1038TheShadowsCommand() {
/*  49 */     super(Integer.valueOf(1038));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  55 */     this.qe.addQuestLvlUp(1038);
/*  56 */     this.qe.setNpcQuestData(204005).addOnKillEvent(1038);
/*  57 */     for (int npc_id : npc_ids) {
/*  58 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1038);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  64 */     Player player = env.getPlayer();
/*  65 */     QuestState qs = player.getQuestStateList().getQuestState(1038);
/*  66 */     boolean lvlCheck = QuestService.checkLevelRequirement(1038, player.getCommonData().getLevel());
/*  67 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
/*  68 */       return false;
/*     */     }
/*  70 */     qs.setStatus(QuestStatus.START);
/*  71 */     updateQuestStatus(player, qs);
/*  72 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  78 */     final Player player = env.getPlayer();
/*  79 */     final QuestState qs = player.getQuestStateList().getQuestState(1038);
/*  80 */     if (qs == null) {
/*  81 */       return false;
/*     */     }
/*  83 */     int var = qs.getQuestVarById(0);
/*  84 */     int targetId = 0;
/*  85 */     if (env.getVisibleObject() instanceof Npc) {
/*  86 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  88 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  90 */       if (targetId == 203991) {
/*  91 */         return defaultQuestEndDialog(env);
/*     */       }
/*  93 */     } else if (qs.getStatus() != QuestStatus.START) {
/*     */       
/*  95 */       return false;
/*     */     } 
/*  97 */     if (targetId == 700162 && var == 0) {
/*     */       final int targetObjectId;
/*  99 */       switch (env.getDialogId().intValue()) {
/*     */ 
/*     */         
/*     */         case -1:
/* 103 */           targetObjectId = env.getVisibleObject().getObjectId();
/* 104 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/* 105 */           PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 106 */           ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 110 */                   PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/* 111 */                   PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/* 112 */                   PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 34));
/* 113 */                   qs.setQuestVarById(0, 1);
/* 114 */                   _1038TheShadowsCommand.this.updateQuestStatus(player, qs);
/*     */                 }
/*     */               }3000L);
/*     */           
/* 118 */           return false;
/*     */       } 
/*     */     
/* 121 */     } else if (targetId == 203933) {
/*     */       
/* 123 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 126 */           if (var == 1)
/* 127 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 128 */           if (var == 3)
/* 129 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1694); 
/* 130 */           if (var == 4)
/* 131 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */         case 33:
/* 133 */           if (QuestService.collectItemCheck(env, true)) {
/* 134 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2035);
/*     */           }
/* 136 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
/*     */         case 10001:
/* 138 */           if (var == 1) {
/*     */             
/* 140 */             qs.setQuestVarById(0, var + 1);
/* 141 */             updateQuestStatus(player, qs);
/* 142 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 143 */             return true;
/*     */           } 
/*     */         case 10002:
/* 146 */           if (var == 3) {
/*     */             
/* 148 */             qs.setQuestVarById(0, var + 1);
/* 149 */             updateQuestStatus(player, qs);
/* 150 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 151 */             return true;
/*     */           } 
/*     */         case 10003:
/* 154 */           if (var == 4) {
/*     */             
/* 156 */             qs.setQuestVarById(0, var + 2);
/* 157 */             updateQuestStatus(player, qs);
/* 158 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 159 */             return true;
/*     */           } 
/* 161 */           return false;
/*     */       } 
/*     */     
/* 164 */     } else if (targetId == 700172 && var == 2) {
/*     */       final int targetObjectId;
/* 166 */       switch (env.getDialogId().intValue()) {
/*     */ 
/*     */         
/*     */         case -1:
/* 170 */           targetObjectId = env.getVisibleObject().getObjectId();
/* 171 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/* 172 */           PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 173 */           ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 177 */                   PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/* 178 */                   PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/* 179 */                   ItemService.addItems(player, Collections.singletonList(new QuestItems(182201007, 1)));
/* 180 */                   qs.setQuestVar(3);
/* 181 */                   _1038TheShadowsCommand.this.updateQuestStatus(player, qs);
/*     */                 }
/*     */               }3000L);
/*     */           
/* 185 */           return false;
/*     */       } 
/*     */     
/* 188 */     } else if (targetId == 203991) {
/*     */       
/* 190 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 193 */           if (var == 6)
/* 194 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*     */         case 10004:
/* 196 */           if (var == 6) {
/*     */             
/* 198 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 35));
/* 199 */             qs.setQuestVarById(0, 7);
/* 200 */             updateQuestStatus(player, qs);
/* 201 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/* 202 */             QuestService.addNewSpawn(210020000, 1, 204005, 1768.16F, 924.47F, 422.02F, (byte)0, true);
/* 203 */             return true;
/*     */           } 
/* 205 */           return false;
/*     */       } 
/*     */     } 
/* 208 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 214 */     Player player = env.getPlayer();
/* 215 */     QuestState qs = player.getQuestStateList().getQuestState(1038);
/* 216 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 217 */       return false;
/*     */     }
/* 219 */     int targetId = 0;
/* 220 */     if (env.getVisibleObject() instanceof Npc) {
/* 221 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 223 */     if (qs.getStatus() != QuestStatus.START || qs.getQuestVarById(0) != 7) {
/* 224 */       return false;
/*     */     }
/* 226 */     if (targetId == 204005) {
/*     */       
/* 228 */       qs.setStatus(QuestStatus.REWARD);
/* 229 */       updateQuestStatus(player, qs);
/* 230 */       return true;
/*     */     } 
/* 232 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1038TheShadowsCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */