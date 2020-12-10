package com.aionemu.gameserver.ai.desires.impl;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.desires.AbstractDesire;
import com.aionemu.gameserver.ai.events.Event;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.utils.MathUtil;

public final class AttackDesire extends AbstractDesire {
  private int attackNotPossibleCounter;
  private int attackCounter = 1;

  protected Creature target;

  protected Npc owner;

  public AttackDesire(Npc npc, Creature target, int desirePower) {
    super(desirePower);
    this.target = target;
    this.owner = npc;
  }

  public boolean handleDesire(AI<?> ai) {
    if (this.target == null || this.target.getLifeStats().isAlreadyDead()) {

      this.owner.getAggroList().stopHating(this.target);
      this.owner.getAi().handleEvent(Event.TIRED_ATTACKING_TARGET);
      return false;
    }

    double distance = MathUtil.getDistance(this.owner.getX(), this.owner.getY(), this.owner.getZ(), this.target.getX(),
        this.target.getY(), this.target.getZ());

    if (distance > 50.0D) {

      this.owner.getAggroList().stopHating(this.target);
      this.owner.getAi().handleEvent(Event.TIRED_ATTACKING_TARGET);
      return false;
    }

    this.attackCounter++;

    if (this.attackCounter % 2 == 0) {
      if (!this.owner.getAggroList().isMostHated(this.target)) {

        this.owner.getAi().handleEvent(Event.TIRED_ATTACKING_TARGET);
        return false;
      }
    }
    int attackRange = this.owner.getGameStats().getCurrentStat(StatEnum.ATTACK_RANGE);
    if (distance * 1000.0D <= attackRange) {

      this.owner.getController().attackTarget(this.target);
      this.attackNotPossibleCounter = 0;
    } else {

      this.attackNotPossibleCounter++;
    }

    if (this.attackNotPossibleCounter > 10) {

      this.owner.getAggroList().stopHating(this.target);
      this.owner.getAi().handleEvent(Event.TIRED_ATTACKING_TARGET);
      return false;
    }
    return true;
  }

  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof AttackDesire)) {
      return false;
    }
    AttackDesire that = (AttackDesire) o;

    return this.target.equals(that.target);
  }

  public int hashCode() {
    return this.target.hashCode();
  }

  public Creature getTarget() {
    return this.target;
  }

  public int getExecutionInterval() {
    return 2;
  }

  public void onClear() {
    this.owner.unsetState(CreatureState.WEAPON_EQUIPPED);
  }
}
