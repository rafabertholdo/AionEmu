package com.aionemu.gameserver.skillengine.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "HealType")
@XmlEnum
public enum HealType {
  HP, MP, DP, FP;
}
