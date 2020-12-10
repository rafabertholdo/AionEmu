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
/*     */ public class _1035RefreshingtheSprings
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1035;
/*  45 */   private static final int[] npc_ids = new int[] { 203917, 203992, 700158, 203965, 203968, 203987, 700160, 203934, 700159 };
/*     */ 
/*     */   
/*     */   public _1035RefreshingtheSprings() {
/*  49 */     super(Integer.valueOf(1035));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  55 */     this.qe.addQuestLvlUp(1035);
/*  56 */     for (int npc_id : npc_ids) {
/*  57 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1035);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  63 */     Player player = env.getPlayer();
/*  64 */     QuestState qs = player.getQuestStateList().getQuestState(1035);
/*  65 */     boolean lvlCheck = QuestService.checkLevelRequirement(1035, player.getCommonData().getLevel());
/*  66 */     if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
/*  67 */       return false;
/*     */     }
/*  69 */     qs.setStatus(QuestStatus.START);
/*  70 */     updateQuestStatus(player, qs);
/*  71 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  77 */     final Player player = env.getPlayer();
/*  78 */     final QuestState qs = player.getQuestStateList().getQuestState(1035);
/*  79 */     if (qs == null) {
/*  80 */       return false;
/*     */     }
/*  82 */     int var = qs.getQuestVarById(0);
/*  83 */     int targetId = 0;
/*  84 */     if (env.getVisibleObject() instanceof Npc) {
/*  85 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  87 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/*  89 */       if (targetId == 203917) {
/*  90 */         return defaultQuestEndDialog(env);
/*     */       }
/*  92 */     } else if (qs.getStatus() != QuestStatus.START) {
/*     */       
/*  94 */       return false;
/*     */     } 
/*  96 */     if (targetId == 203917) {
/*     */       
/*  98 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 101 */           if (var == 0)
/* 102 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 103 */           if (var == 4)
/* 104 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 105 */           return false;
/*     */         case 10000:
/* 107 */           if (var == 0) {
/*     */             
/* 109 */             qs.setQuestVarById(0, var + 1);
/* 110 */             updateQuestStatus(player, qs);
/* 111 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 112 */             return true;
/*     */           } 
/*     */         case 10001:
/* 115 */           if (var == 4) {
/*     */             
/* 117 */             qs.setQuestVarById(0, var + 1);
/* 118 */             updateQuestStatus(player, qs);
/* 119 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 120 */             return true;
/*     */           } 
/* 122 */           return false;
/*     */       } 
/*     */     
/* 125 */     } else if (targetId == 203992) {
/*     */       
/* 127 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 130 */           if (var == 1)
/* 131 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/* 132 */           if (var == 3)
/* 133 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*     */         case 10001:
/* 135 */           if (var == 1) {
/*     */             
/* 137 */             qs.setQuestVarById(0, var + 1);
/* 138 */             updateQuestStatus(player, qs);
/* 139 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 140 */             return true;
/*     */           } 
/*     */         case 10002:
/* 143 */           if (var == 3) {
/*     */             
/* 145 */             qs.setQuestVarById(0, var + 1);
/* 146 */             updateQuestStatus(player, qs);
/* 147 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 148 */             return true;
/*     */           } 
/* 150 */           return false;
/*     */       } 
/*     */     } else {
/* 153 */       if (targetId == 700158 && var == 2) {
/*     */         
/* 155 */         if (env.getDialogId().intValue() == -1 && player.getInventory().getItemCountByItemId(182201014) == 1L) {
/*     */           
/* 157 */           final int targetObjectId = env.getVisibleObject().getObjectId();
/* 158 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/* 159 */           PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 160 */           ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */               {
/*     */                 public void run()
/*     */                 {
/* 164 */                   PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/* 165 */                   PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/* 166 */                   ItemService.decreaseItemCountByItemId(player, 182201014, 1L);
/* 167 */                   qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 168 */                   _1035RefreshingtheSprings.this.updateQuestStatus(player, qs);
/*     */                 }
/*     */               }3000L);
/*     */         } 
/* 172 */         return false;
/*     */       } 
/* 174 */       if (targetId == 203965) {
/*     */         
/* 176 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/* 179 */             if (var == 4)
/* 180 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */           case 10003:
/* 182 */             if (var == 4) {
/*     */               
/* 184 */               qs.setQuestVarById(0, var + 1);
/* 185 */               updateQuestStatus(player, qs);
/* 186 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 187 */               return true;
/*     */             } 
/* 189 */             return false;
/*     */         } 
/*     */       
/* 192 */       } else if (targetId == 203968) {
/*     */         
/* 194 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/* 197 */             if (var == 5)
/* 198 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/*     */           case 10004:
/* 200 */             if (var == 5) {
/*     */               
/* 202 */               qs.setQuestVarById(0, var + 1);
/* 203 */               updateQuestStatus(player, qs);
/* 204 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 205 */               return true;
/*     */             } 
/* 207 */             return false;
/*     */         } 
/*     */       
/* 210 */       } else if (targetId == 203987) {
/*     */         
/* 212 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/* 215 */             if (var == 6)
/* 216 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/* 217 */             if (var == 8)
/* 218 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
/*     */           case 10005:
/* 220 */             if (var == 6) {
/*     */               
/* 222 */               qs.setQuestVarById(0, var + 1);
/* 223 */               updateQuestStatus(player, qs);
/* 224 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 225 */               ItemService.addItems(player, Collections.singletonList(new QuestItems(182201024, 1)));
/* 226 */               return true;
/*     */             } 
/*     */           case 10006:
/* 229 */             if (var == 8) {
/*     */               
/* 231 */               qs.setQuestVarById(0, var + 1);
/* 232 */               updateQuestStatus(player, qs);
/* 233 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 234 */               ItemService.addItems(player, Collections.singletonList(new QuestItems(182201025, 1)));
/* 235 */               return true;
/*     */             } 
/* 237 */             return false;
/*     */         } 
/*     */       } else {
/* 240 */         if (targetId == 700160 && var == 7) {
/*     */           
/* 242 */           if (env.getDialogId().intValue() == -1 && player.getInventory().getItemCountByItemId(182201024) == 1L) {
/*     */             
/* 244 */             final int targetObjectId = env.getVisibleObject().getObjectId();
/* 245 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/* 246 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 247 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 31));
/* 248 */             ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 252 */                     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/* 253 */                     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/* 254 */                     ItemService.decreaseItemCountByItemId(player, 182201024, 1L);
/* 255 */                     qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 256 */                     _1035RefreshingtheSprings.this.updateQuestStatus(player, qs);
/*     */                   }
/*     */                 }3000L);
/*     */           } 
/* 260 */           return false;
/*     */         } 
/* 262 */         if (targetId == 203934)
/*     */         
/* 264 */         { switch (env.getDialogId().intValue()) {
/*     */             
/*     */             case 25:
/* 267 */               if (var == 9)
/* 268 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398); 
/* 269 */               if (var == 11)
/* 270 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3739); 
/*     */             case 10007:
/* 272 */               if (var == 9) {
/*     */                 
/* 274 */                 qs.setQuestVarById(0, var + 1);
/* 275 */                 updateQuestStatus(player, qs);
/* 276 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */               }
/* 278 */               else if (var == 11) {
/*     */                 
/* 280 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 281 */                 qs.setStatus(QuestStatus.REWARD);
/* 282 */                 updateQuestStatus(player, qs);
/* 283 */                 return true;
/*     */               } 
/* 285 */               return false;
/*     */           } 
/*     */            }
/* 288 */         else if (targetId == 700159 && var == 10)
/*     */         
/* 290 */         { if (env.getDialogId().intValue() == -1 && player.getInventory().getItemCountByItemId(182201025) == 1L) {
/*     */             
/* 292 */             final int targetObjectId = env.getVisibleObject().getObjectId();
/* 293 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/* 294 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/* 295 */             ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 299 */                     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/* 300 */                     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/* 301 */                     ItemService.decreaseItemCountByItemId(player, 182201025, 1L);
/* 302 */                     qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
/* 303 */                     _1035RefreshingtheSprings.this.updateQuestStatus(player, qs);
/*     */                   }
/*     */                 }3000L);
/*     */           } 
/* 307 */           return false; } 
/*     */       } 
/* 309 */     }  return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\eltnen\_1035RefreshingtheSprings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */