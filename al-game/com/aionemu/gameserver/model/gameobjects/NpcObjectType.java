package com.aionemu.gameserver.model.gameobjects;

public enum NpcObjectType {
  NORMAL(1), SUMMON(2), TRAP(32), SERVANT(1024);

  private int id;

  NpcObjectType(int id) {
    this.id = id;
  }

  public int getId() {
    return this.id;
  }
}
