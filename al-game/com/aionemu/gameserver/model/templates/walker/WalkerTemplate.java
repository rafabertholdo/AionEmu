package com.aionemu.gameserver.model.templates.walker;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "walker_template")
@XmlAccessorType(XmlAccessType.NONE)
public class WalkerTemplate {
  @XmlAttribute(name = "route_id", required = true)
  private int routeId;
  @XmlElement(name = "routes")
  private RouteData routeData;

  public int getRouteId() {
    return this.routeId;
  }

  public RouteData getRouteData() {
    return this.routeData;
  }
}
