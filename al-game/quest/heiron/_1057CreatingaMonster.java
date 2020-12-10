/*     */ package quest.heiron;
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
/*     */ public class _1057CreatingaMonster
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1057;
/*  46 */   private static final int[] npc_ids = new int[] { 204502, 204619, 700218, 700279, 204500 };
/*     */ 
/*     */   
/*     */   public _1057CreatingaMonster() {
/*  50 */     super(Integer.valueOf(1057));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  56 */     this.qe.addQuestLvlUp(1057);
/*  57 */     this.qe.addOnEnterWorld(1057);
/*  58 */     this.qe.setNpcQuestData(700219).addOnKillEvent(1057);
/*  59 */     this.qe.setNpcQuestData(212211).addOnKillEvent(1057);
/*  60 */     for (int npc_id : npc_ids) {
/*  61 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1057);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  67 */     Player player = env.getPlayer();
/*  68 */     QuestState qs = player.getQuestStateList().getQuestState(1057);
/*  69 */     boolean lvlCheck = QuestService.checkLevelRequirement(1057, player.getCommonData().getLevel());
/*  70 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
/*  71 */       return false;
/*     */     }
/*  73 */     QuestState qs2 = player.getQuestStateList().getQuestState(1056);
/*  74 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
/*  75 */       return false; 
/*  76 */     qs.setStatus(QuestStatus.START);
/*  77 */     updateQuestStatus(player, qs);
/*  78 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  84 */     final Player player = env.getPlayer();
/*  85 */     final QuestState qs = player.getQuestStateList().getQuestState(1057);
/*  86 */     if (qs == null) {
/*  87 */       return false;
/*     */     }
/*  89 */     int var = qs.getQuestVarById(0);
/*  90 */     int targetId = 0;
/*  91 */     if (env.getVisibleObject() instanceof Npc) {
/*  92 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  94 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  96 */       if (targetId == 204500) {
/*     */         
/*  98 */         if (env.getDialogId().intValue() == -1)
/*  99 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
/* 100 */         if (env.getDialogId().intValue() == 1009)
/* 101 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5); 
/* 102 */         return defaultQuestEndDialog(env);
/*     */       } 
/* 104 */       return false;
/*     */     } 
/* 106 */     if (qs.getStatus() != QuestStatus.START)
/*     */     {
/* 108 */       return false;
/*     */     }
/* 110 */     if (targetId == 204502) {
/*     */       
/* 112 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 115 */           if (var == 0)
/* 116 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 117 */           if (var == 3)
/* 118 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */         case 2036:
/* 120 */           if (var == 3)
/* 121 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 190)); 
/* 122 */           return false;
/*     */         case 10000:
/* 124 */           if (var == 0) {
/*     */             
/* 126 */             qs.setQuestVarById(0, var + 1);
/* 127 */             updateQuestStatus(player, qs);
/* 128 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 129 */             return true;
/*     */           } 
/*     */         case 10003:
/* 132 */           if (var == 3) {
/*     */             
/* 134 */             qs.setQuestVarById(0, var + 1);
/* 135 */             updateQuestStatus(player, qs);
/* 136 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 137 */             return true;
/*     */           } 
/* 139 */           return false;
/*     */       } 
/*     */     
/* 142 */     } else if (targetId == 204619) {
/*     */       
/* 144 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 147 */           if (var == 1)
/* 148 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */         case 10001:
/* 150 */           if (var == 1) {
/*     */             
/* 152 */             qs.setQuestVarById(0, var + 1);
/* 153 */             updateQuestStatus(player, qs);
/* 154 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 155 */             return true;
/*     */           } 
/* 157 */           return false;
/*     */       } 
/*     */     
/* 160 */     } else if (targetId == 700218 && qs.getQuestVarById(0) == 2) {
/*     */       
/* 162 */       if (env.getDialogId().intValue() == -1)
/*     */       {
/* 164 */         final int targetObjectId = env.getVisibleObject().getObjectId();
/* 165 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/* 166 */         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 167 */         ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 171 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/* 172 */                 PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/* 173 */                 _1057CreatingaMonster.this.sendQuestDialog(player, targetObjectId, 1693);
/*     */               }
/*     */             }3000L);
/*     */       }
/* 177 */       else if (qs.getQuestVarById(0) == 2 && env.getDialogId().intValue() == 10002)
/*     */       {
/* 179 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/* 180 */         ItemService.addItems(player, Collections.singletonList(new QuestItems(182201616, 1)));
/* 181 */         qs.setQuestVarById(0, 3);
/* 182 */         updateQuestStatus(player, qs);
/* 183 */         return true;
/*     */       }
/*     */     
/* 186 */     } else if (targetId == 700279 && qs.getQuestVarById(0) == 9) {
/*     */       
/* 188 */       if (env.getDialogId().intValue() == -1) {
/*     */         
/* 190 */         final int targetObjectId = env.getVisibleObject().getObjectId();
/* 191 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/* 192 */         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 193 */         ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 197 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/* 198 */                 PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/* 199 */                 qs.setStatus(QuestStatus.REWARD);
/* 200 */                 _1057CreatingaMonster.this.updateQuestStatus(player, qs);
/*     */               }
/*     */             }3000L);
/*     */       } 
/*     */     } 
/* 205 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onEnterWorldEvent(QuestEnv env) {
/* 211 */     Player player = env.getPlayer();
/* 212 */     QuestState qs = player.getQuestStateList().getQuestState(1057);
/* 213 */     if (qs != null && qs.getStatus() == QuestStatus.START)
/*     */     {
/* 215 */       if (player.getWorldId() == 310050000 && qs.getQuestVarById(0) == 4) {
/*     */         
/* 217 */         qs.setQuestVar(5);
/* 218 */         updateQuestStatus(player, qs);
/*     */       } 
/*     */     }
/* 221 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 227 */     Player player = env.getPlayer();
/* 228 */     QuestState qs = player.getQuestStateList().getQuestState(1057);
/* 229 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 230 */       return false;
/*     */     }
/* 232 */     int targetId = 0;
/* 233 */     if (env.getVisibleObject() instanceof Npc) {
/* 234 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 236 */     if (targetId == 700219 && qs.getQuestVarById(0) < 8) {
/*     */       
/* 238 */       qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 239 */       updateQuestStatus(player, qs);
/*     */     }
/* 241 */     else if (targetId == 212211 && qs.getQuestVarById(0) == 8) {
/*     */       
/* 243 */       qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 244 */       updateQuestStatus(player, qs);
/*     */     } 
/* 246 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\heiron\_1057CreatingaMonster.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */