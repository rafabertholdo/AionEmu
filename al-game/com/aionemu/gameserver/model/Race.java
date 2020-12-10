package com.aionemu.gameserver.model;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum Race {
  ELYOS(0),

  ASMODIANS(1),

  LYCAN(2), CONSTRUCT(3), CARRIER(4), DRAKAN(5), LIZARDMAN(6), TELEPORTER(7), NAGA(8), BROWNIE(9), KRALL(10),
  SHULACK(11), BARRIER(12), PC_LIGHT_CASTLE_DOOR(13), PC_DARK_CASTLE_DOOR(14), DRAGON_CASTLE_DOOR(15), GCHIEF_LIGHT(16),
  GCHIEF_DARK(17), DRAGON(18), OUTSIDER(19), RATMAN(20), DEMIHUMANOID(21), UNDEAD(22), BEAST(23), MAGICALMONSTER(24),
  ELEMENTAL(25),

  NONE(26), PC_ALL(27);

  private int raceId;

  Race(int raceId) {
    this.raceId = raceId;
  }

  public int getRaceId() {
    return this.raceId;
  }
}
