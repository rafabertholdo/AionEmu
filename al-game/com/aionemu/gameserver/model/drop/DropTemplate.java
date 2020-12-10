package com.aionemu.gameserver.model.drop;

public class DropTemplate {
  private int mobId;
  private int itemId;
  private int min;
  private int max;
  private float chance;

  public DropTemplate(int mobId, int itemId, int min, int max, float chance) {
    this.mobId = mobId;
    this.itemId = itemId;
    this.min = min;
    this.max = max;
    this.chance = chance;
  }

  public int getMobId() {
    return this.mobId;
  }

  public int getItemId() {
    return this.itemId;
  }

  public int getMin() {
    return this.min;
  }

  public int getMax() {
    return this.max;
  }

  public float getChance() {
    return this.chance;
  }
}
