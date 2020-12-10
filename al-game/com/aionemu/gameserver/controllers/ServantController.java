package com.aionemu.gameserver.controllers;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.Servant;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;





















public class ServantController
  extends NpcController
{
  public void onDie(Creature lastAttacker) {
    onDelete();
    getOwner().getAi().stop();
  }



  
  public void onDialogRequest(Player player) {}



  
  public Servant getOwner() {
    return (Servant)super.getOwner();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\ServantController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
