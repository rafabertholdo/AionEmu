package com.aionemu.gameserver.ai.state.handler;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.state.AIState;
import com.aionemu.gameserver.model.gameobjects.Npc;






















public class NoneNpcStateHandler
  extends StateHandler
{
  public AIState getState() {
    return AIState.NONE;
  }





  
  public void handleState(AIState state, AI<?> ai) {
    ai.clearDesires();
    ((Npc)ai.getOwner()).getAggroList().clear();
    ai.stop();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\state\handler\NoneNpcStateHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
