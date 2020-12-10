package com.aionemu.gameserver.skillengine.condition;

import com.aionemu.gameserver.skillengine.model.Skill;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HpCondition")
public class HpCondition
  extends Condition
{
  @XmlAttribute(required = true)
  protected int value;
  @XmlAttribute
  protected int delta;
  
  public boolean verify(Skill skill) {
    int valueWithDelta = this.value + this.delta * skill.getSkillLevel();
    return (skill.getEffector().getLifeStats().getCurrentHp() > valueWithDelta);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\condition\HpCondition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
