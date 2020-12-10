package com.aionemu.gameserver.model.templates.teleport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "telelocation")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeleportLocation {
  @XmlAttribute(name = "loc_id", required = true)
  private int locId;
  @XmlAttribute(name = "teleportid")
  private int teleportid = 0;

  @XmlAttribute(name = "price", required = true)
  private int price = 0;

  public int getLocId() {
    return this.locId;
  }

  public int getTeleportId() {
    return this.teleportid;
  }

  public int getPrice() {
    return this.price;
  }
}
