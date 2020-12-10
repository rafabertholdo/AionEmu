package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.controllers.attack.AttackStatus;
import com.aionemu.gameserver.controllers.attack.AttackUtil;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
import com.aionemu.gameserver.skillengine.action.DamageType;
import com.aionemu.gameserver.skillengine.model.Effect;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DamageEffect")
public abstract class DamageEffect extends EffectTemplate {
  @XmlAttribute(required = true)
  protected int value;
  @XmlAttribute
  protected int delta;

  public void applyEffect(Effect effect) {
    effect.getEffected().getController().onAttack(effect.getEffector(), effect.getSkillId(),
        SM_ATTACK_STATUS.TYPE.REGULAR, effect.getReserved1());

    effect.getEffector().getObserveController().notifyAttackObservers(effect.getEffected());
  }

  public void calculate(Effect effect, DamageType damageType) {
    int skillLvl = effect.getSkillLevel();
    int valueWithDelta = this.value + this.delta * skillLvl;
    valueWithDelta = applyActionModifiers(effect, valueWithDelta);

    if (effect.getEffected() instanceof com.aionemu.gameserver.model.gameobjects.player.Player
        && effect.getPvpDamage() != 0) {
      valueWithDelta = Math.round(valueWithDelta * effect.getPvpDamage() / 100.0F);
    }
    switch (damageType) {

      case PHYSICAL:
        AttackUtil.calculatePhysicalSkillAttackResult(effect, valueWithDelta);
        break;
      case MAGICAL:
        AttackUtil.calculateMagicalSkillAttackResult(effect, valueWithDelta, getElement());
        break;
      default:
        AttackUtil.calculatePhysicalSkillAttackResult(effect, 0);
        break;
    }
    if (effect.getAttackStatus() != AttackStatus.RESIST && effect.getAttackStatus() != AttackStatus.DODGE)
      effect.addSucessEffect(this);
  }
}
