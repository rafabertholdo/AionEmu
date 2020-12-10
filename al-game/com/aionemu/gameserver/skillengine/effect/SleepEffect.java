package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.controllers.movement.ActionObserver;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.skillengine.model.Effect;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SleepEffect")
public class SleepEffect
  extends EffectTemplate
{
  public void applyEffect(Effect effect) {
    effect.addToEffectedController();
  }


  
  public void calculate(Effect effect) {
    if (calculateEffectResistRate(effect, StatEnum.SLEEP_RESISTANCE)) {
      effect.addSucessEffect(this);
    }
  }

  
  public void startEffect(final Effect effect) {
    final Creature effected = effect.getEffected();
    effected.getController().cancelCurrentSkill();
    effected.getEffectController().setAbnormal(EffectId.SLEEP.getEffectId());
    
    effected.getObserveController().attach(new ActionObserver(ActionObserver.ObserverType.ATTACKED)
        {

          
          public void attacked(Creature creature)
          {
            effected.getEffectController().removeEffect(effect.getSkillId());
          }
        });
  }



  
  public void endEffect(Effect effect) {
    effect.getEffected().getEffectController().unsetAbnormal(EffectId.SLEEP.getEffectId());
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\SleepEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
