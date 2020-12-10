package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.skillengine.model.Effect;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HealFpEffect")
public class HealFpEffect extends AbstractHealEffect {
  public void applyEffect(Effect effect) {
    effect.getEffected().getLifeStats().increaseFp(-effect.getReserved1());
  }

  public void calculate(Effect effect) {
    super.calculate(effect);
    effect.addSucessEffect(this);
  }

  protected int getCurrentStatValue(Effect effect) {
    return effect.getEffected().getLifeStats().getCurrentFp();
  }

  protected int getMaxStatValue(Effect effect) {
    return effect.getEffected().getGameStats().getCurrentStat(StatEnum.FLY_TIME);
  }
}
