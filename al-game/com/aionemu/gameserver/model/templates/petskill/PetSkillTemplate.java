package com.aionemu.gameserver.model.templates.petskill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pet_skill")
public class PetSkillTemplate
{
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


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\petskill\PetSkillTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
