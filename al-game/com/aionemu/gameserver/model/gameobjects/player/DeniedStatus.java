package com.aionemu.gameserver.model.gameobjects.player;

public enum DeniedStatus {
  VEIW_DETAIL(1), TRADE(2), GROUP(4), GUILD(8), FRIEND(16), DUEL(32);

  private int id;

  DeniedStatus(int id) {
    this.id = id;
  }

  public int getId() {
    return this.id;
  }
}
