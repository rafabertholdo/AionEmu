package com.aionemu.gameserver.model.gameobjects.stats;

public class Stat {
  private StatEnum type;
  private int origin;
  private int base;
  private int bonus;
  private int old;

  public Stat(StatEnum type, int origin) {
    this.type = type;
    this.origin = origin;
    this.base = origin;
    this.bonus = 0;
    this.old = 0;
  }

  public Stat(StatEnum type) {
    this(type, 0);
  }

  public StatEnum getType() {
    return this.type;
  }

  public void increase(int amount, boolean bonus) {
    if (bonus) {

      this.bonus += amount;
    } else {

      this.base = amount;
    }
  }

  public void set(int value, boolean bonus) {
    if (bonus) {

      this.bonus = value;
    } else {

      this.base = value;
    }
  }

  public int getOrigin() {
    return this.origin;
  }

  public int getBase() {
    return this.base;
  }

  public int getBonus() {
    return this.bonus;
  }

  public int getCurrent() {
    return this.base + this.bonus;
  }

  public int getOld() {
    return this.old;
  }

  public void reset() {
    this.old = this.base + this.bonus;
    this.base = this.origin;
    this.bonus = 0;
  }

  public String toString() {
    String s = this.type + ":" + this.base + "+" + this.bonus;
    return s;
  }
}
