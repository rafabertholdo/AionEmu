package com.aionemu.gameserver.ai.events.handler;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.events.Event;
import com.aionemu.gameserver.ai.state.AIState;






















public class TiredAttackingEventHandler
  implements EventHandler
{
  public Event getEvent() {
    return Event.TIRED_ATTACKING_TARGET;
  }


  
  public void handleEvent(Event event, AI<?> ai) {
    ai.setAiState(AIState.THINKING);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\events\handler\TiredAttackingEventHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
