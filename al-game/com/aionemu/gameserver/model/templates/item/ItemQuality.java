package com.aionemu.gameserver.model.templates.item;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "quality")
@XmlEnum
public enum ItemQuality {
  COMMON, RARE, UNIQUE, LEGEND, MYTHIC, EPIC, JUNK;
}
