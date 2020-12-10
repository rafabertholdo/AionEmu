package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
import com.aionemu.gameserver.skillengine.action.DamageType;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DelayDamageEffect")
public class DelayDamageEffect
  extends DamageEffect
{
  @XmlAttribute
  protected int delay;
  
  public void calculate(Effect effect) {
    calculate(effect, DamageType.MAGICAL);
  }


  
  public void applyEffect(final Effect effect) {
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            effect.getEffected().getController().onAttack(effect.getEffector(), effect.getSkillId(), SM_ATTACK_STATUS.TYPE.REGULAR, effect.getReserved1());
          }
        }this.delay);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\DelayDamageEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
