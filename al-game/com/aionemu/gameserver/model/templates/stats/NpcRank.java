package com.aionemu.gameserver.model.templates.stats;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;





















@XmlType(name = "rank")
@XmlEnum
public enum NpcRank
{
  NORMAL,
  ELITE,
  JUNK,
  HERO,
  LEGENDARY;
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\stats\NpcRank.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
