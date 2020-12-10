package com.aionemu.gameserver.ai.state.handler;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.state.AIState;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.utils.ThreadPoolManager;






















public class TalkingStateHandler
  extends StateHandler
{
  public AIState getState() {
    return AIState.TALKING;
  }






  
  public void handleState(AIState state, AI<?> ai) {
    ai.clearDesires();
    ai.stop();
    
    final Creature owner = ai.getOwner();
    
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            owner.getAi().setAiState(AIState.THINKING);
          }
        },  60000L);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\state\handler\TalkingStateHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
