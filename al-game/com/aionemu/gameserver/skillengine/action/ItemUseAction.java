package com.aionemu.gameserver.skillengine.action;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.skillengine.model.Skill;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemUseAction")
public class ItemUseAction
  extends Action
{
  @XmlAttribute(required = true)
  protected int itemid;
  @XmlAttribute(required = true)
  protected int count;
  
  public void act(Skill skill) {
    if (skill.getEffector() instanceof Player) {
      
      Player player = (Player)skill.getEffector();
      if (!ItemService.decreaseItemCountByItemId(player, this.itemid, this.count))
        return; 
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\action\ItemUseAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
