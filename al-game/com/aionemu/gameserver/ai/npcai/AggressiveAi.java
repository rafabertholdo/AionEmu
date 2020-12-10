package com.aionemu.gameserver.ai.npcai;

import com.aionemu.gameserver.ai.events.EventHandlers;
import com.aionemu.gameserver.ai.state.StateHandlers;

public class AggressiveAi extends MonsterAi {
  public AggressiveAi() {
    addEventHandler(EventHandlers.SEEPLAYER_EH.getHandler());
    addEventHandler(EventHandlers.NOTSEEPLAYER_EH.getHandler());

    addStateHandler(StateHandlers.ACTIVE_AGGRO_SH.getHandler());
  }
}
