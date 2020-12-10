package com.aionemu.gameserver.skillengine.action;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.skillengine.model.Skill;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MpUseAction")
public class MpUseAction extends Action {
  @XmlAttribute(required = true)
  protected int value;
  @XmlAttribute
  protected int delta;

  public void act(Skill skill) {
    Creature effector = skill.getEffector();
    int valueWithDelta = this.value + this.delta * skill.getSkillLevel();
    int changeMpPercent = skill.getChangeMpConsumption();
    if (changeMpPercent != 0) {
      valueWithDelta += valueWithDelta / 100 / changeMpPercent;
    }
    effector.getLifeStats().reduceMp(valueWithDelta);
  }
}
