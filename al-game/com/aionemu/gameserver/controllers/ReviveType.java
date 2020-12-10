package com.aionemu.gameserver.controllers;

public enum ReviveType {
  BIND_REVIVE(0),

  REBIRTH_REVIVE(1),

  ITEM_SELF_REVIVE(2),

  SKILL_REVIVE(3),

  KISK_REVIVE(4);

  private int typeId;

  ReviveType(int typeId) {
    this.typeId = typeId;
  }

  public int getReviveTypeId() {
    return this.typeId;
  }

  public static ReviveType getReviveTypeById(int id) {
    for (ReviveType rt : values()) {

      if (rt.typeId == id)
        return rt;
    }
    throw new IllegalArgumentException("Unsupported revive type: " + id);
  }
}
