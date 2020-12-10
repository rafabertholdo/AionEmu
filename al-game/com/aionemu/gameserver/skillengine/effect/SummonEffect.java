package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.skillengine.model.Effect;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SummonEffect")
public class SummonEffect extends EffectTemplate {
  @XmlAttribute(name = "npc_id", required = true)
  protected int npcId;

  public void applyEffect(Effect effect) {
    Creature effected = effect.getEffected();
    effected.getController().createSummon(this.npcId, effect.getSkillLevel());
  }

  public void calculate(Effect effect) {
    if (effect.getEffected() instanceof com.aionemu.gameserver.model.gameobjects.player.Player)
      effect.addSucessEffect(this);
  }
}
