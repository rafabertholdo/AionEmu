package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.Creature;
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
@XmlType(name = "DamageOverTimeEffect")
public class DamageOverTimeEffect extends DamageEffect {
  @XmlAttribute(required = true)
  protected int checktime;

  public void calculate(Effect effect) {
    if (calculateEffectResistRate(effect, null)) {
      effect.addSucessEffect(this);
    }
  }

  public void applyEffect(Effect effect) {
    effect.addToEffectedController();
  }

  public void endEffect(Effect effect) {
  }

  public void onPeriodicAction(Effect effect) {
    Creature effected = effect.getEffected();
    Creature effector = effect.getEffector();
    int valueWithDelta = this.value + this.delta * effect.getSkillLevel();
    int damage = StatFunctions.calculateMagicDamageToTarget(effector, effected, valueWithDelta, getElement());
    effected.getController().onAttack(effector, effect.getSkillId(), SM_ATTACK_STATUS.TYPE.DAMAGE, damage);
  }

  public void startEffect(final Effect effect) {
    Future<?> task = ThreadPoolManager.getInstance().scheduleEffectAtFixedRate(new Runnable() {

      public void run() {
        DamageOverTimeEffect.this.onPeriodicAction(effect);
      }
    }, this.checktime, this.checktime);
    effect.setPeriodicTask(task, this.position);
  }
}
