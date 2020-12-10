/*     */ package quest.beluslan;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.QuestService;
/*     */ import com.aionemu.gameserver.services.ZoneService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.world.zone.ZoneName;
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
/*     */ public class _2060RestoringBeluslanObservatory
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2060;
/*  50 */   private static final int[] npc_ids = new int[] { 204701, 204785, 278003, 278088, 700293 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public _2060RestoringBeluslanObservatory() {
/*  58 */     super(Integer.valueOf(2060));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  64 */     this.qe.addQuestLvlUp(2060);
/*  65 */     this.qe.setQuestItemIds(182204318).add(2060);
/*  66 */     this.qe.setNpcQuestData(700290).addOnKillEvent(2060);
/*  67 */     for (int npc_id : npc_ids) {
/*  68 */       this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2060);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/*  74 */     Player player = env.getPlayer();
/*  75 */     QuestState qs = player.getQuestStateList().getQuestState(2060);
/*  76 */     boolean lvlCheck = QuestService.checkLevelRequirement(2060, player.getCommonData().getLevel());
/*     */     
/*  78 */     if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED) {
/*  79 */       return false;
/*     */     }
/*  81 */     QuestState qs2 = player.getQuestStateList().getQuestState(2500);
/*  82 */     if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE) {
/*  83 */       return false;
/*     */     }
/*  85 */     qs.setStatus(QuestStatus.START);
/*  86 */     updateQuestStatus(player, qs);
/*  87 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/*  93 */     final Player player = env.getPlayer();
/*  94 */     final QuestState qs = player.getQuestStateList().getQuestState(2060);
/*  95 */     if (qs == null) {
/*  96 */       return false;
/*     */     }
/*  98 */     int var = qs.getQuestVarById(0);
/*  99 */     int targetId = 0;
/* 100 */     if (env.getVisibleObject() instanceof Npc) {
/* 101 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 103 */     if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 105 */       if (targetId == 204701) {
/*     */         
/* 107 */         if (env.getDialogId().intValue() == -1)
/* 108 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002); 
/* 109 */         if (env.getDialogId().intValue() == 1009) {
/* 110 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/*     */         }
/* 112 */         return defaultQuestEndDialog(env);
/*     */       } 
/* 114 */       return false;
/*     */     } 
/* 116 */     if (qs.getStatus() != QuestStatus.START)
/*     */     {
/* 118 */       return false;
/*     */     }
/*     */     
/* 121 */     if (targetId == 204701) {
/*     */       
/* 123 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 126 */           if (var == 0) {
/* 127 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
/*     */           }
/*     */         case 10000:
/* 130 */           if (var == 0) {
/*     */             
/* 132 */             qs.setQuestVarById(0, var + 1);
/* 133 */             updateQuestStatus(player, qs);
/* 134 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */             
/* 136 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */     
/* 141 */     } else if (targetId == 204785) {
/*     */       
/* 143 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 146 */           if (var == 1) {
/* 147 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */           }
/*     */         case 33:
/* 150 */           if (QuestService.collectItemCheck(env, true) && var == 4)
/*     */           {
/* 152 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
/*     */           }
/*     */           
/* 155 */           return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2461);
/*     */         case 10001:
/* 157 */           if (var == 1) {
/*     */             
/* 159 */             qs.setQuestVarById(0, var + 1);
/* 160 */             updateQuestStatus(player, qs);
/* 161 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */             
/* 163 */             return true;
/*     */           } 
/*     */         case 10004:
/* 166 */           if (var == 4) {
/*     */             
/* 168 */             qs.setQuestVarById(0, var + 1);
/* 169 */             updateQuestStatus(player, qs);
/* 170 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */             
/* 172 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 176 */     } else if (targetId == 278003) {
/*     */       
/* 178 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 181 */           if (var == 2)
/* 182 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*     */         case 10002:
/* 184 */           if (var == 2) {
/*     */             
/* 186 */             qs.setQuestVarById(0, var + 1);
/* 187 */             updateQuestStatus(player, qs);
/* 188 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */             
/* 190 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 194 */     } else if (targetId == 278088) {
/*     */       
/* 196 */       switch (env.getDialogId().intValue()) {
/*     */         
/*     */         case 25:
/* 199 */           if (var == 3)
/* 200 */             return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */         case 2035:
/* 202 */           if (var == 3)
/* 203 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 281)); 
/*     */           break;
/*     */         case 10003:
/* 206 */           if (var == 3) {
/*     */             
/* 208 */             qs.setQuestVarById(0, var + 1);
/* 209 */             updateQuestStatus(player, qs);
/* 210 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182204318, 1)));
/* 211 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/*     */             
/* 213 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/* 217 */     } else if (targetId == 700293 && var == 8) {
/*     */       
/* 219 */       if (env.getDialogId().intValue() == -1) {
/*     */         
/* 221 */         final int targetObjectId = env.getVisibleObject().getObjectId();
/* 222 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
/* 223 */         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
/*     */         
/* 225 */         ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 229 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
/*     */                 
/* 231 */                 PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
/*     */                 
/* 233 */                 PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 254));
/* 234 */                 qs.setStatus(QuestStatus.REWARD);
/* 235 */                 _2060RestoringBeluslanObservatory.this.updateQuestStatus(player, qs);
/*     */               }
/*     */             }3000L);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 242 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/* 248 */     Player player = env.getPlayer();
/* 249 */     QuestState qs = player.getQuestStateList().getQuestState(2060);
/* 250 */     int var = qs.getQuestVarById(0);
/* 251 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/* 252 */       return false;
/*     */     }
/* 254 */     int targetId = 0;
/* 255 */     if (env.getVisibleObject() instanceof Npc) {
/* 256 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 258 */     if (targetId == 700290 && qs.getQuestVarById(0) > 4 && qs.getQuestVarById(0) < 8) {
/*     */ 
/*     */       
/* 261 */       qs.setQuestVarById(0, var + 1);
/* 262 */       updateQuestStatus(player, qs);
/*     */     } 
/* 264 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, Item item) {
/* 270 */     final Player player = env.getPlayer();
/* 271 */     final int id = item.getItemTemplate().getTemplateId();
/* 272 */     final int itemObjId = item.getObjectId();
/* 273 */     final QuestState qs = player.getQuestStateList().getQuestState(2060);
/*     */     
/* 275 */     if (qs == null || qs.getQuestVarById(0) != 4 || id != 182204318)
/* 276 */       return false; 
/* 277 */     if (!ZoneService.getInstance().isInsideZone(player, ZoneName.LEIBO_ISLAND_400010000)) {
/* 278 */       return false;
/*     */     }
/* 280 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
/*     */     
/* 282 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 286 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/*     */             
/* 288 */             ItemService.removeItemFromInventoryByItemId(player, 182204318);
/* 289 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182204319, 1)));
/* 290 */             _2060RestoringBeluslanObservatory.this.updateQuestStatus(player, qs);
/*     */           }
/*     */         }3000L);
/* 293 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\beluslan\_2060RestoringBeluslanObservatory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */