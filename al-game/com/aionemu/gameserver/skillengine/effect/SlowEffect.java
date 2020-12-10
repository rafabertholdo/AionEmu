package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.skillengine.model.Effect;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SlowEffect")
public class SlowEffect extends BufEffect {
  public void applyEffect(Effect effect) {
    effect.addToEffectedController();
  }

  public void calculate(Effect effect) {
    if (calculateEffectResistRate(effect, StatEnum.SLOW_RESISTANCE)) {
      effect.addSucessEffect(this);
    }
  }

  public void startEffect(Effect effect) {
    super.startEffect(effect);
    effect.getEffected().getEffectController().setAbnormal(EffectId.SLOW.getEffectId());
  }

  public void endEffect(Effect effect) {
    super.endEffect(effect);
    effect.getEffected().getEffectController().unsetAbnormal(EffectId.SLOW.getEffectId());
  }
}
