package com.aionemu.gameserver.ai.state.handler;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.state.AIState;

public abstract class StateHandler {
  public abstract AIState getState();
  
  public abstract void handleState(AIState paramAIState, AI<?> paramAI);
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\state\handler\StateHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */