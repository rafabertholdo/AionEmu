package com.aionemu.gameserver.model.templates.zone;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Point2D")
public class Point2D {
  @XmlAttribute(name = "y")
  protected float y;
  @XmlAttribute(name = "x")
  protected float x;

  public float getY() {
    return this.y;
  }

  public float getX() {
    return this.x;
  }
}
