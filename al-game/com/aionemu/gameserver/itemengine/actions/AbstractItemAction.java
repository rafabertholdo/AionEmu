package com.aionemu.gameserver.itemengine.actions;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractItemAction")
public abstract class AbstractItemAction {
  public abstract boolean canAct(Player paramPlayer, Item paramItem1, Item paramItem2);
  
  public abstract void act(Player paramPlayer, Item paramItem1, Item paramItem2);
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\itemengine\actions\AbstractItemAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */