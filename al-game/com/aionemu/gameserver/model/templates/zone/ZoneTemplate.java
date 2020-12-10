package com.aionemu.gameserver.model.templates.zone;

import com.aionemu.gameserver.world.zone.ZoneName;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Zone")
public class ZoneTemplate
{
  @XmlElement(required = true)
  protected Points points;
  protected List<ZoneName> link;
  @XmlAttribute
  protected int priority;
  @XmlAttribute(name = "fly")
  protected boolean flightAllowed;
  @XmlAttribute(name = "breath")
  protected boolean breath;
  @XmlAttribute
  protected ZoneName name;
  @XmlAttribute
  protected int mapid;
  
  public Points getPoints() {
    return this.points;
  }



  
  public List<ZoneName> getLink() {
    if (this.link == null) {
      this.link = new ArrayList<ZoneName>();
    }
    return this.link;
  }




  
  public int getPriority() {
    return this.priority;
  }




  
  public boolean isFlightAllowed() {
    return this.flightAllowed;
  }



  
  public ZoneName getName() {
    return this.name;
  }



  
  public int getMapid() {
    return this.mapid;
  }




  
  public boolean isBreath() {
    return this.breath;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\zone\ZoneTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
