package com.aionemu.gameserver.model.templates.teleport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
























@XmlRootElement(name = "teleloc_template")
@XmlAccessorType(XmlAccessType.NONE)
public class TelelocationTemplate
{
  @XmlAttribute(name = "loc_id", required = true)
  private int locId;
  @XmlAttribute(name = "mapid", required = true)
  private int mapid = 0;


  
  @XmlAttribute(name = "name", required = true)
  private String name = "";

  
  @XmlAttribute(name = "posX")
  private float x = 0.0F;
  
  @XmlAttribute(name = "posY")
  private float y = 0.0F;
  
  @XmlAttribute(name = "posZ")
  private float z = 0.0F;
  
  @XmlAttribute(name = "heading")
  private int heading = 0;


  
  public int getLocId() {
    return this.locId;
  }

  
  public int getMapId() {
    return this.mapid;
  }

  
  public String getName() {
    return this.name;
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

  
  public int getHeading() {
    return this.heading;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\teleport\TelelocationTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
