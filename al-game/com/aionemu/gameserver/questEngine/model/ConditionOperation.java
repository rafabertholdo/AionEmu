package com.aionemu.gameserver.questEngine.model;

import javax.xml.bind.annotation.XmlEnum;






















@XmlEnum
public enum ConditionOperation
{
  EQUAL,
  GREATER,
  GREATER_EQUAL,
  LESSER,
  LESSER_EQUAL,
  NOT_EQUAL,
  IN,
  NOT_IN;
  
  public String value() {
    return name();
  }
  
  public static ConditionOperation fromValue(String v) {
    return valueOf(v);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\model\ConditionOperation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
