package com.aionemu.gameserver.ai.events.handler;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.events.Event;
import com.aionemu.gameserver.ai.state.AIState;
import com.aionemu.gameserver.model.gameobjects.Npc;

public class TalkEventHandler implements EventHandler {
  public Event getEvent() {
    return Event.TALK;
  }

  public void handleEvent(Event event, AI<?> ai) {
    if (((Npc) ai.getOwner()).hasWalkRoutes())
      ai.setAiState(AIState.TALKING);
  }
}
