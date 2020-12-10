/*     */ package com.aionemu.gameserver.model.gameobjects.player;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.Gender;
/*     */ import com.aionemu.gameserver.model.PlayerClass;
/*     */ import com.aionemu.gameserver.model.Race;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*     */ import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ABYSS_RANK;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ABYSS_RANK_UPDATE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DP_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_EDIT;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_STATS_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_STATUPDATE_DP;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_STATUPDATE_EXP;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.stats.XPLossEnum;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import com.aionemu.gameserver.world.WorldPosition;
/*     */ import java.sql.Timestamp;
/*     */ import org.apache.log4j.Logger;
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
/*     */ 
/*     */ public class PlayerCommonData
/*     */   extends VisibleObjectTemplate
/*     */ {
/*  50 */   private static final Logger log = Logger.getLogger(PlayerCommonData.class);
/*     */   
/*     */   private final int playerObjId;
/*     */   
/*     */   private Race race;
/*     */   private String name;
/*     */   private PlayerClass playerClass;
/*  57 */   private int level = 0;
/*  58 */   private long exp = 0L;
/*  59 */   private long expRecoverable = 0L;
/*     */   private Gender gender;
/*     */   private Timestamp lastOnline;
/*     */   private boolean online;
/*     */   private String note;
/*     */   private WorldPosition position;
/*  65 */   private int cubeSize = 0;
/*  66 */   private int warehouseSize = 0;
/*  67 */   private int advencedStigmaSlotSize = 0;
/*     */   private int bindPoint;
/*  69 */   private int titleId = -1;
/*  70 */   private int dp = 0;
/*     */   
/*     */   private int mailboxLetters;
/*     */ 
/*     */   
/*     */   public PlayerCommonData(int objId) {
/*  76 */     this.playerObjId = objId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPlayerObjId() {
/*  81 */     return this.playerObjId;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getExp() {
/*  86 */     return this.exp;
/*     */   }
/*     */   
/*     */   public int getCubeSize() {
/*  90 */     return this.cubeSize;
/*     */   }
/*     */   
/*     */   public void setCubesize(int cubeSize) {
/*  94 */     this.cubeSize = cubeSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAdvencedStigmaSlotSize() {
/* 101 */     return this.advencedStigmaSlotSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAdvencedStigmaSlotSize(int advencedStigmaSlotSize) {
/* 109 */     this.advencedStigmaSlotSize = advencedStigmaSlotSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getExpShown() {
/* 114 */     return this.exp - DataManager.PLAYER_EXPERIENCE_TABLE.getStartExpForLevel(this.level);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getExpNeed() {
/* 119 */     if (this.level == DataManager.PLAYER_EXPERIENCE_TABLE.getMaxLevel())
/*     */     {
/* 121 */       return 0L;
/*     */     }
/* 123 */     return DataManager.PLAYER_EXPERIENCE_TABLE.getStartExpForLevel(this.level + 1) - DataManager.PLAYER_EXPERIENCE_TABLE.getStartExpForLevel(this.level);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void calculateExpLoss() {
/* 133 */     long expLost = XPLossEnum.getExpLoss(this.level, getExpNeed());
/* 134 */     int unrecoverable = (int)(expLost * 0.33333333D);
/* 135 */     int recoverable = (int)expLost - unrecoverable;
/* 136 */     long allExpLost = recoverable + this.expRecoverable;
/*     */ 
/*     */ 
/*     */     
/* 140 */     if (getExpShown() > unrecoverable) {
/*     */       
/* 142 */       this.exp -= unrecoverable;
/*     */     }
/*     */     else {
/*     */       
/* 146 */       this.exp -= getExpShown();
/*     */     } 
/* 148 */     if (getExpShown() > recoverable) {
/*     */       
/* 150 */       this.expRecoverable = allExpLost;
/* 151 */       this.exp -= recoverable;
/*     */     }
/*     */     else {
/*     */       
/* 155 */       this.expRecoverable += getExpShown();
/* 156 */       this.exp -= getExpShown();
/*     */     } 
/* 158 */     if (getPlayer() != null) {
/* 159 */       PacketSendUtility.sendPacket(getPlayer(), (AionServerPacket)new SM_STATUPDATE_EXP(getExpShown(), getExpRecoverable(), getExpNeed()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRecoverableExp(long expRecoverable) {
/* 165 */     this.expRecoverable = expRecoverable;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetRecoverableExp() {
/* 170 */     long el = this.expRecoverable;
/* 171 */     this.expRecoverable = 0L;
/* 172 */     setExp(this.exp + el);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getExpRecoverable() {
/* 177 */     return this.expRecoverable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addExp(long value) {
/* 186 */     setExp(this.exp + value);
/* 187 */     if (getPlayer() != null)
/*     */     {
/* 189 */       PacketSendUtility.sendPacket(getPlayer(), (AionServerPacket)SM_SYSTEM_MESSAGE.EXP(Long.toString(value)));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExp(long exp) {
/* 200 */     int maxLevel = DataManager.PLAYER_EXPERIENCE_TABLE.getMaxLevel();
/*     */     
/* 202 */     if (getPlayerClass() != null && getPlayerClass().isStartingClass()) {
/* 203 */       maxLevel = 10;
/*     */     }
/* 205 */     long maxExp = DataManager.PLAYER_EXPERIENCE_TABLE.getStartExpForLevel(maxLevel);
/* 206 */     int level = 1;
/*     */     
/* 208 */     if (exp > maxExp)
/*     */     {
/* 210 */       exp = maxExp;
/*     */     }
/*     */ 
/*     */     
/* 214 */     while (level + 1 != maxLevel && exp >= DataManager.PLAYER_EXPERIENCE_TABLE.getStartExpForLevel(level + 1))
/*     */     {
/* 216 */       level++;
/*     */     }
/*     */     
/* 219 */     if (level != this.level) {
/*     */       
/* 221 */       this.level = level;
/* 222 */       this.exp = exp;
/*     */       
/* 224 */       if (getPlayer() != null)
/*     */       {
/* 226 */         upgradePlayer();
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 231 */       this.exp = exp;
/*     */       
/* 233 */       if (getPlayer() != null)
/*     */       {
/* 235 */         PacketSendUtility.sendPacket(getPlayer(), (AionServerPacket)new SM_STATUPDATE_EXP(getExpShown(), getExpRecoverable(), getExpNeed()));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void upgradePlayer() {
/* 246 */     Player player = getPlayer();
/* 247 */     if (player != null)
/*     */     {
/* 249 */       player.getController().upgradePlayer(this.level);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAp(int value) {
/* 255 */     Player player = getPlayer();
/*     */     
/* 257 */     if (player == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 262 */     PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.EARNED_ABYSS_POINT(String.valueOf(value)));
/*     */ 
/*     */     
/* 265 */     setAp(value);
/*     */ 
/*     */     
/* 268 */     if (player.isLegionMember() && value > 0) {
/*     */       
/* 270 */       player.getLegion().addContributionPoints(value);
/* 271 */       PacketSendUtility.broadcastPacketToLegion(player.getLegion(), (AionServerPacket)new SM_LEGION_EDIT(3, player.getLegion()));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAp(int value) {
/* 277 */     Player player = getPlayer();
/*     */     
/* 279 */     if (player == null) {
/*     */       return;
/*     */     }
/* 282 */     AbyssRank rank = player.getAbyssRank();
/*     */     
/* 284 */     int oldAbyssRank = rank.getRank().getId();
/*     */     
/* 286 */     rank.addAp(value);
/*     */     
/* 288 */     if (rank.getRank().getId() != oldAbyssRank) {
/*     */       
/* 290 */       PacketSendUtility.broadcastPacket((VisibleObject)player, (AionServerPacket)new SM_ABYSS_RANK_UPDATE(player));
/*     */ 
/*     */       
/* 293 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ABYSS_RANK_UPDATE(player));
/*     */     } 
/*     */ 
/*     */     
/* 297 */     if (rank.getRank().getId() > oldAbyssRank) {
/*     */       
/* 299 */       if (getRace().getRaceId() == 0) {
/*     */         
/* 301 */         switch (rank.getRank().getId()) {
/*     */           
/*     */           case 14:
/* 304 */             player.getSkillList().addSkill(player, 9737, 1, true);
/* 305 */             player.getSkillList().addSkill(player, 9747, 1, true);
/*     */             break;
/*     */           case 15:
/* 308 */             player.getSkillList().removeSkill(player, 9737);
/* 309 */             player.getSkillList().removeSkill(player, 9747);
/* 310 */             player.getSkillList().addSkill(player, 9738, 1, true);
/* 311 */             player.getSkillList().addSkill(player, 9748, 1, true);
/* 312 */             player.getSkillList().addSkill(player, 9751, 1, true);
/*     */             break;
/*     */           case 16:
/* 315 */             player.getSkillList().removeSkill(player, 9738);
/* 316 */             player.getSkillList().removeSkill(player, 9748);
/* 317 */             player.getSkillList().removeSkill(player, 9751);
/* 318 */             player.getSkillList().addSkill(player, 9739, 1, true);
/* 319 */             player.getSkillList().addSkill(player, 9749, 1, true);
/* 320 */             player.getSkillList().addSkill(player, 9751, 1, true);
/* 321 */             player.getSkillList().addSkill(player, 9755, 1, true);
/*     */             break;
/*     */           case 17:
/* 324 */             player.getSkillList().removeSkill(player, 9739);
/* 325 */             player.getSkillList().removeSkill(player, 9749);
/* 326 */             player.getSkillList().removeSkill(player, 9751);
/* 327 */             player.getSkillList().removeSkill(player, 9755);
/* 328 */             player.getSkillList().addSkill(player, 9740, 1, true);
/* 329 */             player.getSkillList().addSkill(player, 9750, 1, true);
/* 330 */             player.getSkillList().addSkill(player, 9752, 1, true);
/* 331 */             player.getSkillList().addSkill(player, 9755, 1, true);
/* 332 */             player.getSkillList().addSkill(player, 9756, 1, true);
/*     */             break;
/*     */           case 18:
/* 335 */             player.getSkillList().removeSkill(player, 9740);
/* 336 */             player.getSkillList().removeSkill(player, 9750);
/* 337 */             player.getSkillList().removeSkill(player, 9752);
/* 338 */             player.getSkillList().removeSkill(player, 9755);
/* 339 */             player.getSkillList().removeSkill(player, 9756);
/* 340 */             player.getSkillList().addSkill(player, 9741, 1, true);
/* 341 */             player.getSkillList().addSkill(player, 9750, 1, true);
/* 342 */             player.getSkillList().addSkill(player, 9752, 1, true);
/* 343 */             player.getSkillList().addSkill(player, 9755, 1, true);
/* 344 */             player.getSkillList().addSkill(player, 9756, 1, true);
/* 345 */             player.getSkillList().addSkill(player, 9757, 1, true);
/* 346 */             player.getSkillList().addSkill(player, 9758, 1, true);
/*     */             break;
/*     */         } 
/*     */ 
/*     */       
/*     */       } else {
/* 352 */         switch (rank.getRank().getId()) {
/*     */           
/*     */           case 14:
/* 355 */             player.getSkillList().addSkill(player, 9742, 1, true);
/* 356 */             player.getSkillList().addSkill(player, 9747, 1, true);
/*     */             break;
/*     */           case 15:
/* 359 */             player.getSkillList().removeSkill(player, 9742);
/* 360 */             player.getSkillList().removeSkill(player, 9747);
/* 361 */             player.getSkillList().addSkill(player, 9743, 1, true);
/* 362 */             player.getSkillList().addSkill(player, 9748, 1, true);
/* 363 */             player.getSkillList().addSkill(player, 9753, 1, true);
/*     */             break;
/*     */           case 16:
/* 366 */             player.getSkillList().removeSkill(player, 9743);
/* 367 */             player.getSkillList().removeSkill(player, 9748);
/* 368 */             player.getSkillList().removeSkill(player, 9753);
/* 369 */             player.getSkillList().addSkill(player, 9744, 1, true);
/* 370 */             player.getSkillList().addSkill(player, 9749, 1, true);
/* 371 */             player.getSkillList().addSkill(player, 9753, 1, true);
/* 372 */             player.getSkillList().addSkill(player, 9755, 1, true);
/*     */             break;
/*     */           case 17:
/* 375 */             player.getSkillList().removeSkill(player, 9744);
/* 376 */             player.getSkillList().removeSkill(player, 9749);
/* 377 */             player.getSkillList().removeSkill(player, 9753);
/* 378 */             player.getSkillList().removeSkill(player, 9755);
/* 379 */             player.getSkillList().addSkill(player, 9745, 1, true);
/* 380 */             player.getSkillList().addSkill(player, 9750, 1, true);
/* 381 */             player.getSkillList().addSkill(player, 9754, 1, true);
/* 382 */             player.getSkillList().addSkill(player, 9755, 1, true);
/* 383 */             player.getSkillList().addSkill(player, 9756, 1, true);
/*     */             break;
/*     */           case 18:
/* 386 */             player.getSkillList().removeSkill(player, 9745);
/* 387 */             player.getSkillList().removeSkill(player, 9750);
/* 388 */             player.getSkillList().removeSkill(player, 9754);
/* 389 */             player.getSkillList().removeSkill(player, 9755);
/* 390 */             player.getSkillList().removeSkill(player, 9756);
/* 391 */             player.getSkillList().addSkill(player, 9746, 1, true);
/* 392 */             player.getSkillList().addSkill(player, 9750, 1, true);
/* 393 */             player.getSkillList().addSkill(player, 9754, 1, true);
/* 394 */             player.getSkillList().addSkill(player, 9755, 1, true);
/* 395 */             player.getSkillList().addSkill(player, 9756, 1, true);
/* 396 */             player.getSkillList().addSkill(player, 9757, 1, true);
/* 397 */             player.getSkillList().addSkill(player, 9758, 1, true);
/*     */             break;
/*     */         } 
/*     */ 
/*     */ 
/*     */       
/*     */       } 
/* 404 */     } else if (getRace().getRaceId() == 0) {
/*     */       
/* 406 */       switch (rank.getRank().getId()) {
/*     */         
/*     */         case 17:
/* 409 */           player.getSkillList().removeSkill(player, 9741);
/* 410 */           player.getSkillList().removeSkill(player, 9750);
/* 411 */           player.getSkillList().removeSkill(player, 9752);
/* 412 */           player.getSkillList().removeSkill(player, 9755);
/* 413 */           player.getSkillList().removeSkill(player, 9756);
/* 414 */           player.getSkillList().removeSkill(player, 9757);
/* 415 */           player.getSkillList().removeSkill(player, 9758);
/* 416 */           player.getSkillList().addSkill(player, 9740, 1, true);
/* 417 */           player.getSkillList().addSkill(player, 9750, 1, true);
/* 418 */           player.getSkillList().addSkill(player, 9752, 1, true);
/* 419 */           player.getSkillList().addSkill(player, 9755, 1, true);
/* 420 */           player.getSkillList().addSkill(player, 9756, 1, true);
/*     */           break;
/*     */         case 16:
/* 423 */           player.getSkillList().removeSkill(player, 9738);
/* 424 */           player.getSkillList().removeSkill(player, 9748);
/* 425 */           player.getSkillList().removeSkill(player, 9751);
/* 426 */           player.getSkillList().addSkill(player, 9739, 1, true);
/* 427 */           player.getSkillList().addSkill(player, 9749, 1, true);
/* 428 */           player.getSkillList().addSkill(player, 9751, 1, true);
/* 429 */           player.getSkillList().addSkill(player, 9755, 1, true);
/*     */           break;
/*     */         case 15:
/* 432 */           player.getSkillList().removeSkill(player, 9737);
/* 433 */           player.getSkillList().removeSkill(player, 9747);
/* 434 */           player.getSkillList().addSkill(player, 9738, 1, true);
/* 435 */           player.getSkillList().addSkill(player, 9748, 1, true);
/* 436 */           player.getSkillList().addSkill(player, 9751, 1, true);
/*     */           break;
/*     */         case 14:
/* 439 */           player.getSkillList().removeSkill(player, 9738);
/* 440 */           player.getSkillList().removeSkill(player, 9748);
/* 441 */           player.getSkillList().removeSkill(player, 9751);
/* 442 */           player.getSkillList().addSkill(player, 9737, 1, true);
/* 443 */           player.getSkillList().addSkill(player, 9747, 1, true);
/*     */           break;
/*     */         case 13:
/* 446 */           player.getSkillList().removeSkill(player, 9737);
/* 447 */           player.getSkillList().removeSkill(player, 9747);
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     } else {
/* 453 */       switch (rank.getRank().getId()) {
/*     */         
/*     */         case 17:
/* 456 */           player.getSkillList().removeSkill(player, 9745);
/* 457 */           player.getSkillList().removeSkill(player, 9750);
/* 458 */           player.getSkillList().removeSkill(player, 9754);
/* 459 */           player.getSkillList().removeSkill(player, 9755);
/* 460 */           player.getSkillList().removeSkill(player, 9756);
/* 461 */           player.getSkillList().addSkill(player, 9745, 1, true);
/* 462 */           player.getSkillList().addSkill(player, 9750, 1, true);
/* 463 */           player.getSkillList().addSkill(player, 9754, 1, true);
/* 464 */           player.getSkillList().addSkill(player, 9755, 1, true);
/* 465 */           player.getSkillList().addSkill(player, 9756, 1, true);
/*     */           break;
/*     */         case 16:
/* 468 */           player.getSkillList().removeSkill(player, 9745);
/* 469 */           player.getSkillList().removeSkill(player, 9750);
/* 470 */           player.getSkillList().removeSkill(player, 9754);
/* 471 */           player.getSkillList().removeSkill(player, 9755);
/* 472 */           player.getSkillList().removeSkill(player, 9756);
/* 473 */           player.getSkillList().addSkill(player, 9744, 1, true);
/* 474 */           player.getSkillList().addSkill(player, 9749, 1, true);
/* 475 */           player.getSkillList().addSkill(player, 9753, 1, true);
/* 476 */           player.getSkillList().addSkill(player, 9755, 1, true);
/*     */           break;
/*     */         case 15:
/* 479 */           player.getSkillList().removeSkill(player, 9744);
/* 480 */           player.getSkillList().removeSkill(player, 9749);
/* 481 */           player.getSkillList().removeSkill(player, 9753);
/* 482 */           player.getSkillList().removeSkill(player, 9755);
/* 483 */           player.getSkillList().addSkill(player, 9743, 1, true);
/* 484 */           player.getSkillList().addSkill(player, 9748, 1, true);
/* 485 */           player.getSkillList().addSkill(player, 9753, 1, true);
/*     */           break;
/*     */         case 14:
/* 488 */           player.getSkillList().removeSkill(player, 9743);
/* 489 */           player.getSkillList().removeSkill(player, 9748);
/* 490 */           player.getSkillList().removeSkill(player, 9753);
/* 491 */           player.getSkillList().addSkill(player, 9742, 1, true);
/* 492 */           player.getSkillList().addSkill(player, 9747, 1, true);
/*     */           break;
/*     */         case 13:
/* 495 */           player.getSkillList().removeSkill(player, 9742);
/* 496 */           player.getSkillList().removeSkill(player, 9747);
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 502 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ABYSS_RANK(player.getAbyssRank()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Race getRace() {
/* 508 */     return this.race;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRace(Race race) {
/* 513 */     this.race = race;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 519 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/* 524 */     this.name = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public PlayerClass getPlayerClass() {
/* 529 */     return this.playerClass;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlayerClass(PlayerClass playerClass) {
/* 534 */     this.playerClass = playerClass;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOnline() {
/* 539 */     return this.online;
/*     */   }
/*     */   
/*     */   public void setOnline(boolean online) {
/* 543 */     this.online = online;
/*     */   }
/*     */ 
/*     */   
/*     */   public Gender getGender() {
/* 548 */     return this.gender;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setGender(Gender gender) {
/* 553 */     this.gender = gender;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldPosition getPosition() {
/* 558 */     return this.position;
/*     */   }
/*     */ 
/*     */   
/*     */   public Timestamp getLastOnline() {
/* 563 */     return this.lastOnline;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBindPoint(int bindId) {
/* 568 */     this.bindPoint = bindId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBindPoint() {
/* 573 */     return this.bindPoint;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLastOnline(Timestamp timestamp) {
/* 578 */     this.lastOnline = timestamp;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLevel() {
/* 583 */     return this.level;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLevel(int level) {
/* 588 */     if (level <= DataManager.PLAYER_EXPERIENCE_TABLE.getMaxLevel())
/*     */     {
/* 590 */       setExp(DataManager.PLAYER_EXPERIENCE_TABLE.getStartExpForLevel(level));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNote() {
/* 596 */     return this.note;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNote(String note) {
/* 601 */     this.note = note;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTitleId() {
/* 606 */     return this.titleId;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTitleId(int titleId) {
/* 611 */     this.titleId = titleId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPosition(WorldPosition position) {
/* 620 */     if (this.position != null)
/*     */     {
/* 622 */       throw new IllegalStateException("position already set");
/*     */     }
/* 624 */     this.position = position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getPlayer() {
/* 634 */     if (this.online && getPosition() != null)
/*     */     {
/* 636 */       return World.getInstance().findPlayer(this.playerObjId);
/*     */     }
/* 638 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addDp(int dp) {
/* 643 */     setDp(this.dp + dp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDp(int dp) {
/* 653 */     if (getPlayer() != null) {
/*     */       
/* 655 */       if (this.playerClass.isStartingClass()) {
/*     */         return;
/*     */       }
/* 658 */       int maxDp = getPlayer().getGameStats().getCurrentStat(StatEnum.MAXDP);
/* 659 */       this.dp = (dp > maxDp) ? maxDp : dp;
/*     */       
/* 661 */       PacketSendUtility.broadcastPacket(getPlayer(), (AionServerPacket)new SM_DP_INFO(this.playerObjId, this.dp), true);
/* 662 */       PacketSendUtility.sendPacket(getPlayer(), (AionServerPacket)new SM_STATS_INFO(getPlayer()));
/* 663 */       PacketSendUtility.sendPacket(getPlayer(), (AionServerPacket)new SM_STATUPDATE_DP(this.dp));
/*     */     }
/*     */     else {
/*     */       
/* 667 */       log.warn("CHECKPOINT : getPlayer in PCD return null for setDP " + isOnline() + " " + getPosition());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDp() {
/* 673 */     return this.dp;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTemplateId() {
/* 679 */     return 100000 + this.race.getRaceId() * 2 + this.gender.getGenderId();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNameId() {
/* 685 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWarehouseSize(int warehouseSize) {
/* 693 */     this.warehouseSize = warehouseSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWarehouseSize() {
/* 701 */     return this.warehouseSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMailboxLetters(int count) {
/* 706 */     this.mailboxLetters = count;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMailboxLetters() {
/* 711 */     return this.mailboxLetters;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\PlayerCommonData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */