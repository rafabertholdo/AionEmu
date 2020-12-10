package com.aionemu.gameserver.controllers;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.Servant;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;

public class ServantController extends NpcController {
  public void onDie(Creature lastAttacker) {
    onDelete();
    getOwner().getAi().stop();
  }

  public void onDialogRequest(Player player) {
  }

  public Servant getOwner() {
    return (Servant) super.getOwner();
  }
}
