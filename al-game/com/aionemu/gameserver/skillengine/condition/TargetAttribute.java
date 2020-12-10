package com.aionemu.gameserver.skillengine.condition;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;






















@XmlType(name = "TargetAttribute")
@XmlEnum
public enum TargetAttribute
{
  NPC,
  PC,
  ALL,
  SELF,
  NONE;

  
  public String value() {
    return name();
  }

  
  public static TargetAttribute fromValue(String v) {
    return valueOf(v);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\condition\TargetAttribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
