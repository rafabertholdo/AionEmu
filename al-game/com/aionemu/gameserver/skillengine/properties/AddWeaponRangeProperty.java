package com.aionemu.gameserver.skillengine.properties;

import com.aionemu.gameserver.skillengine.model.Skill;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddWeaponRangeProperty")
public class AddWeaponRangeProperty extends Property {
  public boolean set(Skill skill) {
    return true;
  }
}
