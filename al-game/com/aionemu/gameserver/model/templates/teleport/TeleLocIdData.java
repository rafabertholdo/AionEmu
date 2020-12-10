package com.aionemu.gameserver.model.templates.teleport;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


























@XmlRootElement(name = "locations")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeleLocIdData
{
  @XmlElement(name = "telelocation")
  private List<TeleportLocation> locids;
  
  public List<TeleportLocation> getTelelocations() {
    return this.locids;
  }

  
  public TeleportLocation getTeleportLocation(int value) {
    for (TeleportLocation t : this.locids) {
      
      if (t != null && t.getLocId() == value)
      {
        return t;
      }
    } 
    return null;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\teleport\TeleLocIdData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
