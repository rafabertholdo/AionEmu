package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.skillengine.model.Effect;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;



























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractHealEffect")
public abstract class AbstractHealEffect
  extends EffectTemplate
{
  @XmlAttribute(required = true)
  protected int value;
  @XmlAttribute
  protected int delta;
  @XmlAttribute
  protected boolean percent;
  
  public void calculate(Effect effect) {
    int valueWithDelta = this.value + this.delta * effect.getSkillLevel();
    
    int healValue = valueWithDelta;
    if (this.percent) {
      
      int currentValue = getCurrentStatValue(effect);
      int maxValue = getMaxStatValue(effect);
      int possibleHealValue = maxValue * valueWithDelta / 100;
      healValue = (maxValue - currentValue < possibleHealValue) ? (maxValue - currentValue) : possibleHealValue;
    } 

    
    int boostHeal = effect.getEffector().getGameStats().getCurrentStat(StatEnum.BOOST_HEAL) / 10 + 100;
    effect.setReserved1(Math.round((-healValue * boostHeal) / 100.0F));
  }






  
  protected int getCurrentStatValue(Effect effect) {
    return effect.getEffected().getLifeStats().getCurrentHp();
  }






  
  protected int getMaxStatValue(Effect effect) {
    return effect.getEffected().getGameStats().getCurrentStat(StatEnum.MAXHP);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\AbstractHealEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
