package com.aionemu.gameserver.model.templates.walker;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;




























@XmlRootElement(name = "routestep")
@XmlAccessorType(XmlAccessType.FIELD)
public class RouteStep
{
  @XmlAttribute(name = "step", required = true)
  private int step;
  @XmlAttribute(name = "loc_x", required = true)
  private float locX;
  @XmlAttribute(name = "loc_y", required = true)
  private float locY;
  @XmlAttribute(name = "loc_z", required = true)
  private float locZ;
  @XmlAttribute(name = "rest_time", required = true)
  private int time;
  
  public RouteStep() {}
  
  public RouteStep(float x, float y, float z) {
    this.locX = x;
    this.locY = y;
    this.locZ = z;
  }

  
  public float getX() {
    return this.locX;
  }

  
  public float getY() {
    return this.locY;
  }

  
  public float getZ() {
    return this.locZ;
  }

  
  public int getRouteStep() {
    return this.step;
  }

  
  public int getRestTime() {
    return this.time;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\walker\RouteStep.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
