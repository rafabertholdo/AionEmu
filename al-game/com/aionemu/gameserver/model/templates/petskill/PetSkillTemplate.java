package com.aionemu.gameserver.model.templates.petskill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pet_skill")
public class PetSkillTemplate {
  @XmlAttribute(name = "skill_id")
  protected int skillId;
  @XmlAttribute(name = "pet_id")
  protected int petId;
  @XmlAttribute(name = "order_skill")
  protected int orderSkill;

  public int getSkillId() {
    return this.skillId;
  }

  public int getPetId() {
    return this.petId;
  }

  public int getOrderSkill() {
    return this.orderSkill;
  }
}
