package com.aionemu.gameserver.skillengine.properties;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;























































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Properties")
public class Properties
{
  @XmlElements({@XmlElement(name = "firsttarget", type = FirstTargetProperty.class), @XmlElement(name = "targetrange", type = TargetRangeProperty.class), @XmlElement(name = "addweaponrange", type = AddWeaponRangeProperty.class), @XmlElement(name = "targetrelation", type = TargetRelationProperty.class), @XmlElement(name = "firsttargetrange", type = FirstTargetRangeProperty.class)})
  protected List<Property> properties;
  
  public List<Property> getProperties() {
    if (this.properties == null)
    {
      this.properties = new ArrayList<Property>();
    }
    
    return this.properties;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\properties\Properties.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
