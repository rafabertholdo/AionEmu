package com.aionemu.gameserver.model.templates;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "bind_points")
@XmlAccessorType(XmlAccessType.NONE)
public class BindPointTemplate {
  @XmlAttribute(name = "name", required = true)
  private String name;
  @XmlAttribute(name = "npcid")
  private int npcId;
  @XmlAttribute(name = "bindid")
  private int bindId;
  @XmlAttribute(name = "mapid")
  private int mapId = 0;

  @XmlAttribute(name = "posX")
  private float x = 0.0F;

  @XmlAttribute(name = "posY")
  private float y = 0.0F;

  @XmlAttribute(name = "posZ")
  private float z = 0.0F;

  @XmlAttribute(name = "price")
  private int price = 0;

  public String getName() {
    return this.name;
  }

  public int getNpcId() {
    return this.npcId;
  }

  public int getBindId() {
    return this.bindId;
  }

  public int getZoneId() {
    return this.mapId;
  }

  public float getX() {
    return this.x;
  }

  public float getY() {
    return this.y;
  }

  public float getZ() {
    return this.z;
  }

  public int getPrice() {
    return this.price;
  }
}
