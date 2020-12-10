package com.aionemu.gameserver.skillengine.model.learn;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "skill")
public class SkillLearnTemplate {
  @XmlAttribute(name = "classId", required = true)
  private SkillClass classId;
  @XmlAttribute(name = "skillId", required = true)
  private int skillId;
  @XmlAttribute(name = "skillLevel", required = true)
  private int skillLevel;
  @XmlAttribute(name = "name", required = true)
  private String name;
  @XmlAttribute(name = "type", required = true)
  private SkillUsageType type;
  @XmlAttribute(name = "race", required = true)
  private SkillRace race;
  @XmlAttribute(name = "minLevel", required = true)
  private int minLevel;
  @XmlAttribute
  private boolean autolearn;
  @XmlAttribute
  private boolean stigma = false;

  public SkillClass getClassId() {
    return this.classId;
  }

  public int getSkillId() {
    return this.skillId;
  }

  public int getSkillLevel() {
    return this.skillLevel;
  }

  public String getName() {
    return this.name;
  }

  public SkillUsageType getType() {
    return this.type;
  }

  public int getMinLevel() {
    return this.minLevel;
  }

  public SkillRace getRace() {
    return this.race;
  }

  public boolean isAutolearn() {
    return this.autolearn;
  }

  public boolean isStigma() {
    return this.stigma;
  }
}
