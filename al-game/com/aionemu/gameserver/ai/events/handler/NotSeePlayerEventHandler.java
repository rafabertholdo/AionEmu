package com.aionemu.gameserver.ai.events.handler;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.events.Event;
import com.aionemu.gameserver.ai.state.AIState;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;























public class NotSeePlayerEventHandler
  implements EventHandler
{
  public Event getEvent() {
    return Event.NOT_SEE_PLAYER;
  }


  
  public void handleEvent(Event event, AI<?> ai) {
    int playerCount = 0;
    for (VisibleObject visibleObject : ai.getOwner().getKnownList().getKnownObjects().values()) {
      
      if (visibleObject instanceof com.aionemu.gameserver.model.gameobjects.player.Player)
        playerCount++; 
    } 
    if (playerCount == 0) {
      
      ai.getOwner().getKnownList().clearKnownList();
      ai.setAiState(AIState.THINKING);
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\events\handler\NotSeePlayerEventHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
