package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.SkillTargetSlot;
import com.aionemu.gameserver.skillengine.model.SkillType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DispelDebuffPhysicalEffect")
public class DispelDebuffPhysicalEffect extends EffectTemplate {
  @XmlAttribute
  protected int value;

  public void applyEffect(Effect effect) {
    effect.getEffected().getEffectController().removeEffectBySkillTypeAndTargetSlot(SkillType.PHYSICAL,
        SkillTargetSlot.DEBUFF, this.value);
  }

  public void calculate(Effect effect) {
    effect.addSucessEffect(this);
  }
}
