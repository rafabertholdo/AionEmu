package com.aionemu.gameserver.model.group;

public enum LootRuleType {
  FREEFORALL(0), ROUNDROBIN(1), LEADER(2);

  private int id;

  LootRuleType(int id) {
    this.id = id;
  }

  public int getId() {
    return this.id;
  }
}
