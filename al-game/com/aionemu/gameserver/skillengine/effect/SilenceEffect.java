package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.SkillType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SilenceEffect")
public class SilenceEffect extends EffectTemplate {
  public void applyEffect(Effect effect) {
    effect.addToEffectedController();
  }

  public void calculate(Effect effect) {
    if (calculateEffectResistRate(effect, StatEnum.SILENCE_RESISTANCE)) {
      effect.addSucessEffect(this);
    }
  }

  public void startEffect(Effect effect) {
    Creature effected = effect.getEffected();
    effected.getEffectController().setAbnormal(EffectId.SILENCE.getEffectId());
    if (effected.getCastingSkill() != null
        && effected.getCastingSkill().getSkillTemplate().getType() == SkillType.MAGICAL) {
      effected.getController().cancelCurrentSkill();
    }
  }

  public void endEffect(Effect effect) {
    effect.getEffected().getEffectController().unsetAbnormal(EffectId.SILENCE.getEffectId());
  }
}
