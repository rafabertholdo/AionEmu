package com.aionemu.gameserver.model.group;

public enum LootDistribution {
  NORMAL(0), ROLL_DICE(2), BID(3);

  private int id;

  LootDistribution(int id) {
    this.id = id;
  }

  public int getId() {
    return this.id;
  }
}
