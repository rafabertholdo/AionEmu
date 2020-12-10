package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.stats.CreatureLifeStats;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
import com.aionemu.gameserver.skillengine.model.Effect;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SwitchHpMpEffect")
public class SwitchHpMpEffect
  extends EffectTemplate
{
  public void applyEffect(Effect effect) {
    CreatureLifeStats<? extends Creature> lifeStats = effect.getEffected().getLifeStats();
    int currentHp = lifeStats.getCurrentHp();
    int currentMp = lifeStats.getCurrentMp();
    
    lifeStats.increaseHp(SM_ATTACK_STATUS.TYPE.NATURAL_HP, currentMp - currentHp);
    lifeStats.increaseMp(SM_ATTACK_STATUS.TYPE.NATURAL_MP, currentHp - currentMp);
  }


  
  public void calculate(Effect effect) {
    effect.addSucessEffect(this);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\SwitchHpMpEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
