package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
import com.aionemu.gameserver.skillengine.model.Effect;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemHealMpEffect")
public class ItemHealMpEffect extends AbstractHealEffect {
  public void applyEffect(Effect effect) {
    effect.getEffected().getLifeStats().increaseMp(SM_ATTACK_STATUS.TYPE.NATURAL_MP, -effect.getReserved1());
  }

  public void calculate(Effect effect) {
    super.calculate(effect);
    effect.addSucessEffect(this);
  }

  protected int getCurrentStatValue(Effect effect) {
    return ((Player) effect.getEffected()).getLifeStats().getCurrentMp();
  }

  protected int getMaxStatValue(Effect effect) {
    return effect.getEffected().getGameStats().getCurrentStat(StatEnum.MAXMP);
  }
}
