package com.aionemu.gameserver.ai.npcai;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.desires.AbstractDesire;
import com.aionemu.gameserver.ai.desires.Desire;
import com.aionemu.gameserver.ai.events.Event;
import com.aionemu.gameserver.ai.state.AIState;
import com.aionemu.gameserver.ai.state.handler.NoneNpcStateHandler;
import com.aionemu.gameserver.ai.state.handler.StateHandler;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Servant;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.skillengine.model.Skill;

public class ServantAi extends NpcAi {
  public ServantAi() {
    addStateHandler(new ActiveServantStateHandler());
    addStateHandler((StateHandler) new NoneNpcStateHandler());
  }

  class ActiveServantStateHandler extends StateHandler {
    public AIState getState() {
      return AIState.ACTIVE;
    }

    public void handleState(AIState state, AI<?> ai) {
      ai.clearDesires();
      Servant owner = (Servant) ai.getOwner();
      Creature servantOwner = owner.getCreator();

      VisibleObject servantOwnerTarget = servantOwner.getTarget();
      if (servantOwnerTarget instanceof Creature) {
        ai.addDesire((Desire) new ServantAi.ServantSkillUseDesire(owner, (Creature) servantOwnerTarget,
            AIState.ACTIVE.getPriority()));
      }

      if (ai.desireQueueSize() == 0) {
        ai.handleEvent(Event.NOTHING_TODO);
      } else {
        ai.schedule();
      }
    }
  }

  class ServantSkillUseDesire extends AbstractDesire {
    private Servant owner;

    private Creature target;

    private ServantSkillUseDesire(Servant owner, Creature target, int desirePower) {
      super(desirePower);
      this.owner = owner;
      this.target = target;
    }

    public boolean handleDesire(AI<?> ai) {
      if (this.target == null || this.target.getLifeStats().isAlreadyDead()) {
        return true;
      }
      if (!this.owner.getActingCreature().isEnemy((VisibleObject) this.target)) {
        return false;
      }
      Skill skill = SkillEngine.getInstance().getSkill((Creature) this.owner, this.owner.getSkillId(), 1,
          (VisibleObject) this.target);
      if (skill != null) {

        skill.useSkill();

        int maxHp = this.owner.getLifeStats().getMaxHp();
        this.owner.getLifeStats().reduceHp(Math.round(maxHp / 3.0F), null);
      }
      return true;
    }

    public int getExecutionInterval() {
      return 10;
    }

    public void onClear() {
    }
  }
}
