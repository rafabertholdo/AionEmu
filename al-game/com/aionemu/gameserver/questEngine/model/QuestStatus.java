package com.aionemu.gameserver.questEngine.model;

import javax.xml.bind.annotation.XmlEnum;





















@XmlEnum
public enum QuestStatus
{
  NONE(0),
  START(3),
  REWARD(4),
  COMPLETE(5),
  LOCKED(6);
  
  private int id;

  
  QuestStatus(int id) {
    this.id = id;
  }

  
  public int value() {
    return this.id;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\model\QuestStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
