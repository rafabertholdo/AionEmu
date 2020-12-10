package com.aionemu.gameserver.model.templates.teleport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;





















@XmlRootElement(name = "telelocation")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeleportLocation
{
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


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\teleport\TeleportLocation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
