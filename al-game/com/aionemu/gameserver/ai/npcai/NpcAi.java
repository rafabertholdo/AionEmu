package com.aionemu.gameserver.ai.npcai;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.events.Event;
import com.aionemu.gameserver.ai.events.EventHandlers;
import com.aionemu.gameserver.ai.events.handler.EventHandler;
import com.aionemu.gameserver.ai.state.StateHandlers;
import com.aionemu.gameserver.model.gameobjects.Npc;

public class NpcAi extends AI<Npc> {
  public NpcAi() {
    addEventHandler(EventHandlers.NOTHINGTODO_EH.getHandler());
    addEventHandler(EventHandlers.TALK_EH.getHandler());
    addEventHandler(EventHandlers.SEEPLAYER_EH.getHandler());

    addStateHandler(StateHandlers.ACTIVE_NPC_SH.getHandler());
    addStateHandler(StateHandlers.TALKING_SH.getHandler());
  }

  public void handleEvent(Event event) {
    super.handleEvent(event);

    if (this.owner.getLifeStats().isAlreadyDead()) {
      return;
    }
    EventHandler eventHandler = (EventHandler) this.eventHandlers.get(event);
    if (eventHandler != null)
      eventHandler.handleEvent(event, this);
  }
}
