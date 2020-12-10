package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.skillengine.action.DamageType;
import com.aionemu.gameserver.skillengine.model.Effect;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SkillAttackEffect")
public class SkillAttackEffect extends DamageEffect {
  public void calculate(Effect effect) {
    calculate(effect, DamageType.PHYSICAL);
  }
}
