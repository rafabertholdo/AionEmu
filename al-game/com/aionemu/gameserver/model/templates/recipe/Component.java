package com.aionemu.gameserver.model.templates.recipe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Component")
public class Component {
  @XmlAttribute
  protected int itemid;
  @XmlAttribute
  protected int quantity;

  public Integer getItemid() {
    return Integer.valueOf(this.itemid);
  }

  public Integer getQuantity() {
    return Integer.valueOf(this.quantity);
  }
}
