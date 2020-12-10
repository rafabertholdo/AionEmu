package com.aionemu.gameserver.skillengine.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;






















@XmlType(name = "TargetSlot")
@XmlEnum
public enum SkillTargetSlot
{
  BUFF,
  DEBUFF,
  CHANT,
  SPEC,
  SPEC2,
  BOOST,
  NOSHOW,
  NONE;
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\model\SkillTargetSlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
