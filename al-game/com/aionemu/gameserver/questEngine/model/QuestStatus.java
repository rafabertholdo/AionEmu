package com.aionemu.gameserver.questEngine.model;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum QuestStatus {
  NONE(0), START(3), REWARD(4), COMPLETE(5), LOCKED(6);

  private int id;

  QuestStatus(int id) {
    this.id = id;
  }

  public int value() {
    return this.id;
  }
}
