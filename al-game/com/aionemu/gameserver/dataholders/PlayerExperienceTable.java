package com.aionemu.gameserver.dataholders;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "player_experience_table")
@XmlAccessorType(XmlAccessType.NONE)
public class PlayerExperienceTable {
  @XmlElement(name = "exp")
  private long[] experience;

  public long getStartExpForLevel(int level) {
    if (level > this.experience.length) {
      throw new IllegalArgumentException("The given level is higher than possible max");
    }
    return (level > this.experience.length) ? 0L : this.experience[level - 1];
  }

  public int getMaxLevel() {
    return (this.experience == null) ? 0 : this.experience.length;
  }
}
