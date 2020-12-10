package com.aionemu.gameserver.ai.events.handler;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.events.Event;
import com.aionemu.gameserver.ai.state.AIState;

public class SeePlayerEventHandler implements EventHandler {
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
