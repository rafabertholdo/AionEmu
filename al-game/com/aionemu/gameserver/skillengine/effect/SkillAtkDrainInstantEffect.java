package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
import com.aionemu.gameserver.skillengine.action.DamageType;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.HealType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SkillAtkDrainInstantEffect")
public class SkillAtkDrainInstantEffect extends DamageEffect {
  @XmlAttribute
  protected int percent;
  @XmlAttribute(name = "heal_type")
  protected HealType healType;

  public void applyEffect(Effect effect) {
    super.applyEffect(effect);
    int value = effect.getReserved1() * this.percent / 100;
    switch (this.healType) {

      case HP:
        effect.getEffector().getLifeStats().increaseHp(SM_ATTACK_STATUS.TYPE.NATURAL_HP, value);
        break;
      case MP:
        effect.getEffector().getLifeStats().increaseMp(SM_ATTACK_STATUS.TYPE.NATURAL_MP, value);
        break;
    }
  }

  public void calculate(Effect effect) {
    calculate(effect, DamageType.PHYSICAL);
  }
}
