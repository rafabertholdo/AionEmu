package com.aionemu.gameserver.skillengine.properties;

import com.aionemu.gameserver.skillengine.model.Skill;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;




























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddWeaponRangeProperty")
public class AddWeaponRangeProperty
  extends Property
{
  public boolean set(Skill skill) {
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\properties\AddWeaponRangeProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
