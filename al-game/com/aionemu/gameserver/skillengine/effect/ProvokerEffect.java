package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.controllers.movement.ActionObserver;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.ProvokeTarget;
import com.aionemu.gameserver.skillengine.model.ProvokeType;
import com.aionemu.gameserver.skillengine.model.SkillTemplate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProvokerEffect")
public class ProvokerEffect extends EffectTemplate {
  @XmlAttribute
  protected int prob2;
  @XmlAttribute
  protected int prob1;
  @XmlAttribute(name = "provoke_target")
  protected ProvokeTarget provokeTarget;
  @XmlAttribute(name = "provoke_type")
  protected ProvokeType provokeType;
  @XmlAttribute(name = "skill_id")
  protected int skillId;

  public void applyEffect(Effect effect) {
    effect.addToEffectedController();
  }

  public void calculate(Effect effect) {
    effect.addSucessEffect(this);
  }

  public void startEffect(Effect effect) {
    ActionObserver observer = null;
    final Creature effector = effect.getEffector();
    switch (this.provokeType) {

      case ME:
        observer = new ActionObserver(ActionObserver.ObserverType.ATTACK) {

          public void attack(Creature creature) {
            if (Rnd.get(0, 100) <= ProvokerEffect.this.prob2) {

              Creature target = ProvokerEffect.this.getProvokeTarget(ProvokerEffect.this.provokeTarget, effector,
                  creature);
              ProvokerEffect.this.createProvokedEffect(effector, target);
            }
          }
        };
        break;

      case OPPONENT:
        observer = new ActionObserver(ActionObserver.ObserverType.ATTACKED) {

          public void attacked(Creature creature) {
            if (Rnd.get(0, 100) <= ProvokerEffect.this.prob2) {

              Creature target = ProvokerEffect.this.getProvokeTarget(ProvokerEffect.this.provokeTarget, effector,
                  creature);
              ProvokerEffect.this.createProvokedEffect(effector, target);
            }
          }
        };
        break;
    }

    if (observer == null) {
      return;
    }
    effect.setActionObserver(observer, this.position);
    effect.getEffected().getObserveController().addObserver(observer);
  }

  private void createProvokedEffect(Creature effector, Creature target) {
    SkillTemplate template = DataManager.SKILL_DATA.getSkillTemplate(this.skillId);
    Effect e = new Effect(effector, target, template, template.getLvl(), template.getEffectsDuration());
    e.initialize();
    e.applyEffect();
  }

  private Creature getProvokeTarget(ProvokeTarget provokeTarget, Creature effector, Creature target) {
    switch (provokeTarget) {

      case ME:
        return effector;
      case OPPONENT:
        return target;
    }
    throw new IllegalArgumentException("Provoker target is invalid " + provokeTarget);
  }

  public void endEffect(Effect effect) {
    ActionObserver observer = effect.getActionObserver(this.position);
    if (observer != null)
      effect.getEffected().getObserveController().removeObserver(observer);
  }
}
