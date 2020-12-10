package com.aionemu.gameserver.model.templates.zone;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Points")
public class Points {
  @XmlElement(required = true)
  protected List<Point2D> point;
  @XmlAttribute(name = "top")
  protected float top;
  @XmlAttribute(name = "bottom")
  protected float bottom;
  @XmlAttribute
  protected String type;

  public List<Point2D> getPoint() {
    if (this.point == null) {
      this.point = new ArrayList<Point2D>();
    }
    return this.point;
  }

  public float getTop() {
    return this.top;
  }

  public float getBottom() {
    return this.bottom;
  }

  public String getType() {
    return this.type;
  }
}
