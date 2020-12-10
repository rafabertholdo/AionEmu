package com.aionemu.gameserver.model.templates.itemset;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

























@XmlRootElement(name = "ItemPart")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemPart
{
  @XmlAttribute
  protected int itemid;
  
  public int getItemid() {
    return this.itemid;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\itemset\ItemPart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
