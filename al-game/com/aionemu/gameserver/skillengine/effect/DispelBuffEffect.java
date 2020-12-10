package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.SkillTargetSlot;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DispelBuffEffect")
public class DispelBuffEffect extends EffectTemplate {
  @XmlAttribute
  protected int count;

  public void applyEffect(Effect effect) {
    effect.getEffected().getEffectController().removeEffectByTargetSlot(SkillTargetSlot.BUFF, this.count);
  }

  public void calculate(Effect effect) {
    effect.addSucessEffect(this);
  }
}
