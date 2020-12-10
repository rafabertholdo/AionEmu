package com.aionemu.gameserver.ai.state.handler;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.desires.Desire;
import com.aionemu.gameserver.ai.desires.impl.RestoreHealthDesire;
import com.aionemu.gameserver.ai.state.AIState;





















public class RestingStateHandler
  extends StateHandler
{
  public AIState getState() {
    return AIState.RESTING;
  }






  
  public void handleState(AIState state, AI<?> ai) {
    ai.addDesire((Desire)new RestoreHealthDesire(ai.getOwner(), AIState.RESTING.getPriority()));
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\state\handler\RestingStateHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
