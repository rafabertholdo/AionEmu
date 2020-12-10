package com.aionemu.gameserver.model.siege;

public enum SiegeRace {
  ELYOS(0), ASMODIANS(1), BALAUR(2);

  private int raceId;

  SiegeRace(int id) {
    this.raceId = id;
  }

  public int getRaceId() {
    return this.raceId;
  }
}
