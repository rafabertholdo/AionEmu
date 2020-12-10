package com.aionemu.gameserver.model.templates.stats;

import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.utils.stats.ClassStats;

public class CalculatedPlayerStatsTemplate extends PlayerStatsTemplate {
  private PlayerClass playerClass;

  public CalculatedPlayerStatsTemplate(PlayerClass playerClass) {
    this.playerClass = playerClass;
  }

  public int getAccuracy() {
    return ClassStats.getAccuracyFor(this.playerClass);
  }

  public int getAgility() {
    return ClassStats.getAgilityFor(this.playerClass);
  }

  public int getHealth() {
    return ClassStats.getHealthFor(this.playerClass);
  }

  public int getKnowledge() {
    return ClassStats.getKnowledgeFor(this.playerClass);
  }

  public int getPower() {
    return ClassStats.getPowerFor(this.playerClass);
  }

  public int getWill() {
    return ClassStats.getWillFor(this.playerClass);
  }

  public float getAttackSpeed() {
    return ClassStats.getAttackSpeedFor(this.playerClass) / 1000.0F;
  }

  public int getBlock() {
    return ClassStats.getBlockFor(this.playerClass);
  }

  public int getEvasion() {
    return ClassStats.getEvasionFor(this.playerClass);
  }

  public float getFlySpeed() {
    return ClassStats.getFlySpeedFor(this.playerClass);
  }

  public int getMagicAccuracy() {
    return ClassStats.getMagicAccuracyFor(this.playerClass);
  }

  public int getMainHandAccuracy() {
    return ClassStats.getMainHandAccuracyFor(this.playerClass);
  }

  public int getMainHandAttack() {
    return ClassStats.getMainHandAttackFor(this.playerClass);
  }

  public int getMainHandCritRate() {
    return ClassStats.getMainHandCritRateFor(this.playerClass);
  }

  public int getMaxHp() {
    return ClassStats.getMaxHpFor(this.playerClass, 10);
  }

  public int getMaxMp() {
    return 1000;
  }

  public int getParry() {
    return ClassStats.getParryFor(this.playerClass);
  }

  public float getRunSpeed() {
    return ClassStats.getSpeedFor(this.playerClass);
  }

  public float getWalkSpeed() {
    return 1.5F;
  }
}
