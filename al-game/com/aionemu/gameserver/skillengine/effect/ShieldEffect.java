package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.controllers.movement.AttackCalcObserver;
import com.aionemu.gameserver.controllers.movement.AttackShieldObserver;
import com.aionemu.gameserver.skillengine.model.Effect;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShieldEffect")
public class ShieldEffect extends EffectTemplate {
  @XmlAttribute
  protected int hitdelta;
  @XmlAttribute
  protected int hitvalue;
  @XmlAttribute
  protected boolean percent;
  @XmlAttribute
  protected int delta;
  @XmlAttribute
  protected int value;

  public void applyEffect(Effect effect) {
    effect.addToEffectedController();
  }

  public void calculate(Effect effect) {
    int skillLvl = effect.getSkillLevel();
    int valueWithDelta = this.value + this.delta * skillLvl;
    int hitValueWithDelta = this.hitvalue + this.hitdelta * skillLvl;
    effect.setReserved2(valueWithDelta);
    effect.setReserved3(hitValueWithDelta);
    effect.addSucessEffect(this);
  }

  public void startEffect(Effect effect) {
    AttackShieldObserver asObserver = new AttackShieldObserver(effect.getReserved3(), effect.getReserved2(),
        this.percent, effect);

    effect.getEffected().getObserveController().addAttackCalcObserver((AttackCalcObserver) asObserver);
    effect.setAttackShieldObserver((AttackCalcObserver) asObserver, this.position);
  }

  public void endEffect(Effect effect) {
    AttackCalcObserver acObserver = effect.getAttackShieldObserver(this.position);
    if (acObserver != null)
      effect.getEffected().getObserveController().removeAttackCalcObserver(acObserver);
  }
}
