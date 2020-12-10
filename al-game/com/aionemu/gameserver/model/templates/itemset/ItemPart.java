package com.aionemu.gameserver.model.templates.itemset;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ItemPart")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemPart {
  @XmlAttribute
  protected int itemid;

  public int getItemid() {
    return this.itemid;
  }
}
