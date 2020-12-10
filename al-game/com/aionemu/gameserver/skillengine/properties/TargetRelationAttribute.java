package com.aionemu.gameserver.skillengine.properties;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;






















@XmlType(name = "TargetRelationAttribute")
@XmlEnum
public enum TargetRelationAttribute
{
  NONE,
  ENEMY,
  MYPARTY,
  ALL,
  FRIEND;
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\properties\TargetRelationAttribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
