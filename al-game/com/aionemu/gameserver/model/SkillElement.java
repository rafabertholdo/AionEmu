package com.aionemu.gameserver.model;

public enum SkillElement {
  NONE(0), FIRE(1), WATER(2), WIND(3), EARTH(4);

  private int element;

  SkillElement(int id) {
    this.element = id;
  }

  public int getElementId() {
    return this.element;
  }
}
