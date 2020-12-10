/*     */ package quest.poeta;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
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
/*     */ 
/*     */ public class _1005BarringtheGate
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1005;
/*     */   
/*     */   public _1005BarringtheGate() {
/*  45 */     super(Integer.valueOf(1005));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  51 */     int[] talkNpcs = { 203067, 203081, 790001, 203085, 203086, 700080, 700081, 700082, 700083, 203067 };
/*  52 */     this.qe.addQuestLvlUp(1005);
/*  53 */     for (int id : talkNpcs) {
/*  54 */       this.qe.setNpcQuestData(id).addOnTalkEvent(1005);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  60 */     Player player = env.getPlayer();
/*  61 */     QuestState qs = player.getQuestStateList().getQuestState(1005);
/*  62 */     if (qs == null) {
/*  63 */       return false;
/*     */     }
/*  65 */     int var = qs.getQuestVarById(0);
/*  66 */     int targetId = 0;
/*  67 */     if (env.getVisibleObject() instanceof Npc) {
/*  68 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  70 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/*  72 */       if (targetId == 203067) {
/*     */         
/*  74 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/*  77 */             if (var == 0)
/*  78 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/*     */           case 10000:
/*  80 */             if (var == 0) {
/*     */               
/*  82 */               qs.setQuestVarById(0, var + 1);
/*  83 */               updateQuestStatus(player, qs);
/*  84 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*  85 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/*  89 */       } else if (targetId == 203081) {
/*     */         
/*  91 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/*  94 */             if (var == 1)
/*  95 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */           case 10001:
/*  97 */             if (var == 1) {
/*     */               
/*  99 */               qs.setQuestVarById(0, var + 1);
/* 100 */               updateQuestStatus(player, qs);
/* 101 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 102 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/* 106 */       } else if (targetId == 790001) {
/*     */         
/* 108 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/* 111 */             if (var == 2)
/* 112 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*     */           case 10002:
/* 114 */             if (var == 2) {
/*     */               
/* 116 */               qs.setQuestVarById(0, var + 1);
/* 117 */               updateQuestStatus(player, qs);
/* 118 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 119 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/* 123 */       } else if (targetId == 203085) {
/*     */         
/* 125 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/* 128 */             if (var == 3)
/* 129 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */           case 10003:
/* 131 */             if (var == 3) {
/*     */               
/* 133 */               qs.setQuestVarById(0, var + 1);
/* 134 */               updateQuestStatus(player, qs);
/* 135 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 136 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/* 140 */       } else if (targetId == 203086) {
/*     */         
/* 142 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/* 145 */             if (var == 4)
/* 146 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*     */           case 10004:
/* 148 */             if (var == 4) {
/*     */               
/* 150 */               qs.setQuestVarById(0, var + 1);
/* 151 */               updateQuestStatus(player, qs);
/* 152 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 153 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/* 157 */       } else if (targetId == 700081) {
/*     */         
/* 159 */         if (var == 5)
/*     */         {
/* 161 */           destroy(6, env);
/* 162 */           return false;
/*     */         }
/*     */       
/* 165 */       } else if (targetId == 700082) {
/*     */         
/* 167 */         if (var == 6)
/*     */         {
/* 169 */           destroy(7, env);
/* 170 */           return false;
/*     */         }
/*     */       
/* 173 */       } else if (targetId == 700083) {
/*     */         
/* 175 */         if (var == 7)
/*     */         {
/* 177 */           destroy(8, env);
/* 178 */           return false;
/*     */         }
/*     */       
/* 181 */       } else if (targetId == 700080) {
/*     */         
/* 183 */         if (var == 8)
/*     */         {
/* 185 */           destroy(-1, env);
/* 186 */           return false;
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 191 */     } else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 193 */       if (targetId == 203067) {
/*     */         
/* 195 */         if (env.getDialogId().intValue() == -1)
/* 196 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/* 197 */         return defaultQuestEndDialog(env);
/*     */       } 
/*     */     } 
/* 200 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 205 */     Player player = env.getPlayer();
/* 206 */     QuestState qs = player.getQuestStateList().getQuestState(1005);
/* 207 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
/* 208 */       return false; 
/* 209 */     int[] quests = { 1001, 1002, 1003, 1004 };
/* 210 */     for (int id : quests) {
/*     */       
/* 212 */       QuestState qs2 = player.getQuestStateList().getQuestState(id);
/* 213 */       if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/* 214 */         return false; 
/*     */     } 
/* 216 */     qs.setStatus(QuestStatus.START);
/* 217 */     updateQuestStatus(player, qs);
/* 218 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void destroy(final int var, QuestEnv env) {
/* 223 */     final int targetObjectId = env.getVisibleObject().getObjectId();
/*     */     
/* 225 */     final Player player = env.getPlayer();
/* 226 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/* 227 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 228 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 232 */             if (player.getTarget().getObjectId() != targetObjectId)
/*     */               return; 
/* 234 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/* 235 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/* 236 */             PacketSendUtility.broadcastPacket(player.getTarget(), (AionServerPacket)new SM_EMOTION((Creature)player.getTarget(), EmotionType.EMOTE, 128, 0));
/* 237 */             QuestState qs = player.getQuestStateList().getQuestState(1005);
/* 238 */             if (var != -1) {
/* 239 */               qs.setQuestVarById(0, var);
/*     */             } else {
/*     */               
/* 242 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 21));
/* 243 */               qs.setStatus(QuestStatus.REWARD);
/*     */             } 
/* 245 */             _1005BarringtheGate.this.updateQuestStatus(player, qs);
/*     */           }
/*     */         }3000L);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\poeta\_1005BarringtheGate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */