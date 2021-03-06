package com.aionemu.gameserver.model.templates.portal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PortalItem")
public class PortalItem {
  @XmlAttribute(name = "id")
  protected int id;
  @XmlAttribute(name = "itemid")
  protected int itemid;
  @XmlAttribute(name = "quantity")
  protected int quantity;

  public int getId() {
    return this.id;
  }

  public int getItemid() {
    return this.itemid;
  }

  public int getQuantity() {
    return this.quantity;
  }
}
