package com.aionemu.gameserver.ai.state.handler;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.desires.Desire;
import com.aionemu.gameserver.ai.desires.impl.AggressionDesire;
import com.aionemu.gameserver.ai.desires.impl.WalkDesire;
import com.aionemu.gameserver.ai.events.Event;
import com.aionemu.gameserver.ai.state.AIState;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;

public class ActiveAggroStateHandler extends StateHandler {
  public AIState getState() {
    return AIState.ACTIVE;
  }

  public void handleState(AIState state, AI<?> ai) {
    ai.clearDesires();
    Npc owner = (Npc) ai.getOwner();

    int creatureCount = 0;
    for (VisibleObject visibleObject : owner.getKnownList().getKnownObjects().values()) {

      if (visibleObject instanceof Creature) {
        if (owner.isAggressiveTo((Creature) visibleObject))
          creatureCount++;
      }
    }
    if (creatureCount > 0) {

      ai.addDesire((Desire) new AggressionDesire(owner, AIState.ACTIVE.getPriority()));
    } else if (owner.hasWalkRoutes()) {

      ai.addDesire((Desire) new WalkDesire(owner, AIState.ACTIVE.getPriority()));
    }
    if (ai.desireQueueSize() == 0) {
      ai.handleEvent(Event.NOTHING_TODO);
    } else {
      ai.schedule();
    }
  }
}
