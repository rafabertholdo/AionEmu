package com.aionemu.gameserver.ai.state.handler;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.state.AIState;

public abstract class StateHandler {
  public abstract AIState getState();

  public abstract void handleState(AIState paramAIState, AI<?> paramAI);
}
