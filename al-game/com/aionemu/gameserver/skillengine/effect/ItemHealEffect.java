package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
import com.aionemu.gameserver.skillengine.model.Effect;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemHealEffect")
public class ItemHealEffect extends AbstractHealEffect {
  public void applyEffect(Effect effect) {
    effect.getEffected().getLifeStats().increaseHp(SM_ATTACK_STATUS.TYPE.NATURAL_HP, -effect.getReserved1());
  }

  public void calculate(Effect effect) {
    super.calculate(effect);
    effect.addSucessEffect(this);
  }
}
