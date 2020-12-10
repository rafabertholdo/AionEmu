package com.aionemu.gameserver.ai.state.handler;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.desires.Desire;
import com.aionemu.gameserver.ai.desires.impl.RestoreHealthDesire;
import com.aionemu.gameserver.ai.state.AIState;

public class RestingStateHandler extends StateHandler {
  public AIState getState() {
    return AIState.RESTING;
  }

  public void handleState(AIState state, AI<?> ai) {
    ai.addDesire((Desire) new RestoreHealthDesire(ai.getOwner(), AIState.RESTING.getPriority()));
  }
}
