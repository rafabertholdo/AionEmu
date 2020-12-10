package com.aionemu.gameserver.skillengine;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.ActivationAttribute;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.skillengine.model.SkillTemplate;

public class SkillEngine {
  public static final SkillEngine skillEngine = new SkillEngine();

  public Skill getSkillFor(Player player, int skillId, VisibleObject firstTarget) {
    SkillTemplate template = DataManager.SKILL_DATA.getSkillTemplate(skillId);

    if (template == null) {
      return null;
    }

    if (template.getActivationAttribute() != ActivationAttribute.PROVOKED) {
      if (!player.getSkillList().isSkillPresent(skillId)) {
        return null;
      }
    }

    Creature target = null;
    if (firstTarget instanceof Creature) {
      target = (Creature) firstTarget;
    }
    return new Skill(template, player, target);
  }

  public Skill getSkill(Creature creature, int skillId, int skillLevel, VisibleObject firstTarget) {
    SkillTemplate template = DataManager.SKILL_DATA.getSkillTemplate(skillId);

    if (template == null) {
      return null;
    }
    Creature target = null;
    if (firstTarget instanceof Creature)
      target = (Creature) firstTarget;
    return new Skill(template, creature, skillLevel, target);
  }

  public static SkillEngine getInstance() {
    return skillEngine;
  }
}
