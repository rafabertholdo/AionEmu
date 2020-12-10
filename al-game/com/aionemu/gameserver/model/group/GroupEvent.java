package com.aionemu.gameserver.model.group;

public enum GroupEvent {
  LEAVE(0), MOVEMENT(1), ENTER(13), UPDATE(13), CHANGELEADER(13);

  private int id;

  GroupEvent(int id) {
    this.id = id;
  }

  public int getId() {
    return this.id;
  }
}
