package com.aionemu.gameserver.model.templates.walker;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "routes")
@XmlAccessorType(XmlAccessType.FIELD)
public class RouteData {
  @XmlElement(name = "routestep")
  private List<RouteStep> stepids;

  public List<RouteStep> getRouteSteps() {
    return this.stepids;
  }

  public RouteStep getRouteStep(int value) {
    for (RouteStep t : this.stepids) {

      if (t != null && t.getRouteStep() == value) {
        return t;
      }
    }
    return null;
  }
}
