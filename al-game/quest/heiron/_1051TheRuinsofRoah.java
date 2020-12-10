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
/*     */ public class _1051TheRuinsofRoah
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1051;
/*  45 */   private static final int[] npc_ids = new int[] { 204501, 204582, 203882, 278503, 700303, 700217 };
/*     */ 
/*     */   
/*     */   public _1051TheRuinsofRoah() {
/*  49 */     super(Integer.valueOf(1051));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  55 */     this.qe.addQuestLvlUp(1051);
/*  56 */     this.qe.setQuestItemIds(182201602).add(1051);
/*  57 */     for (int npc_id : npc_ids) {
/*  58 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1051);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  64 */     Player player = env.getPlayer();
/*  65 */     QuestState qs = player.getQuestStateList().getQuestState(1051);
/*  66 */     boolean lvlCheck = QuestService.checkLevelRequirement(1051, player.getCommonData().getLevel());
/*  67 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
/*  68 */       return false;
/*     */     }
/*  70 */     QuestState qs2 = player.getQuestStateList().getQuestState(1500);
/*  71 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
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
/*  82 */     QuestState qs = player.getQuestStateList().getQuestState(1051);
/*  83 */     if (qs == null) {
/*  84 */       return false;
/*     */     }
/*  86 */     int var = qs.getQuestVarById(0);
/*  87 */     int targetId = 0;
/*  88 */     if (env.getVisibleObject() instanceof Npc) {
/*  89 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  91 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  93 */       if (targetId == 204501) {
/*  94 */         return defaultQuestEndDialog(env);
/*     */       }
/*  96 */     } else if (qs.getStatus() != QuestStatus.START) {
/*     */       
/*  98 */       return false;
/*     */     } 
/* 100 */     if (targetId == 204501) {
/*     */       
/* 102 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 105 */           if (var == 0)
/* 106 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 107 */           if (var == 4)
/* 108 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 109 */           return false;
/*     */         case 10000:
/* 111 */           if (var == 0) {
/*     */             
/* 113 */             qs.setQuestVarById(0, var + 1);
/* 114 */             updateQuestStatus(player, qs);
/* 115 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */             
/* 117 */             return true;
/*     */           } 
/*     */         case 10004:
/* 120 */           if (var == 4) {
/*     */             
/* 122 */             qs.setQuestVarById(0, var + 1);
/* 123 */             updateQuestStatus(player, qs);
/* 124 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 125 */             return true;
/*     */           } 
/* 127 */           return false;
/*     */       } 
/*     */     
/* 130 */     } else if (targetId == 204582) {
/*     */       
/* 132 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 135 */           if (var == 1)
/* 136 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 137 */           if (var == 3)
/* 138 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/* 139 */           return false;
/*     */         case 10001:
/* 141 */           if (var == 1) {
/*     */             
/* 143 */             qs.setQuestVarById(0, var + 1);
/* 144 */             updateQuestStatus(player, qs);
/* 145 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 146 */             return true;
/*     */           } 
/*     */         case 10003:
/* 149 */           if (var == 3) {
/*     */             
/* 151 */             qs.setQuestVarById(0, var + 1);
/* 152 */             updateQuestStatus(player, qs);
/* 153 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 154 */             ItemService.decreaseItemCountByItemId(player, 182201601, 1L);
/* 155 */             return true;
/*     */           } 
/* 157 */           return false;
/*     */       } 
/*     */     
/* 160 */     } else if (targetId == 203882) {
/*     */       
/* 162 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 165 */           if (var == 5)
/* 166 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/*     */         case 10005:
/* 168 */           if (var == 5) {
/*     */             
/* 170 */             qs.setQuestVarById(0, var + 1);
/* 171 */             updateQuestStatus(player, qs);
/* 172 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 173 */             return true;
/*     */           } 
/* 175 */           return false;
/*     */       } 
/*     */     
/* 178 */     } else if (targetId == 278503) {
/*     */       
/* 180 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 183 */           if (var == 6)
/* 184 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
/* 185 */           if (var == 7)
/* 186 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398); 
/*     */         case 33:
/* 188 */           if (QuestService.collectItemCheck(env, true)) {
/*     */             
/* 190 */             qs.setQuestVarById(0, var + 1);
/* 191 */             qs.setStatus(QuestStatus.REWARD);
/* 192 */             updateQuestStatus(player, qs);
/* 193 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
/*     */           } 
/*     */           
/* 196 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
/*     */         case 10006:
/* 198 */           if (var == 6) {
/*     */             
/* 200 */             qs.setQuestVarById(0, var + 1);
/* 201 */             updateQuestStatus(player, qs);
/* 202 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 203 */             return true;
/*     */           } 
/*     */         case 10007:
/* 206 */           if (var == 7) {
/*     */             
/* 208 */             qs.setQuestVarById(0, var + 1);
/* 209 */             updateQuestStatus(player, qs);
/* 210 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 211 */             return true;
/*     */           } 
/* 213 */           return false;
/*     */       } 
/*     */     
/* 216 */     } else if (targetId == 700217 && qs.getQuestVarById(0) == 2) {
/*     */       
/* 218 */       if (env.getDialogId().intValue() == -1)
/*     */       {
/* 220 */         final int targetObjectId = env.getVisibleObject().getObjectId();
/* 221 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/* 222 */         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 223 */         ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 227 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/* 228 */                 PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/* 229 */                 _1051TheRuinsofRoah.this.sendQuestDialog(player, targetObjectId, 1693);
/*     */               }
/*     */             }3000L);
/*     */       }
/* 233 */       else if (qs.getQuestVarById(0) == 2 && env.getDialogId().intValue() == 10002)
/*     */       {
/* 235 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/* 236 */         ItemService.addItems(player, Collections.singletonList(new QuestItems(182201601, 1)));
/* 237 */         qs.setQuestVarById(0, 3);
/* 238 */         updateQuestStatus(player, qs);
/* 239 */         return true;
/*     */       }
/*     */     
/* 242 */     } else if (targetId == 700303 && qs.getQuestVarById(0) == 7) {
/*     */       
/* 244 */       if (env.getDialogId().intValue() == -1) {
/*     */         
/* 246 */         final int targetObjectId = env.getVisibleObject().getObjectId();
/* 247 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/* 248 */         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 249 */         ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 253 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/* 254 */                 PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/* 255 */                 ItemService.addItems(player, Collections.singletonList(new QuestItems(182201602, 1)));
/*     */               }
/*     */             }3000L);
/*     */       } 
/*     */     } 
/* 260 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\heiron\_1051TheRuinsofRoah.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */