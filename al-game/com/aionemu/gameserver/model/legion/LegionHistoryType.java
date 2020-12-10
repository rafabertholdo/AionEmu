package com.aionemu.gameserver.model.legion;

public enum LegionHistoryType {
  CREATE(0), JOIN(1), KICK(2), LEVEL_UP(3), APPOINTED(4), EMBLEM_REGISTER(5), EMBLEM_MODIFIED(6);

  private byte historyType;

  LegionHistoryType(int historyType) {
    this.historyType = (byte) historyType;
  }

  public byte getHistoryId() {
    return this.historyType;
  }
}
