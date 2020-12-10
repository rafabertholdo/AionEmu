package com.aionemu.gameserver.ai.events;

import com.aionemu.gameserver.ai.events.handler.AttackedEventHandler;
import com.aionemu.gameserver.ai.events.handler.BackHomeEventHandler;
import com.aionemu.gameserver.ai.events.handler.EventHandler;
import com.aionemu.gameserver.ai.events.handler.NotSeePlayerEventHandler;
import com.aionemu.gameserver.ai.events.handler.NothingTodoEventHandler;
import com.aionemu.gameserver.ai.events.handler.RestoredHealthEventHandler;
import com.aionemu.gameserver.ai.events.handler.SeePlayerEventHandler;
import com.aionemu.gameserver.ai.events.handler.TalkEventHandler;
import com.aionemu.gameserver.ai.events.handler.TiredAttackingEventHandler;

public enum EventHandlers {
  ATTACKED_EH((EventHandler) new AttackedEventHandler()),
  TIREDATTACKING_EH((EventHandler) new TiredAttackingEventHandler()),
  MOST_HATED_CHANGED_EH((EventHandler) new TiredAttackingEventHandler()),
  SEEPLAYER_EH((EventHandler) new SeePlayerEventHandler()),
  NOTSEEPLAYER_EH((EventHandler) new NotSeePlayerEventHandler()),
  BACKHOME_EH((EventHandler) new BackHomeEventHandler()), TALK_EH((EventHandler) new TalkEventHandler()),
  RESTOREDHEALTH_EH((EventHandler) new RestoredHealthEventHandler()),
  NOTHINGTODO_EH((EventHandler) new NothingTodoEventHandler());

  private EventHandler eventHandler;

  EventHandlers(EventHandler eventHandler) {
    this.eventHandler = eventHandler;
  }

  public EventHandler getHandler() {
    return this.eventHandler;
  }
}
