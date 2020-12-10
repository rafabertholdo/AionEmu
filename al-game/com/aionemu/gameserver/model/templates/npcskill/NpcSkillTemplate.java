package com.aionemu.gameserver.model.templates.npcskill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "npcskill")
public class NpcSkillTemplate {
  @XmlAttribute(name = "id")
  protected int id;
  @XmlAttribute(name = "skillid")
  protected int skillid;
  @XmlAttribute(name = "skilllevel")
  protected int skilllevel;
  @XmlAttribute(name = "probability")
  protected int probability;
  @XmlAttribute(name = "abouthp")
  protected boolean abouthp;

  public int getId() {
    return this.id;
  }

  public int getSkillid() {
    return this.skillid;
  }

  public int getSkillLevel() {
    return this.skilllevel;
  }

  public int getProbability() {
    return this.probability;
  }

  public boolean isAbooutHp() {
    return this.abouthp;
  }
}
