package com.aionemu.gameserver.skillengine.properties;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;






















@XmlType(name = "FirstTargetAttribute")
@XmlEnum
public enum FirstTargetAttribute
{
  NONE,
  TARGETORME,
  ME,
  MYPET,
  TARGET,
  PASSIVE,
  TARGET_MYPARTY_NONVISIBLE,
  POINT;
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\properties\FirstTargetAttribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
