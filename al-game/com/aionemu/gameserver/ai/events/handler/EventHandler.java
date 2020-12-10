package com.aionemu.gameserver.ai.events.handler;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.events.Event;

public interface EventHandler {
  Event getEvent();

  void handleEvent(Event paramEvent, AI<?> paramAI);
}
