package com.aionemu.gameserver.ai.events.handler;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.events.Event;
import com.aionemu.gameserver.ai.state.AIState;

public class BackHomeEventHandler implements EventHandler {
  public Event getEvent() {
    return Event.BACK_HOME;
  }

  public void handleEvent(Event event, AI<?> ai) {
    ai.setAiState(AIState.RESTING);
  }
}
