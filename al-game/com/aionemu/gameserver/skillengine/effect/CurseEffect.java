package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.skillengine.model.Effect;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CurseEffect")
public class CurseEffect extends EffectTemplate {
  public void applyEffect(Effect effect) {
  }

  public void calculate(Effect effect) {
    if (calculateEffectResistRate(effect, StatEnum.CURSE_RESISTANCE))
      effect.addSucessEffect(this);
  }
}
