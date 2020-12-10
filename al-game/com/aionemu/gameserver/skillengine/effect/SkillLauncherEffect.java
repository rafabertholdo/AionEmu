package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.skillengine.model.Effect;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SkillLauncherEffect")
public class SkillLauncherEffect extends EffectTemplate {
  @XmlAttribute(name = "skill_id")
  protected int skillId;
  @XmlAttribute
  protected int value;

  public void applyEffect(Effect effect) {
  }

  public void calculate(Effect effect) {
    effect.addSucessEffect(this);
  }
}
