package com.aionemu.gameserver.ai.events.handler;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.events.Event;
import com.aionemu.gameserver.ai.state.AIState;

public class NothingTodoEventHandler implements EventHandler {
  public Event getEvent() {
    return Event.NOTHING_TODO;
  }

  public void handleEvent(Event event, AI<?> ai) {
    ai.setAiState(AIState.NONE);
  }
}
