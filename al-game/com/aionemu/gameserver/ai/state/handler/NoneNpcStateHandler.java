package com.aionemu.gameserver.ai.state.handler;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.state.AIState;
import com.aionemu.gameserver.model.gameobjects.Npc;

public class NoneNpcStateHandler extends StateHandler {
  public AIState getState() {
    return AIState.NONE;
  }

  public void handleState(AIState state, AI<?> ai) {
    ai.clearDesires();
    ((Npc) ai.getOwner()).getAggroList().clear();
    ai.stop();
  }
}
