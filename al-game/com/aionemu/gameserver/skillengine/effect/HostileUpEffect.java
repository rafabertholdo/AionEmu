package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.skillengine.model.Effect;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostileUpEffect")
public class HostileUpEffect extends EffectTemplate {
  @XmlAttribute
  protected int value;
  @XmlAttribute
  protected int delta;

  public void applyEffect(Effect effect) {
    Creature effected = effect.getEffected();
    if (effected instanceof Npc) {
      ((Npc) effected).getAggroList().addHate(effect.getEffector(), effect.getTauntHate());
    }
  }

  public void calculate(Effect effect) {
    effect.setTauntHate(this.value + this.delta * effect.getSkillLevel());
    effect.addSucessEffect(this);
  }
}
