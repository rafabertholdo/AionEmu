package com.aionemu.gameserver.ai.state;

import com.aionemu.gameserver.ai.state.handler.ActiveAggroStateHandler;
import com.aionemu.gameserver.ai.state.handler.ActiveNpcStateHandler;
import com.aionemu.gameserver.ai.state.handler.AttackingStateHandler;
import com.aionemu.gameserver.ai.state.handler.MovingToHomeStateHandler;
import com.aionemu.gameserver.ai.state.handler.NoneNpcStateHandler;
import com.aionemu.gameserver.ai.state.handler.RestingStateHandler;
import com.aionemu.gameserver.ai.state.handler.StateHandler;
import com.aionemu.gameserver.ai.state.handler.TalkingStateHandler;
import com.aionemu.gameserver.ai.state.handler.ThinkingStateHandler;

public enum StateHandlers {
  MOVINGTOHOME_SH((StateHandler) new MovingToHomeStateHandler()),

  NONE_MONSTER_SH((StateHandler) new NoneNpcStateHandler()),

  ATTACKING_SH((StateHandler) new AttackingStateHandler()),

  THINKING_SH((StateHandler) new ThinkingStateHandler()),

  ACTIVE_NPC_SH((StateHandler) new ActiveNpcStateHandler()),
  ACTIVE_AGGRO_SH((StateHandler) new ActiveAggroStateHandler()),

  RESTING_SH((StateHandler) new RestingStateHandler()),

  TALKING_SH((StateHandler) new TalkingStateHandler());

  private StateHandler stateHandler;

  StateHandlers(StateHandler stateHandler) {
    this.stateHandler = stateHandler;
  }

  public StateHandler getHandler() {
    return this.stateHandler;
  }
}
