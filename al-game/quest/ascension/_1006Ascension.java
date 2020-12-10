/*     */ package quest.ascension;
/*     */ 
/*     */ import com.aionemu.gameserver.configs.main.CustomConfig;
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.PlayerClass;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.SystemMessageId;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ASCENSION_MORPH;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestEnv;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestState;
/*     */ import com.aionemu.gameserver.questEngine.model.QuestStatus;
/*     */ import com.aionemu.gameserver.services.InstanceService;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.QuestService;
/*     */ import com.aionemu.gameserver.services.TeleportService;
/*     */ import com.aionemu.gameserver.services.ZoneService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.world.WorldMapInstance;
/*     */ import com.aionemu.gameserver.world.zone.ZoneName;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
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
/*     */ public class _1006Ascension
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 1006;
/*     */   
/*     */   public _1006Ascension() {
/*  63 */     super(Integer.valueOf(1006));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  69 */     if (CustomConfig.ENABLE_SIMPLE_2NDCLASS)
/*     */       return; 
/*  71 */     this.qe.addQuestLvlUp(1006);
/*  72 */     this.qe.setNpcQuestData(790001).addOnTalkEvent(1006);
/*  73 */     this.qe.setQuestItemIds(182200007).add(1006);
/*  74 */     this.qe.setNpcQuestData(730008).addOnTalkEvent(1006);
/*  75 */     this.qe.setNpcQuestData(205000).addOnTalkEvent(1006);
/*  76 */     this.qe.setNpcQuestData(211042).addOnKillEvent(1006);
/*  77 */     this.qe.setNpcQuestData(211043).addOnAttackEvent(1006);
/*  78 */     this.qe.setQuestMovieEndIds(151).add(1006);
/*  79 */     this.qe.addOnEnterWorld(1006);
/*  80 */     this.qe.addOnDie(1006);
/*  81 */     this.qe.addOnQuestFinish(1006);
/*  82 */     this.deletebleItems = new int[] { 182200008, 182200009 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/*  88 */     Player player = env.getPlayer();
/*  89 */     int instanceId = player.getInstanceId();
/*  90 */     QuestState qs = player.getQuestStateList().getQuestState(1006);
/*  91 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/*  92 */       return false;
/*     */     }
/*  94 */     int var = qs.getQuestVarById(0);
/*  95 */     int targetId = 0;
/*  96 */     if (env.getVisibleObject() instanceof Npc) {
/*  97 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  99 */     if (targetId == 211042) {
/*     */       
/* 101 */       if (var >= 51 && var <= 53) {
/*     */         
/* 103 */         qs.setQuestVar(qs.getQuestVars().getQuestVars() + 1);
/* 104 */         updateQuestStatus(player, qs);
/* 105 */         return true;
/*     */       } 
/* 107 */       if (var == 54) {
/*     */         
/* 109 */         qs.setQuestVar(4);
/* 110 */         updateQuestStatus(player, qs);
/* 111 */         Npc mob = (Npc)QuestService.addNewSpawn(310010000, instanceId, 211043, 226.7F, 251.5F, 205.5F, (byte)0, true);
/*     */         
/* 113 */         mob.getGameStats().setStat(StatEnum.MAIN_HAND_POWER, mob.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_POWER) / 3);
/* 114 */         mob.getAggroList().addDamage((Creature)player, 1000);
/* 115 */         return true;
/*     */       } 
/*     */     } 
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/* 124 */     final Player player = env.getPlayer();
/* 125 */     final int instanceId = player.getInstanceId();
/* 126 */     final QuestState qs = player.getQuestStateList().getQuestState(1006);
/* 127 */     if (qs == null) {
/* 128 */       return false;
/*     */     }
/* 130 */     int var = qs.getQuestVars().getQuestVars();
/* 131 */     int targetId = 0;
/* 132 */     if (env.getVisibleObject() instanceof Npc) {
/* 133 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 135 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/* 137 */       if (targetId == 790001) {
/*     */         
/* 139 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/* 142 */             if (var == 0)
/* 143 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 144 */             if (var == 3)
/* 145 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/* 146 */             if (var == 5)
/* 147 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */           case 10000:
/* 149 */             if (var == 0) {
/*     */               
/* 151 */               if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182200007, 1))))
/* 152 */                 return true; 
/* 153 */               qs.setQuestVarById(0, var + 1);
/* 154 */               updateQuestStatus(player, qs);
/* 155 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 156 */               return true;
/*     */             } 
/*     */           case 10002:
/* 159 */             if (var == 3) {
/*     */               
/* 161 */               ItemService.removeItemFromInventoryByItemId(player, 182200009);
/* 162 */               qs.setQuestVar(99);
/* 163 */               updateQuestStatus(player, qs);
/* 164 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/* 165 */               WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(310010000);
/* 166 */               InstanceService.registerPlayerWithInstance(newInstance, player);
/* 167 */               TeleportService.teleportTo(player, 310010000, newInstance.getInstanceId(), 52.0F, 174.0F, 229.0F, 0);
/* 168 */               return true;
/*     */             } 
/*     */           case 10003:
/* 171 */             if (var == 5) {
/*     */               
/* 173 */               PlayerClass playerClass = player.getCommonData().getPlayerClass();
/* 174 */               if (playerClass == PlayerClass.WARRIOR)
/* 175 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 176 */               if (playerClass == PlayerClass.SCOUT)
/* 177 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/* 178 */               if (playerClass == PlayerClass.MAGE)
/* 179 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
/* 180 */               if (playerClass == PlayerClass.PRIEST)
/* 181 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398); 
/*     */             } 
/*     */           case 10004:
/* 184 */             if (var == 5)
/* 185 */               return setPlayerClass(env, qs, PlayerClass.GLADIATOR); 
/*     */           case 10005:
/* 187 */             if (var == 5)
/* 188 */               return setPlayerClass(env, qs, PlayerClass.TEMPLAR); 
/*     */           case 10006:
/* 190 */             if (var == 5)
/* 191 */               return setPlayerClass(env, qs, PlayerClass.ASSASSIN); 
/*     */           case 10007:
/* 193 */             if (var == 5)
/* 194 */               return setPlayerClass(env, qs, PlayerClass.RANGER); 
/*     */           case 10008:
/* 196 */             if (var == 5)
/* 197 */               return setPlayerClass(env, qs, PlayerClass.SORCERER); 
/*     */           case 10009:
/* 199 */             if (var == 5)
/* 200 */               return setPlayerClass(env, qs, PlayerClass.SPIRIT_MASTER); 
/*     */           case 10010:
/* 202 */             if (var == 5)
/* 203 */               return setPlayerClass(env, qs, PlayerClass.CLERIC); 
/*     */           case 10011:
/* 205 */             if (var == 5)
/* 206 */               return setPlayerClass(env, qs, PlayerClass.CHANTER); 
/*     */             break;
/*     */         } 
/* 209 */       } else if (targetId == 730008) {
/*     */         
/* 211 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/* 214 */             if (var == 2) {
/*     */               
/* 216 */               if (player.getInventory().getItemCountByItemId(182200008) != 0L) {
/* 217 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
/*     */               }
/* 219 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1354);
/*     */             } 
/*     */           case 1353:
/* 222 */             if (var == 2) {
/*     */               
/* 224 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 14));
/* 225 */               ItemService.decreaseItemCountByItemId(player, 182200008, 1L);
/* 226 */               ItemService.addItems(player, Collections.singletonList(new QuestItems(182200009, 1)));
/*     */             } 
/* 228 */             return false;
/*     */           case 10001:
/* 230 */             if (var == 2) {
/*     */               
/* 232 */               qs.setQuestVarById(0, var + 1);
/* 233 */               updateQuestStatus(player, qs);
/* 234 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 235 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/* 239 */       } else if (targetId == 205000) {
/*     */         
/* 241 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/* 244 */             if (var == 99) {
/*     */               
/* 246 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_FLYTELEPORT, 1001, 0));
/* 247 */               qs.setQuestVar(50);
/* 248 */               updateQuestStatus(player, qs);
/* 249 */               ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                   {
/*     */                     public void run()
/*     */                     {
/* 253 */                       qs.setQuestVar(51);
/* 254 */                       _1006Ascension.this.updateQuestStatus(player, qs);
/* 255 */                       List<Npc> mobs = new ArrayList<Npc>();
/* 256 */                       mobs.add((Npc)QuestService.addNewSpawn(310010000, instanceId, 211042, 224.073F, 239.1F, 206.7F, (byte)0, true));
/* 257 */                       mobs.add((Npc)QuestService.addNewSpawn(310010000, instanceId, 211042, 233.5F, 241.04F, 206.365F, (byte)0, true));
/* 258 */                       mobs.add((Npc)QuestService.addNewSpawn(310010000, instanceId, 211042, 229.6F, 265.7F, 205.7F, (byte)0, true));
/* 259 */                       mobs.add((Npc)QuestService.addNewSpawn(310010000, instanceId, 211042, 222.8F, 262.5F, 205.7F, (byte)0, true));
/* 260 */                       for (Npc mob : mobs) {
/*     */ 
/*     */                         
/* 263 */                         mob.getGameStats().setStat(StatEnum.MAIN_HAND_POWER, mob.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_POWER) / 3);
/* 264 */                         mob.getGameStats().setStat(StatEnum.PHYSICAL_DEFENSE, 0);
/* 265 */                         mob.getAggroList().addDamage((Creature)player, 1000);
/*     */                       } 
/*     */                     }
/*     */                   }43000L);
/* 269 */               return true;
/*     */             } 
/* 271 */             return false;
/*     */         } 
/* 273 */         return false;
/*     */       }
/*     */     
/*     */     }
/* 277 */     else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 279 */       if (targetId == 790001)
/*     */       {
/* 281 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     } 
/* 284 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 290 */     Player player = env.getPlayer();
/* 291 */     QuestState qs = player.getQuestStateList().getQuestState(1006);
/* 292 */     if (qs != null)
/* 293 */       return false; 
/* 294 */     boolean lvlCheck = QuestService.checkLevelRequirement(1006, player.getCommonData().getLevel());
/* 295 */     if (!lvlCheck)
/* 296 */       return false; 
/* 297 */     env.setQuestId(Integer.valueOf(1006));
/* 298 */     QuestService.startQuest(env, QuestStatus.START);
/* 299 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onAttackEvent(QuestEnv env) {
/* 305 */     Player player = env.getPlayer();
/* 306 */     QuestState qs = player.getQuestStateList().getQuestState(1006);
/* 307 */     if (qs == null || qs.getStatus() != QuestStatus.START || qs.getQuestVars().getQuestVars() != 4)
/* 308 */       return false; 
/* 309 */     int targetId = 0;
/* 310 */     if (env.getVisibleObject() instanceof Npc)
/* 311 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 312 */     if (targetId != 211043)
/* 313 */       return false; 
/* 314 */     Npc npc = (Npc)env.getVisibleObject();
/* 315 */     if (npc.getLifeStats().getCurrentHp() < npc.getLifeStats().getMaxHp() / 2) {
/*     */       
/* 317 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 151));
/* 318 */       npc.getController().onDelete();
/*     */     } 
/* 320 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemUseEvent(QuestEnv env, final Item item) {
/* 326 */     final Player player = env.getPlayer();
/* 327 */     final int id = item.getItemTemplate().getTemplateId();
/* 328 */     final int itemObjId = item.getObjectId();
/*     */     
/* 330 */     if (id != 182200007)
/* 331 */       return false; 
/* 332 */     if (!ZoneService.getInstance().isInsideZone(player, ZoneName.ITEMUSE_Q1006))
/* 333 */       return false; 
/* 334 */     final QuestState qs = player.getQuestStateList().getQuestState(1006);
/* 335 */     if (qs == null)
/* 336 */       return false; 
/* 337 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 3000, 0, 0), true);
/* 338 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 342 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), itemObjId, id, 0, 1, 0), true);
/* 343 */             ItemService.removeItemFromInventory(player, item);
/* 344 */             ItemService.addItems(player, Collections.singletonList(new QuestItems(182200008, 1)));
/* 345 */             qs.setQuestVarById(0, 2);
/* 346 */             _1006Ascension.this.updateQuestStatus(player, qs);
/*     */           }
/*     */         }3000L);
/* 349 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMovieEndEvent(QuestEnv env, int movieId) {
/* 355 */     if (movieId != 151)
/* 356 */       return false; 
/* 357 */     Player player = env.getPlayer();
/* 358 */     int instanceId = player.getInstanceId();
/* 359 */     QuestState qs = player.getQuestStateList().getQuestState(1006);
/* 360 */     if (qs == null || qs.getStatus() != QuestStatus.START || qs.getQuestVars().getQuestVars() != 4)
/* 361 */       return false; 
/* 362 */     QuestService.addNewSpawn(310010000, instanceId, 790001, 220.6F, 247.8F, 206.0F, (byte)0, true);
/* 363 */     qs.setQuestVar(5);
/* 364 */     updateQuestStatus(player, qs);
/* 365 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean setPlayerClass(QuestEnv env, QuestState qs, PlayerClass playerClass) {
/* 370 */     Player player = env.getPlayer();
/* 371 */     player.getCommonData().setPlayerClass(playerClass);
/* 372 */     player.getCommonData().upgradePlayer();
/* 373 */     qs.setStatus(QuestStatus.REWARD);
/* 374 */     updateQuestStatus(player, qs);
/* 375 */     sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/* 376 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDieEvent(QuestEnv env) {
/* 382 */     Player player = env.getPlayer();
/* 383 */     QuestState qs = player.getQuestStateList().getQuestState(1006);
/* 384 */     if (qs == null || qs.getStatus() != QuestStatus.START)
/* 385 */       return false; 
/* 386 */     int var = qs.getQuestVars().getQuestVars();
/* 387 */     if (var == 4 || (var >= 50 && var <= 55)) {
/*     */       
/* 389 */       qs.setQuestVar(3);
/* 390 */       updateQuestStatus(player, qs);
/* 391 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SYSTEM_MESSAGE(SystemMessageId.QUEST_FAILED_$1, new Object[] { DataManager.QUEST_DATA.getQuestById(1006).getName() }));
/*     */     } 
/*     */     
/* 394 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onEnterWorldEvent(QuestEnv env) {
/* 400 */     Player player = env.getPlayer();
/* 401 */     QuestState qs = player.getQuestStateList().getQuestState(1006);
/* 402 */     if (qs != null && qs.getStatus() == QuestStatus.START) {
/*     */       
/* 404 */       int var = qs.getQuestVars().getQuestVars();
/* 405 */       if (var == 4 || (var >= 50 && var <= 55) || var == 99)
/*     */       {
/* 407 */         if (player.getWorldId() != 310010000) {
/*     */           
/* 409 */           qs.setQuestVar(3);
/* 410 */           updateQuestStatus(player, qs);
/* 411 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SYSTEM_MESSAGE(SystemMessageId.QUEST_FAILED_$1, new Object[] { DataManager.QUEST_DATA.getQuestById(1006).getName() }));
/*     */         }
/*     */         else {
/*     */           
/* 415 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ASCENSION_MORPH(1));
/* 416 */           return true;
/*     */         } 
/*     */       }
/*     */     } 
/* 420 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onQuestFinishEvent(QuestEnv env) {
/* 426 */     Player player = env.getPlayer();
/* 427 */     QuestState qs = player.getQuestStateList().getQuestState(1006);
/* 428 */     if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 430 */       TeleportService.teleportTo(player, 210010000, 1, 242.0F, 1638.0F, 100.0F, (byte)20, 0);
/* 431 */       return true;
/*     */     } 
/* 433 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ascension\_1006Ascension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */