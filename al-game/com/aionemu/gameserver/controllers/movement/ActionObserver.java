package com.aionemu.gameserver.controllers.movement;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.Skill;



















public class ActionObserver
{
  private ObserverType observerType;
  
  public enum ObserverType
  {
    MOVE,
    ATTACK,
    ATTACKED,
    EQUIP,
    SKILLUSE;
  }



  
  public ActionObserver(ObserverType observerType) {
    this.observerType = observerType;
  }




  
  public ObserverType getObserverType() {
    return this.observerType;
  }
  
  public void moved() {}
  
  public void attacked(Creature creature) {}
  
  public void attack(Creature creature) {}
  
  public void equip(Item item, Player owner) {}
  
  public void unequip(Item item, Player owner) {}
  
  public void skilluse(Skill skill) {}
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\movement\ActionObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
