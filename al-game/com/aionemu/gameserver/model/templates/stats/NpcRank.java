package com.aionemu.gameserver.model.templates.stats;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "rank")
@XmlEnum
public enum NpcRank {
  NORMAL, ELITE, JUNK, HERO, LEGENDARY;
}
