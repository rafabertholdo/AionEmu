package com.aionemu.gameserver.model.gameobjects.state;

public enum CreatureSeeState {
  NORMAL(0), SEARCH1(1), SEARCH2(2);

  private int id;

  CreatureSeeState(int id) {
    this.id = id;
  }

  public int getId() {
    return this.id;
  }
}
