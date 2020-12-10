package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.controllers.movement.ActionObserver;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.skillengine.model.SkillType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OneTimeBoostSkillAttackEffect")
public class OneTimeBoostSkillAttackEffect extends BufEffect {
  @XmlAttribute
  private int count;

  public void startEffect(final Effect effect) {
    super.startEffect(effect);

    final int stopCount = this.count;
    ActionObserver observer = new ActionObserver(ActionObserver.ObserverType.SKILLUSE) {
      private int count = 0;

      public void skilluse(Skill skill) {
        if (this.count < stopCount && skill.getSkillTemplate().getType() == SkillType.PHYSICAL) {
          this.count++;
        }
        if (this.count == stopCount) {
          effect.endEffect();
        }
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
