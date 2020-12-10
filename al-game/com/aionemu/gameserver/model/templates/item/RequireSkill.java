package com.aionemu.gameserver.model.templates.item;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequireSkill", propOrder = { "skillId" })
public class RequireSkill {
  @XmlElement(type = Integer.class)
  protected List<Integer> skillId;
  @XmlAttribute
  protected Integer skilllvl;

  public List<Integer> getSkillId() {
    if (this.skillId == null) {
      this.skillId = new ArrayList<Integer>();
    }
    return this.skillId;
  }

  public Integer getSkilllvl() {
    return this.skilllvl;
  }
}
