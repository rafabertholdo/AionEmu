package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.stats.CreatureGameStats;
import com.aionemu.gameserver.model.gameobjects.stats.id.SkillEffectId;
import com.aionemu.gameserver.model.gameobjects.stats.id.StatEffectId;
import com.aionemu.gameserver.model.gameobjects.stats.modifiers.AddModifier;
import com.aionemu.gameserver.model.gameobjects.stats.modifiers.RateModifier;
import com.aionemu.gameserver.model.gameobjects.stats.modifiers.SetModifier;
import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
import com.aionemu.gameserver.skillengine.change.Change;
import com.aionemu.gameserver.skillengine.change.Func;
import com.aionemu.gameserver.skillengine.model.Effect;
import java.util.TreeSet;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import org.apache.log4j.Logger;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BufEffect")
public abstract class BufEffect extends EffectTemplate {
  private static final Logger log = Logger.getLogger(BufEffect.class);

  public void applyEffect(Effect effect) {
    effect.addToEffectedController();
  }

  public void calculate(Effect effect) {
    effect.addSucessEffect(this);
  }

  public void endEffect(Effect effect) {
    Creature effected = effect.getEffected();
    int skillId = effect.getSkillId();
    effected.getGameStats().endEffect((StatEffectId) SkillEffectId.getInstance(skillId, this.effectid, this.position));
  }

  public void startEffect(Effect effect) {
    if (this.change == null) {
      return;
    }
    Creature effected = effect.getEffected();
    CreatureGameStats<? extends Creature> cgs = effected.getGameStats();

    TreeSet<StatModifier> modifiers = getModifiers(effect);
    SkillEffectId skillEffectId = getSkillEffectId(effect);

    if (modifiers.size() > 0) {
      cgs.addModifiers((StatEffectId) skillEffectId, modifiers);
    }
  }

  protected SkillEffectId getSkillEffectId(Effect effect) {
    int skillId = effect.getSkillId();
    return SkillEffectId.getInstance(skillId, this.effectid, this.position);
  }

  protected TreeSet<StatModifier> getModifiers(Effect effect) {
    int skillId = effect.getSkillId();
    int skillLvl = effect.getSkillLevel();

    TreeSet<StatModifier> modifiers = new TreeSet<StatModifier>();

    for (Change changeItem : this.change) {

      if (changeItem.getStat() == null) {

        log.warn("Skill stat has wrong name for skillid: " + skillId);

        continue;
      }
      int valueWithDelta = changeItem.getValue() + changeItem.getDelta() * skillLvl;

      switch (changeItem.getFunc()) {

        case ADD:
          modifiers.add(AddModifier.newInstance(changeItem.getStat(), valueWithDelta, true));

        case PERCENT:
          modifiers.add(RateModifier.newInstance(changeItem.getStat(), valueWithDelta, true));

        case REPLACE:
          modifiers.add(SetModifier.newInstance(changeItem.getStat(), valueWithDelta, true));
      }

    }
    return modifiers;
  }

  public void onPeriodicAction(Effect effect) {
  }
}
