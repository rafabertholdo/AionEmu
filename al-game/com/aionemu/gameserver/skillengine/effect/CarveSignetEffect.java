package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.skillengine.action.DamageType;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.SkillTemplate;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CarveSignetEffect")
public class CarveSignetEffect extends DamageEffect {
  @XmlAttribute(required = true)
  protected int signetlvl;
  @XmlAttribute(required = true)
  protected int signetid;
  @XmlAttribute(required = true)
  protected String signet;

  public void applyEffect(Effect effect) {
    super.applyEffect(effect);

    Creature effected = effect.getEffected();
    Effect placedSignet = effected.getEffectController().getAnormalEffect(this.signet);
    int nextSignetlvl = 1;
    if (placedSignet != null) {

      nextSignetlvl = placedSignet.getSkillId() - this.signetid + 2;
      if (nextSignetlvl > this.signetlvl || nextSignetlvl > 5)
        return;
      placedSignet.endEffect();
    }

    SkillTemplate template = DataManager.SKILL_DATA.getSkillTemplate(this.signetid + nextSignetlvl - 1);
    int effectsDuration = template.getEffectsDuration();
    Effect newEffect = new Effect(effect.getEffector(), effect.getEffected(), template, nextSignetlvl, effectsDuration);
    newEffect.initialize();
    newEffect.applyEffect();
  }

  public void calculate(Effect effect) {
    calculate(effect, DamageType.PHYSICAL);
  }
}
