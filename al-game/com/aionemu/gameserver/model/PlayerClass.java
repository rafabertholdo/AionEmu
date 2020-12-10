package com.aionemu.gameserver.model;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum PlayerClass {
  WARRIOR(0, true), GLADIATOR(1), TEMPLAR(2), SCOUT(3, true), ASSASSIN(4), RANGER(5), MAGE(6, true), SORCERER(7),
  SPIRIT_MASTER(8), PRIEST(9, true), CLERIC(10), CHANTER(11);

  private byte classId;

  private int idMask;

  private boolean startingClass;

  PlayerClass(int classId, boolean startingClass) {
    this.classId = (byte) classId;
    this.startingClass = startingClass;
    this.idMask = (int) Math.pow(2.0D, classId);
  }

  public byte getClassId() {
    return this.classId;
  }

  public static PlayerClass getPlayerClassById(byte classId) {
    for (PlayerClass pc : values()) {

      if (pc.getClassId() == classId) {
        return pc;
      }
    }
    throw new IllegalArgumentException("There is no player class with id " + classId);
  }

  public boolean isStartingClass() {
    return this.startingClass;
  }

  public static PlayerClass getStartingClassFor(PlayerClass pc) {
    switch (pc) {

      case ASSASSIN:
      case RANGER:
        return SCOUT;
      case GLADIATOR:
      case TEMPLAR:
        return WARRIOR;
      case CHANTER:
      case CLERIC:
        return PRIEST;
      case SORCERER:
      case SPIRIT_MASTER:
        return MAGE;
    }
    throw new IllegalArgumentException("Given player class is starting class: " + pc);
  }

  public int getMask() {
    return this.idMask;
  }
}
