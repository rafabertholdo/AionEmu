package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.controllers.movement.ActionObserver;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.Skill;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChangeMpConsumptionEffect")
public class ChangeMpConsumptionEffect extends BufEffect {
  @XmlAttribute
  protected boolean percent;
  @XmlAttribute
  protected int value;

  public void startEffect(Effect effect) {
    super.startEffect(effect);

    ActionObserver observer = new ActionObserver(ActionObserver.ObserverType.SKILLUSE) {

      public void skilluse(Skill skill) {
        skill.setChangeMpConsumption(ChangeMpConsumptionEffect.this.value);
      }
    };

    effect.getEffected().getObserveController().addObserver(observer);
    effect.setActionObserver(observer, this.position);
  }

  public void endEffect(Effect effect) {
    super.endEffect(effect);
    ActionObserver observer = effect.getActionObserver(this.position);
    effect.getEffected().getObserveController().removeObserver(observer);
  }

  public void calculate(Effect effect) {
    super.calculate(effect);
  }
}
