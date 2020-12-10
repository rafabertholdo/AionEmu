package com.aionemu.gameserver.model.templates.teleport;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "locations")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeleLocIdData {
  @XmlElement(name = "telelocation")
  private List<TeleportLocation> locids;

  public List<TeleportLocation> getTelelocations() {
    return this.locids;
  }

  public TeleportLocation getTeleportLocation(int value) {
    for (TeleportLocation t : this.locids) {

      if (t != null && t.getLocId() == value) {
        return t;
      }
    }
    return null;
  }
}
