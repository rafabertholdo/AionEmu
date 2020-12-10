/*     */ package com.aionemu.gameserver.utils.stats;
/*     */ 
/*     */ import com.aionemu.gameserver.model.PlayerClass;
/*     */ import com.aionemu.gameserver.utils.stats.enums.ACCURACY;
/*     */ import com.aionemu.gameserver.utils.stats.enums.AGILITY;
/*     */ import com.aionemu.gameserver.utils.stats.enums.ATTACK_RANGE;
/*     */ import com.aionemu.gameserver.utils.stats.enums.ATTACK_SPEED;
/*     */ import com.aionemu.gameserver.utils.stats.enums.BLOCK;
/*     */ import com.aionemu.gameserver.utils.stats.enums.EARTH_RESIST;
/*     */ import com.aionemu.gameserver.utils.stats.enums.EVASION;
/*     */ import com.aionemu.gameserver.utils.stats.enums.FIRE_RESIST;
/*     */ import com.aionemu.gameserver.utils.stats.enums.FLY_SPEED;
/*     */ import com.aionemu.gameserver.utils.stats.enums.HEALTH;
/*     */ import com.aionemu.gameserver.utils.stats.enums.KNOWLEDGE;
/*     */ import com.aionemu.gameserver.utils.stats.enums.MAGIC_ACCURACY;
/*     */ import com.aionemu.gameserver.utils.stats.enums.MAIN_HAND_ACCURACY;
/*     */ import com.aionemu.gameserver.utils.stats.enums.MAIN_HAND_ATTACK;
/*     */ import com.aionemu.gameserver.utils.stats.enums.MAIN_HAND_CRITRATE;
/*     */ import com.aionemu.gameserver.utils.stats.enums.MAXHP;
/*     */ import com.aionemu.gameserver.utils.stats.enums.PARRY;
/*     */ import com.aionemu.gameserver.utils.stats.enums.POWER;
/*     */ import com.aionemu.gameserver.utils.stats.enums.SPEED;
/*     */ import com.aionemu.gameserver.utils.stats.enums.WATER_RESIST;
/*     */ import com.aionemu.gameserver.utils.stats.enums.WILL;
/*     */ import com.aionemu.gameserver.utils.stats.enums.WIND_RESIST;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClassStats
/*     */ {
/*     */   public static int getMaxHpFor(PlayerClass playerClass, int level) {
/*  57 */     return MAXHP.valueOf(playerClass.toString()).getMaxHpFor(level);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getPowerFor(PlayerClass playerClass) {
/*  66 */     return POWER.valueOf(playerClass.toString()).getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getHealthFor(PlayerClass playerClass) {
/*  75 */     return HEALTH.valueOf(playerClass.toString()).getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getAgilityFor(PlayerClass playerClass) {
/*  84 */     return AGILITY.valueOf(playerClass.toString()).getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getAccuracyFor(PlayerClass playerClass) {
/*  93 */     return ACCURACY.valueOf(playerClass.toString()).getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getKnowledgeFor(PlayerClass playerClass) {
/* 102 */     return KNOWLEDGE.valueOf(playerClass.toString()).getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getWillFor(PlayerClass playerClass) {
/* 111 */     return WILL.valueOf(playerClass.toString()).getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getMainHandAttackFor(PlayerClass playerClass) {
/* 120 */     return MAIN_HAND_ATTACK.valueOf(playerClass.toString()).getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getMainHandCritRateFor(PlayerClass playerClass) {
/* 129 */     return MAIN_HAND_CRITRATE.valueOf(playerClass.toString()).getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getMainHandAccuracyFor(PlayerClass playerClass) {
/* 138 */     return MAIN_HAND_ACCURACY.valueOf(playerClass.toString()).getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getWaterResistFor(PlayerClass playerClass) {
/* 147 */     return WATER_RESIST.valueOf(playerClass.toString()).getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getWindResistFor(PlayerClass playerClass) {
/* 156 */     return WIND_RESIST.valueOf(playerClass.toString()).getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEarthResistFor(PlayerClass playerClass) {
/* 165 */     return EARTH_RESIST.valueOf(playerClass.toString()).getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getFireResistFor(PlayerClass playerClass) {
/* 174 */     return FIRE_RESIST.valueOf(playerClass.toString()).getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getMagicAccuracyFor(PlayerClass playerClass) {
/* 183 */     return MAGIC_ACCURACY.valueOf(playerClass.toString()).getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getEvasionFor(PlayerClass playerClass) {
/* 192 */     return EVASION.valueOf(playerClass.toString()).getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getBlockFor(PlayerClass playerClass) {
/* 201 */     return BLOCK.valueOf(playerClass.toString()).getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getParryFor(PlayerClass playerClass) {
/* 210 */     return PARRY.valueOf(playerClass.toString()).getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getAttackRangeFor(PlayerClass playerClass) {
/* 219 */     return ATTACK_RANGE.valueOf(playerClass.toString()).getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getAttackSpeedFor(PlayerClass playerClass) {
/* 228 */     return ATTACK_SPEED.valueOf(playerClass.toString()).getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getFlySpeedFor(PlayerClass playerClass) {
/* 237 */     return FLY_SPEED.valueOf(playerClass.toString()).getValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getSpeedFor(PlayerClass playerClass) {
/* 246 */     return SPEED.valueOf(playerClass.toString()).getValue();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\ClassStats.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */