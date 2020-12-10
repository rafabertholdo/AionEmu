package com.aionemu.gameserver.dataholders.loadingutils.adapters;

import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;

public class NpcEquipmentList {
  @XmlElement(name = "item")
  @XmlIDREF
  public ItemTemplate[] items;
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\loadingutils\adapters\NpcEquipmentList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */