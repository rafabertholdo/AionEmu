/*     */ package quest.ascension;
/*     */ 
/*     */ import com.aionemu.gameserver.configs.main.CustomConfig;
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.PlayerClass;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.SystemMessageId;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ASCENSION_MORPH;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
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
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.world.WorldMapInstance;
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
/*     */ public class _2008Ascension
/*     */   extends QuestHandler
/*     */ {
/*     */   private static final int questId = 2008;
/*     */   
/*     */   public _2008Ascension() {
/*  59 */     super(Integer.valueOf(2008));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void register() {
/*  65 */     if (CustomConfig.ENABLE_SIMPLE_2NDCLASS)
/*     */       return; 
/*  67 */     this.qe.addQuestLvlUp(2008);
/*  68 */     this.qe.setNpcQuestData(203550).addOnTalkEvent(2008);
/*  69 */     this.qe.setNpcQuestData(790003).addOnTalkEvent(2008);
/*  70 */     this.qe.setNpcQuestData(790002).addOnTalkEvent(2008);
/*  71 */     this.qe.setNpcQuestData(203546).addOnTalkEvent(2008);
/*  72 */     this.qe.setNpcQuestData(205020).addOnTalkEvent(2008);
/*  73 */     this.qe.setNpcQuestData(205040).addOnKillEvent(2008);
/*  74 */     this.qe.setNpcQuestData(205041).addOnAttackEvent(2008);
/*  75 */     this.qe.setQuestMovieEndIds(152).add(2008);
/*  76 */     this.qe.addOnEnterWorld(2008);
/*  77 */     this.qe.addOnDie(2008);
/*  78 */     this.qe.addOnQuestFinish(2008);
/*  79 */     this.deletebleItems = new int[] { 182203009, 182203010, 182203011 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onKillEvent(QuestEnv env) {
/*  85 */     Player player = env.getPlayer();
/*  86 */     int instanceId = player.getInstanceId();
/*  87 */     QuestState qs = player.getQuestStateList().getQuestState(2008);
/*  88 */     if (qs == null || qs.getStatus() != QuestStatus.START) {
/*  89 */       return false;
/*     */     }
/*  91 */     int var = qs.getQuestVarById(0);
/*  92 */     int targetId = 0;
/*  93 */     if (env.getVisibleObject() instanceof Npc) {
/*  94 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/*  96 */     if (targetId == 205040) {
/*     */       
/*  98 */       if (var >= 51 && var <= 53) {
/*     */         
/* 100 */         qs.setQuestVar(qs.getQuestVars().getQuestVars() + 1);
/* 101 */         updateQuestStatus(player, qs);
/* 102 */         return true;
/*     */       } 
/* 104 */       if (var == 54) {
/*     */         
/* 106 */         qs.setQuestVar(5);
/* 107 */         updateQuestStatus(player, qs);
/* 108 */         Npc mob = (Npc)QuestService.addNewSpawn(320010000, instanceId, 205041, 301.0F, 259.0F, 205.5F, (byte)0, true);
/*     */         
/* 110 */         mob.getGameStats().setStat(StatEnum.MAIN_HAND_POWER, mob.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_POWER) / 3);
/* 111 */         mob.getAggroList().addDamage((Creature)player, 1000);
/* 112 */         return true;
/*     */       } 
/*     */     } 
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onAttackEvent(QuestEnv env) {
/* 121 */     Player player = env.getPlayer();
/* 122 */     QuestState qs = player.getQuestStateList().getQuestState(2008);
/* 123 */     if (qs == null || qs.getStatus() != QuestStatus.START || qs.getQuestVars().getQuestVars() != 5)
/* 124 */       return false; 
/* 125 */     int targetId = 0;
/* 126 */     if (env.getVisibleObject() instanceof Npc)
/* 127 */       targetId = ((Npc)env.getVisibleObject()).getNpcId(); 
/* 128 */     if (targetId != 205041)
/* 129 */       return false; 
/* 130 */     Npc npc = (Npc)env.getVisibleObject();
/* 131 */     if (npc.getLifeStats().getCurrentHp() < npc.getLifeStats().getMaxHp() / 2) {
/*     */       
/* 133 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 152));
/* 134 */       npc.getController().onDelete();
/*     */     } 
/* 136 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDialogEvent(QuestEnv env) {
/* 142 */     final Player player = env.getPlayer();
/* 143 */     final int instanceId = player.getInstanceId();
/* 144 */     final QuestState qs = player.getQuestStateList().getQuestState(2008);
/* 145 */     if (qs == null) {
/* 146 */       return false;
/*     */     }
/* 148 */     int var = qs.getQuestVars().getQuestVars();
/* 149 */     int targetId = 0;
/* 150 */     if (env.getVisibleObject() instanceof Npc) {
/* 151 */       targetId = ((Npc)env.getVisibleObject()).getNpcId();
/*     */     }
/* 153 */     if (qs.getStatus() == QuestStatus.START) {
/*     */       
/* 155 */       if (targetId == 203550) {
/*     */         
/* 157 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/* 160 */             if (var == 0)
/* 161 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011); 
/* 162 */             if (var == 4)
/* 163 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375); 
/* 164 */             if (var == 6)
/* 165 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716); 
/*     */           case 2376:
/* 167 */             if (var == 4) {
/*     */               
/* 169 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 57));
/* 170 */               ItemService.removeItemFromInventoryByItemId(player, 182203009);
/* 171 */               ItemService.removeItemFromInventoryByItemId(player, 182203010);
/* 172 */               ItemService.removeItemFromInventoryByItemId(player, 182203011);
/* 173 */               return false;
/*     */             } 
/*     */           case 10000:
/* 176 */             if (var == 0) {
/*     */               
/* 178 */               qs.setQuestVarById(0, var + 1);
/* 179 */               updateQuestStatus(player, qs);
/* 180 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 181 */               return true;
/*     */             } 
/*     */           case 10004:
/* 184 */             if (var == 4) {
/*     */               
/* 186 */               qs.setQuestVar(99);
/* 187 */               updateQuestStatus(player, qs);
/* 188 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 0));
/*     */               
/* 190 */               WorldMapInstance newInstance = InstanceService.getNextAvailableInstance(320010000);
/* 191 */               InstanceService.registerPlayerWithInstance(newInstance, player);
/* 192 */               TeleportService.teleportTo(player, 320010000, newInstance.getInstanceId(), 457.65F, 426.8F, 230.4F, 0);
/* 193 */               return true;
/*     */             } 
/*     */           case 10005:
/* 196 */             if (var == 6) {
/*     */               
/* 198 */               PlayerClass playerClass = player.getCommonData().getPlayerClass();
/* 199 */               if (playerClass == PlayerClass.WARRIOR)
/* 200 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057); 
/* 201 */               if (playerClass == PlayerClass.SCOUT)
/* 202 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398); 
/* 203 */               if (playerClass == PlayerClass.MAGE)
/* 204 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3739); 
/* 205 */               if (playerClass == PlayerClass.PRIEST)
/* 206 */                 return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4080); 
/*     */             } 
/*     */           case 10006:
/* 209 */             if (var == 6)
/* 210 */               return setPlayerClass(env, qs, PlayerClass.GLADIATOR); 
/*     */           case 10007:
/* 212 */             if (var == 6)
/* 213 */               return setPlayerClass(env, qs, PlayerClass.TEMPLAR); 
/*     */           case 10008:
/* 215 */             if (var == 6)
/* 216 */               return setPlayerClass(env, qs, PlayerClass.ASSASSIN); 
/*     */           case 10009:
/* 218 */             if (var == 6)
/* 219 */               return setPlayerClass(env, qs, PlayerClass.RANGER); 
/*     */           case 10010:
/* 221 */             if (var == 6)
/* 222 */               return setPlayerClass(env, qs, PlayerClass.SORCERER); 
/*     */           case 10011:
/* 224 */             if (var == 6)
/* 225 */               return setPlayerClass(env, qs, PlayerClass.SPIRIT_MASTER); 
/*     */           case 10012:
/* 227 */             if (var == 6)
/* 228 */               return setPlayerClass(env, qs, PlayerClass.CHANTER); 
/*     */           case 10013:
/* 230 */             if (var == 6)
/* 231 */               return setPlayerClass(env, qs, PlayerClass.CLERIC); 
/*     */             break;
/*     */         } 
/* 234 */       } else if (targetId == 790003) {
/*     */         
/* 236 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/* 239 */             if (var == 1)
/* 240 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352); 
/*     */           case 10001:
/* 242 */             if (var == 1) {
/*     */               
/* 244 */               if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182203009, 1)))) {
/* 245 */                 return true;
/*     */               }
/* 247 */               qs.setQuestVarById(0, 2);
/* 248 */               updateQuestStatus(player, qs);
/* 249 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 250 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/* 254 */       } else if (targetId == 790002) {
/*     */         
/* 256 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/* 259 */             if (var == 2)
/* 260 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693); 
/*     */           case 10002:
/* 262 */             if (var == 2) {
/*     */               
/* 264 */               if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182203010, 1))))
/* 265 */                 return true; 
/* 266 */               qs.setQuestVarById(0, 3);
/* 267 */               updateQuestStatus(player, qs);
/* 268 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 269 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/* 273 */       } else if (targetId == 203546) {
/*     */         
/* 275 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/* 278 */             if (var == 3)
/* 279 */               return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034); 
/*     */           case 10003:
/* 281 */             if (var == 3) {
/*     */               
/* 283 */               if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(182203011, 1)))) {
/* 284 */                 return true;
/*     */               }
/* 286 */               qs.setQuestVarById(0, 4);
/* 287 */               updateQuestStatus(player, qs);
/* 288 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
/* 289 */               return true;
/*     */             } 
/*     */             break;
/*     */         } 
/* 293 */       } else if (targetId == 205020) {
/*     */         
/* 295 */         switch (env.getDialogId().intValue()) {
/*     */           
/*     */           case 25:
/* 298 */             if (var == 99) {
/*     */               
/* 300 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_FLYTELEPORT, 3001, 0));
/* 301 */               qs.setQuestVar(50);
/* 302 */               updateQuestStatus(player, qs);
/* 303 */               ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */                   {
/*     */                     public void run()
/*     */                     {
/* 307 */                       qs.setQuestVar(51);
/* 308 */                       _2008Ascension.this.updateQuestStatus(player, qs);
/* 309 */                       List<Npc> mobs = new ArrayList<Npc>();
/* 310 */                       mobs.add((Npc)QuestService.addNewSpawn(320010000, instanceId, 205040, 294.0F, 277.0F, 207.0F, (byte)0, true));
/* 311 */                       mobs.add((Npc)QuestService.addNewSpawn(320010000, instanceId, 205040, 305.0F, 279.0F, 206.5F, (byte)0, true));
/* 312 */                       mobs.add((Npc)QuestService.addNewSpawn(320010000, instanceId, 205040, 298.0F, 253.0F, 205.7F, (byte)0, true));
/* 313 */                       mobs.add((Npc)QuestService.addNewSpawn(320010000, instanceId, 205040, 306.0F, 251.0F, 206.0F, (byte)0, true));
/* 314 */                       for (Npc mob : mobs) {
/*     */ 
/*     */                         
/* 317 */                         mob.getGameStats().setStat(StatEnum.MAIN_HAND_POWER, mob.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_POWER) / 3);
/* 318 */                         mob.getGameStats().setStat(StatEnum.PHYSICAL_DEFENSE, 0);
/* 319 */                         mob.getAggroList().addDamage((Creature)player, 1000);
/*     */                       } 
/*     */                     }
/*     */                   }43000L);
/* 323 */               return true;
/*     */             } 
/* 325 */             return false;
/*     */         } 
/* 327 */         return false;
/*     */       }
/*     */     
/*     */     }
/* 331 */     else if (qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 333 */       if (targetId == 203550)
/*     */       {
/* 335 */         return defaultQuestEndDialog(env);
/*     */       }
/*     */     } 
/* 338 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLvlUpEvent(QuestEnv env) {
/* 344 */     Player player = env.getPlayer();
/* 345 */     QuestState qs = player.getQuestStateList().getQuestState(2008);
/* 346 */     if (qs == null) {
/*     */       
/* 348 */       boolean lvlCheck = QuestService.checkLevelRequirement(2008, player.getCommonData().getLevel());
/* 349 */       if (!lvlCheck)
/* 350 */         return false; 
/* 351 */       env.setQuestId(Integer.valueOf(2008));
/* 352 */       QuestService.startQuest(env, QuestStatus.START);
/* 353 */       return true;
/*     */     } 
/* 355 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onEnterWorldEvent(QuestEnv env) {
/* 361 */     Player player = env.getPlayer();
/* 362 */     QuestState qs = player.getQuestStateList().getQuestState(2008);
/* 363 */     if (qs != null && qs.getStatus() == QuestStatus.START) {
/*     */       
/* 365 */       int var = qs.getQuestVars().getQuestVars();
/* 366 */       if (var == 5 || (var >= 50 && var <= 55) || var == 99)
/*     */       {
/* 368 */         if (player.getWorldId() != 320010000) {
/*     */           
/* 370 */           qs.setQuestVar(4);
/* 371 */           updateQuestStatus(player, qs);
/* 372 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SYSTEM_MESSAGE(SystemMessageId.QUEST_FAILED_$1, new Object[] { DataManager.QUEST_DATA.getQuestById(2008).getName() }));
/*     */         }
/*     */         else {
/*     */           
/* 376 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ASCENSION_MORPH(1));
/* 377 */           return true;
/*     */         } 
/*     */       }
/*     */     } 
/* 381 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMovieEndEvent(QuestEnv env, int movieId) {
/* 387 */     if (movieId != 152)
/* 388 */       return false; 
/* 389 */     Player player = env.getPlayer();
/* 390 */     QuestState qs = player.getQuestStateList().getQuestState(2008);
/* 391 */     if (qs == null || qs.getStatus() != QuestStatus.START || qs.getQuestVars().getQuestVars() != 5)
/* 392 */       return false; 
/* 393 */     int instanceId = player.getInstanceId();
/* 394 */     QuestService.addNewSpawn(320010000, instanceId, 203550, 301.93F, 274.26F, 205.7F, (byte)0, true);
/* 395 */     qs.setQuestVar(6);
/* 396 */     updateQuestStatus(player, qs);
/* 397 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean setPlayerClass(QuestEnv env, QuestState qs, PlayerClass playerClass) {
/* 402 */     Player player = env.getPlayer();
/* 403 */     player.getCommonData().setPlayerClass(playerClass);
/* 404 */     player.getCommonData().upgradePlayer();
/* 405 */     qs.setStatus(QuestStatus.REWARD);
/* 406 */     updateQuestStatus(player, qs);
/* 407 */     sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
/* 408 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDieEvent(QuestEnv env) {
/* 414 */     Player player = env.getPlayer();
/* 415 */     QuestState qs = player.getQuestStateList().getQuestState(2008);
/* 416 */     if (qs == null || qs.getStatus() != QuestStatus.START)
/* 417 */       return false; 
/* 418 */     if (qs.getStatus() != QuestStatus.START)
/* 419 */       return false; 
/* 420 */     int var = qs.getQuestVars().getQuestVars();
/* 421 */     if (var == 5 || (var >= 51 && var <= 53)) {
/*     */       
/* 423 */       qs.setQuestVar(4);
/* 424 */       updateQuestStatus(player, qs);
/* 425 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SYSTEM_MESSAGE(SystemMessageId.QUEST_FAILED_$1, new Object[] { DataManager.QUEST_DATA.getQuestById(env.getQuestId().intValue()).getName() }));
/*     */     } 
/* 427 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onQuestFinishEvent(QuestEnv env) {
/* 433 */     Player player = env.getPlayer();
/* 434 */     QuestState qs = player.getQuestStateList().getQuestState(2008);
/* 435 */     if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
/*     */       
/* 437 */       TeleportService.teleportTo(player, 220010000, 1, 385.0F, 1895.0F, 327.0F, (byte)20, 0);
/* 438 */       return true;
/*     */     } 
/* 440 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\quest\ascension\_2008Ascension.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */