package com.aionemu.gameserver.controllers.movement;

import com.aionemu.gameserver.controllers.attack.AttackResult;
import com.aionemu.gameserver.controllers.attack.AttackStatus;
import java.util.List;

public class AttackCalcObserver {
  public boolean checkStatus(AttackStatus status) {
    return false;
  }

  public void checkShield(List<AttackResult> attackList) {
  }

  public boolean checkAttackerStatus(AttackStatus status) {
    return false;
  }
}
