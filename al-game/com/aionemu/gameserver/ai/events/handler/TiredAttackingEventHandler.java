package com.aionemu.gameserver.ai.events.handler;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.events.Event;
import com.aionemu.gameserver.ai.state.AIState;

public class TiredAttackingEventHandler implements EventHandler {
  public Event getEvent() {
    return Event.TIRED_ATTACKING_TARGET;
  }

  public void handleEvent(Event event, AI<?> ai) {
    ai.setAiState(AIState.THINKING);
  }
}
