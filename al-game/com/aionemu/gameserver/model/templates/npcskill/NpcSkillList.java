package com.aionemu.gameserver.model.templates.npcskill;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "npcskills")
public class NpcSkillList {
  @XmlAttribute(name = "npcid")
  protected int npcId;
  @XmlAttribute(name = "skill_count")
  protected int count;
  @XmlElement(name = "npcskill")
  protected List<NpcSkillTemplate> npcSkills;

  public int getNpcId() {
    return this.npcId;
  }

  public int getCount() {
    return this.count;
  }

  public List<NpcSkillTemplate> getNpcSkills() {
    return this.npcSkills;
  }
}
