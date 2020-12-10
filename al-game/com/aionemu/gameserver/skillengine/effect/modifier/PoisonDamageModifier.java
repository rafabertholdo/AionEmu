package com.aionemu.gameserver.skillengine.effect.modifier;

import com.aionemu.gameserver.skillengine.effect.EffectId;
import com.aionemu.gameserver.skillengine.model.Effect;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PoisonDamageModifier")
public class PoisonDamageModifier extends ActionModifier {
  @XmlAttribute(required = true)
  protected int delta;
  @XmlAttribute(required = true)
  protected int value;

  public int analyze(Effect effect, int originalValue) {
    return originalValue + this.value + effect.getSkillLevel() * this.delta;
  }

  public boolean check(Effect effect) {
    return effect.getEffected().getEffectController().isAbnoramlSet(EffectId.POISON);
  }
}
