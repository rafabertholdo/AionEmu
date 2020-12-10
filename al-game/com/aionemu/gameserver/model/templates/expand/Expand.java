package com.aionemu.gameserver.model.templates.expand;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Expand")
public class Expand {
  @XmlAttribute(name = "level", required = true)
  protected int level;
  @XmlAttribute(name = "price", required = true)
  protected int price;

  public int getLevel() {
    return this.level;
  }

  public int getPrice() {
    return this.price;
  }
}
