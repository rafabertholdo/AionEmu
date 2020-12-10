package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.SpellStatus;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CloseAerialEffect")
public class CloseAerialEffect extends EffectTemplate {
  public void applyEffect(Effect effect) {
    effect.getEffected().getEffectController().removeEffectByEffectId(8224);
  }

  public void calculate(Effect effect) {
    effect.addSucessEffect(this);
    effect.setSpellStatus(SpellStatus.CLOSEAERIAL);
  }
}
