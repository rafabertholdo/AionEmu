package com.aionemu.gameserver.model.templates.portal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExitPoint")
public class ExitPoint
{
  @XmlAttribute(name = "mapid")
  protected int mapId;
  @XmlAttribute(name = "x")
  protected float x;
  @XmlAttribute(name = "y")
  protected float y;
  @XmlAttribute(name = "z")
  protected float z;
  
  public int getMapId() {
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
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\portal\ExitPoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
