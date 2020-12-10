package com.aionemu.gameserver.controllers.attack;

public class AttackResult {
  private int damage;
  private AttackStatus attackStatus;
  private int shieldType;

  public AttackResult(int damage, AttackStatus attackStatus) {
    this.damage = damage;
    this.attackStatus = attackStatus;
  }

  public int getDamage() {
    return this.damage;
  }

  public void setDamage(int damage) {
    this.damage = damage;
  }

  public AttackStatus getAttackStatus() {
    return this.attackStatus;
  }

  public int getShieldType() {
    return this.shieldType;
  }

  public void setShieldType(int shieldType) {
    this.shieldType = shieldType;
  }
}
