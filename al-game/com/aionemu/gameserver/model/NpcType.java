package com.aionemu.gameserver.model;

public enum NpcType {
  ATTACKABLE(0),

  AGGRESSIVE(8),

  NON_ATTACKABLE(38),

  RESURRECT(38),

  POSTBOX(38),

  USEITEM(38),

  PORTAL(38),

  ARTIFACT(38),

  ARTIFACT_PROTECTOR(0);

  private int someClientSideId;

  NpcType(int id) {
    this.someClientSideId = id;
  }

  public int getId() {
    return this.someClientSideId;
  }
}
