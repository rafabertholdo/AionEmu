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
public class Properties {
  @XmlElements({ @XmlElement(name = "firsttarget", type = FirstTargetProperty.class),
      @XmlElement(name = "targetrange", type = TargetRangeProperty.class),
      @XmlElement(name = "addweaponrange", type = AddWeaponRangeProperty.class),
      @XmlElement(name = "targetrelation", type = TargetRelationProperty.class),
      @XmlElement(name = "firsttargetrange", type = FirstTargetRangeProperty.class) })
  protected List<Property> properties;

  public List<Property> getProperties() {
    if (this.properties == null) {
      this.properties = new ArrayList<Property>();
    }

    return this.properties;
  }
}
