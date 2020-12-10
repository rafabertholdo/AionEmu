package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.skillengine.model.Effect;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatdownEffect")
public class StatdownEffect extends BufEffect {
  public void calculate(Effect effect) {
    if (calculateEffectResistRate(effect, null))
      effect.addSucessEffect(this);
  }
}
