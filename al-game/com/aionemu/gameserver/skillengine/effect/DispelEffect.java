package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.skillengine.model.DispelType;
import com.aionemu.gameserver.skillengine.model.Effect;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class DispelEffect extends EffectTemplate {
  @XmlElement(type = Integer.class)
  protected List<Integer> effectids;
  @XmlAttribute
  protected DispelType dispeltype;
  @XmlAttribute
  protected Integer value;

  public void applyEffect(Effect effect) {
    if (this.effectids == null) {
      return;
    }
    switch (this.dispeltype) {

      case EFFECTID:
        for (Integer effectId : this.effectids) {
          effect.getEffected().getEffectController().removeEffectByEffectId(effectId.intValue());
        }
        break;
    }
  }

  public void calculate(Effect effect) {
    effect.addSucessEffect(this);
  }
}
