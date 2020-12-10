package com.aionemu.gameserver.ai.desires.impl;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.desires.AbstractDesire;
import com.aionemu.gameserver.ai.desires.MoveDesire;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;

public class MoveToTargetDesire extends AbstractDesire implements MoveDesire {
  private Npc owner;
  private Creature target;

  public MoveToTargetDesire(Npc owner, Creature target, int desirePower) {
    super(desirePower);
    this.owner = owner;
    this.target = target;
  }

  public boolean handleDesire(AI ai) {
    if (this.owner == null || this.owner.getLifeStats().isAlreadyDead())
      return false;
    if (this.target == null || this.target.getLifeStats().isAlreadyDead()) {
      return false;
    }
    this.owner.getMoveController().setFollowTarget(true);

    if (!this.owner.getMoveController().isScheduled()) {
      this.owner.getMoveController().schedule();
    }
    double distance = this.owner.getMoveController().getDistanceToTarget();
    if (distance > 150.0D) {
      return false;
    }
    return true;
  }

  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof MoveToTargetDesire)) {
      return false;
    }
    MoveToTargetDesire that = (MoveToTargetDesire) o;
    return this.target.equals(that.target);
  }

  public Creature getTarget() {
    return this.target;
  }

  public int getExecutionInterval() {
    return 1;
  }

  public void onClear() {
    this.owner.getMoveController().stop();
  }
}
