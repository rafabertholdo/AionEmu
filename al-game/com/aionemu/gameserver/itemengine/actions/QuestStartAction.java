package com.aionemu.gameserver.itemengine.actions;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuestStartAction")
public class QuestStartAction extends AbstractItemAction {
  @XmlAttribute
  protected int questid;

  public boolean canAct(Player player, Item parentItem, Item targetItem) {
    return true;
  }

  public void act(Player player, Item parentItem, Item targetItem) {
  }
}
