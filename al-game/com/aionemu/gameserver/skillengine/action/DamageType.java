package com.aionemu.gameserver.skillengine.action;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;





















@XmlType(name = "DamageType")
@XmlEnum
public enum DamageType
{
  PHYSICAL,
  MAGICAL;

  
  public String value() {
    return name();
  }

  
  public static DamageType fromValue(String v) {
    return valueOf(v);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\action\DamageType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
