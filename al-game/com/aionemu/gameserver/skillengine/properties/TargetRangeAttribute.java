package com.aionemu.gameserver.skillengine.properties;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TargetRangeAttribute")
@XmlEnum
public enum TargetRangeAttribute {
  NONE, ONLYONE, PARTY, AREA, PARTY_WITHPET, POINT;
}
