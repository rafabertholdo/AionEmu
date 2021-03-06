package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.controllers.attack.AttackStatus;
import com.aionemu.gameserver.controllers.movement.AttackCalcObserver;
import com.aionemu.gameserver.controllers.movement.AttackStatusObserver;
import com.aionemu.gameserver.skillengine.model.Effect;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AlwaysDodgeEffect")
public class AlwaysDodgeEffect extends EffectTemplate {
  @XmlAttribute
  protected int value;

  public void applyEffect(Effect effect) {
    effect.addToEffectedController();
  }

  public void calculate(Effect effect) {
    effect.addSucessEffect(this);
  }

  public void startEffect(Effect effect) {
    AttackStatusObserver attackStatusObserver = new AttackStatusObserver(this.value, AttackStatus.DODGE) {

      public boolean checkStatus(AttackStatus status) {
        if (status == AttackStatus.DODGE && this.value > 0) {

          this.value--;
          return true;
        }
        return false;
      }
    };

    effect.getEffected().getObserveController().addAttackCalcObserver((AttackCalcObserver) attackStatusObserver);
    effect.setAttackStatusObserver((AttackCalcObserver) attackStatusObserver, this.position);
  }

  public void endEffect(Effect effect) {
    AttackCalcObserver acObserver = effect.getAttackStatusObserver(this.position);
    if (acObserver != null)
      effect.getEffected().getObserveController().removeAttackCalcObserver(acObserver);
  }
}
