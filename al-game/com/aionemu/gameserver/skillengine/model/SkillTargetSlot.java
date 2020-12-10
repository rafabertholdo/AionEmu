package com.aionemu.gameserver.skillengine.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TargetSlot")
@XmlEnum
public enum SkillTargetSlot {
  BUFF, DEBUFF, CHANT, SPEC, SPEC2, BOOST, NOSHOW, NONE;
}
