package com.aionemu.gameserver.skillengine.condition;

import com.aionemu.gameserver.skillengine.model.Skill;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlayerMovedCondition")
public class PlayerMovedCondition extends Condition {
  @XmlAttribute(required = true)
  protected boolean allow;

  public boolean isAllow() {
    return this.allow;
  }

  public boolean verify(Skill skill) {
    return (this.allow == skill.getConditionChangeListener().isEffectorMoved());
  }
}
