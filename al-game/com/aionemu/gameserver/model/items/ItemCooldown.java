package com.aionemu.gameserver.model.items;

public class ItemCooldown {
  private long time;
  private int useDelay;

  public ItemCooldown(long time, int useDelay) {
    this.time = time;
    this.useDelay = useDelay;
  }

  public long getReuseTime() {
    return this.time;
  }

  public int getUseDelay() {
    return this.useDelay;
  }
}
