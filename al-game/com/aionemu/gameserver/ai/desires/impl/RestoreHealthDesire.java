package com.aionemu.gameserver.ai.desires.impl;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.desires.AbstractDesire;
import com.aionemu.gameserver.ai.events.Event;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;

public class RestoreHealthDesire extends AbstractDesire {
  private Creature owner;
  private int restoreHpValue;

  public RestoreHealthDesire(Creature owner, int desirePower) {
    super(desirePower);
    this.owner = owner;
    this.restoreHpValue = owner.getLifeStats().getMaxHp() / 5;
  }

  public boolean handleDesire(AI<?> ai) {
    if (this.owner == null || this.owner.getLifeStats().isAlreadyDead()) {
      return false;
    }
    this.owner.getLifeStats().increaseHp(SM_ATTACK_STATUS.TYPE.NATURAL_HP, this.restoreHpValue);
    if (this.owner.getLifeStats().isFullyRestoredHpMp()) {

      ai.handleEvent(Event.RESTORED_HEALTH);
      return false;
    }
    return true;
  }

  public int getExecutionInterval() {
    return 1;
  }

  public void onClear() {
  }
}
