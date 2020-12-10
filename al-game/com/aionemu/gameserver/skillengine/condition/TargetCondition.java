package com.aionemu.gameserver.skillengine.condition;

import com.aionemu.gameserver.skillengine.model.Skill;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TargetCondition")
public class TargetCondition extends Condition {
  @XmlAttribute(required = true)
  protected TargetAttribute value;

  public TargetAttribute getValue() {
    return this.value;
  }

  public boolean verify(Skill skill) {
    if (this.value != TargetAttribute.NONE && skill.getFirstTarget() == null) {
      return false;
    }
    switch (this.value) {

      case NPC:
        return skill.getFirstTarget() instanceof com.aionemu.gameserver.model.gameobjects.Npc;
      case PC:
        return skill.getFirstTarget() instanceof com.aionemu.gameserver.model.gameobjects.player.Player;
    }
    return false;
  }
}
