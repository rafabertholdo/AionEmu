package com.aionemu.gameserver.skillengine.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "HopType")
@XmlEnum
public enum HopType {
  DAMAGE, SKILLLV;
}
