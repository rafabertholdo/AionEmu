package com.aionemu.gameserver.ai.npcai;

import com.aionemu.gameserver.ai.events.EventHandlers;
import com.aionemu.gameserver.ai.state.StateHandlers;

public class MonsterAi extends NpcAi {
  public MonsterAi() {
    addEventHandler(EventHandlers.ATTACKED_EH.getHandler());
    addEventHandler(EventHandlers.TIREDATTACKING_EH.getHandler());
    addEventHandler(EventHandlers.MOST_HATED_CHANGED_EH.getHandler());
    addEventHandler(EventHandlers.BACKHOME_EH.getHandler());
    addEventHandler(EventHandlers.RESTOREDHEALTH_EH.getHandler());

    addStateHandler(StateHandlers.MOVINGTOHOME_SH.getHandler());
    addStateHandler(StateHandlers.NONE_MONSTER_SH.getHandler());
    addStateHandler(StateHandlers.ATTACKING_SH.getHandler());
    addStateHandler(StateHandlers.THINKING_SH.getHandler());
    addStateHandler(StateHandlers.RESTING_SH.getHandler());
  }
}
