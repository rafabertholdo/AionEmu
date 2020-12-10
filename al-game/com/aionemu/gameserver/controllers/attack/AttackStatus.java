package com.aionemu.gameserver.controllers.attack;

public enum AttackStatus {
  DODGE(0), OFFHAND_DODGE(1), PARRY(2), OFFHAND_PARRY(3), BLOCK(4), OFFHAND_BLOCK(5), RESIST(6), OFFHAND_RESIST(7),
  BUF(8), OFFHAND_BUF(9), NORMALHIT(10), OFFHAND_NORMALHIT(11), CRITICAL_DODGE(-64), CRITICAL_PARRY(-62),
  CRITICAL_BLOCK(-60), CRITICAL_RESIST(-58), CRITICAL(-54), OFFHAND_CRITICAL_DODGE(-47), OFFHAND_CRITICAL_PARRY(-45),
  OFFHAND_CRITICAL_BLOCK(-43), OFFHAND_CRITICAL_RESIST(-41), OFFHAND_CRITICAL(-37);

  private int _type;

  AttackStatus(int type) {
    this._type = type;
  }

  public int getId() {
    return this._type;
  }
}
