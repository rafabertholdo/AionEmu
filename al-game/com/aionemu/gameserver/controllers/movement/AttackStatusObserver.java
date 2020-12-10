package com.aionemu.gameserver.controllers.movement;

import com.aionemu.gameserver.controllers.attack.AttackStatus;

public class AttackStatusObserver extends AttackCalcObserver {
  protected int value;
  protected AttackStatus status;

  public AttackStatusObserver(int value, AttackStatus status) {
    this.value = value;
    this.status = status;
  }
}
