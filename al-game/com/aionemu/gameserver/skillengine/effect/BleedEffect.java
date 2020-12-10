package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.stats.StatFunctions;
import java.util.concurrent.Future;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BleedEffect")
public class BleedEffect
  extends EffectTemplate
{
  @XmlAttribute(required = true)
  protected int checktime;
  @XmlAttribute
  protected int value;
  @XmlAttribute
  protected int delta;
  
  public void applyEffect(Effect effect) {
    effect.addToEffectedController();
  }


  
  public void calculate(Effect effect) {
    if (calculateEffectResistRate(effect, StatEnum.BLEED_RESISTANCE)) {
      effect.addSucessEffect(this);
    }
  }

  
  public void endEffect(Effect effect) {
    Creature effected = effect.getEffected();
    effected.getEffectController().unsetAbnormal(EffectId.BLEED.getEffectId());
  }


  
  public void onPeriodicAction(Effect effect) {
    Creature effected = effect.getEffected();
    Creature effector = effect.getEffector();
    int valueWithDelta = this.value + this.delta * effect.getSkillLevel();
    int damage = StatFunctions.calculateMagicDamageToTarget(effector, effected, valueWithDelta, getElement());
    effected.getController().onAttack(effector, effect.getSkillId(), SM_ATTACK_STATUS.TYPE.DAMAGE, damage);
  }


  
  public void startEffect(final Effect effect) {
    Creature effected = effect.getEffected();
    
    effected.getEffectController().setAbnormal(EffectId.BLEED.getEffectId());
    
    Future<?> task = ThreadPoolManager.getInstance().scheduleEffectAtFixedRate(new Runnable()
        {
          
          public void run()
          {
            BleedEffect.this.onPeriodicAction(effect);
          }
        },  this.checktime, this.checktime);
    effect.setPeriodicTask(task, this.position);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\BleedEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
