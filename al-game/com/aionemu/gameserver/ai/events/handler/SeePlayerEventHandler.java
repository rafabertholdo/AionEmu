package com.aionemu.gameserver.ai.events.handler;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.events.Event;
import com.aionemu.gameserver.ai.state.AIState;






















public class SeePlayerEventHandler
  implements EventHandler
{
  public Event getEvent() {
    return Event.SEE_PLAYER;
  }


  
  public void handleEvent(Event event, AI<?> ai) {
    ai.setAiState(AIState.ACTIVE);
    if (!ai.isScheduled())
      ai.analyzeState(); 
    ai.getOwner().getKnownList().updateKnownList();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\events\handler\SeePlayerEventHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
