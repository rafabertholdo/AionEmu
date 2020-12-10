/*     */ package com.aionemu.gameserver.model.templates.stats;
/*     */ 
/*     */ import com.aionemu.gameserver.model.PlayerClass;
/*     */ import com.aionemu.gameserver.utils.stats.ClassStats;
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
/*     */ public class CalculatedPlayerStatsTemplate
/*     */   extends PlayerStatsTemplate
/*     */ {
/*     */   private PlayerClass playerClass;
/*     */   
/*     */   public CalculatedPlayerStatsTemplate(PlayerClass playerClass) {
/*  33 */     this.playerClass = playerClass;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAccuracy() {
/*  39 */     return ClassStats.getAccuracyFor(this.playerClass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAgility() {
/*  45 */     return ClassStats.getAgilityFor(this.playerClass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHealth() {
/*  51 */     return ClassStats.getHealthFor(this.playerClass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getKnowledge() {
/*  57 */     return ClassStats.getKnowledgeFor(this.playerClass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPower() {
/*  63 */     return ClassStats.getPowerFor(this.playerClass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWill() {
/*  69 */     return ClassStats.getWillFor(this.playerClass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAttackSpeed() {
/*  75 */     return ClassStats.getAttackSpeedFor(this.playerClass) / 1000.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBlock() {
/*  81 */     return ClassStats.getBlockFor(this.playerClass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEvasion() {
/*  87 */     return ClassStats.getEvasionFor(this.playerClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getFlySpeed() {
/*  94 */     return ClassStats.getFlySpeedFor(this.playerClass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMagicAccuracy() {
/* 100 */     return ClassStats.getMagicAccuracyFor(this.playerClass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMainHandAccuracy() {
/* 106 */     return ClassStats.getMainHandAccuracyFor(this.playerClass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMainHandAttack() {
/* 112 */     return ClassStats.getMainHandAttackFor(this.playerClass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMainHandCritRate() {
/* 118 */     return ClassStats.getMainHandCritRateFor(this.playerClass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxHp() {
/* 124 */     return ClassStats.getMaxHpFor(this.playerClass, 10);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxMp() {
/* 130 */     return 1000;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getParry() {
/* 136 */     return ClassStats.getParryFor(this.playerClass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getRunSpeed() {
/* 142 */     return ClassStats.getSpeedFor(this.playerClass);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getWalkSpeed() {
/* 148 */     return 1.5F;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\stats\CalculatedPlayerStatsTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */