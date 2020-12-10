package com.aionemu.gameserver.ai.desires.impl;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.desires.AbstractDesire;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.templates.npcskill.NpcSkillList;
import com.aionemu.gameserver.model.templates.npcskill.NpcSkillTemplate;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.skillengine.model.Skill;
import java.util.List;

public class SkillUseDesire extends AbstractDesire {
  protected Creature owner;
  private NpcSkillList skillList;

  public SkillUseDesire(Creature owner, int desirePower) {
    super(desirePower);
    this.owner = owner;
    this.skillList = ((Npc) owner).getNpcSkillList();
  }

  public boolean handleDesire(AI<?> ai) {
    if (this.owner.isCasting()) {
      return true;
    }

    List<NpcSkillTemplate> skills = this.skillList.getNpcSkills();
    NpcSkillTemplate npcSkill = skills.get(Rnd.get(0, this.skillList.getCount() - 1));

    int skillProbability = npcSkill.getProbability();
    if (Rnd.get(0, 100) < skillProbability) {

      Skill skill = SkillEngine.getInstance().getSkill(this.owner, npcSkill.getSkillid(), npcSkill.getSkillLevel(),
          this.owner.getTarget());

      if (skill != null) {
        skill.useSkill();
      }
    }

    return true;
  }

  public void onClear() {
  }

  public int getExecutionInterval() {
    return 1;
  }
}
