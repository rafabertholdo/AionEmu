package com.aionemu.gameserver.model.gameobjects;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.npcai.ServantAi;
import com.aionemu.gameserver.controllers.NpcController;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;

public class Servant extends Npc {
  private int skillId;
  private Creature creator;
  private Creature target;
  private int hpRatio;

  public Servant(int objId, NpcController controller, SpawnTemplate spawnTemplate,
      VisibleObjectTemplate objectTemplate) {
    super(objId, controller, spawnTemplate, objectTemplate);
  }

  public int getSkillId() {
    return this.skillId;
  }

  public void setSkillId(int skillId) {
    this.skillId = skillId;
  }

  public Creature getCreator() {
    return this.creator;
  }

  public void setCreator(Creature creator) {
    this.creator = creator;
  }

  public Creature getTarget() {
    return this.target;
  }

  public void setTarget(Creature target) {
    this.target = target;
  }

  public int getHpRatio() {
    return this.hpRatio;
  }

  public void setHpRatio(int hpRatio) {
    this.hpRatio = hpRatio;
  }

  public void initializeAi() {
    this.ai = (AI<? extends Creature>) new ServantAi();
    this.ai.setOwner(this);
  }

  protected boolean isEnemyNpc(Npc visibleObject) {
    return this.creator.isEnemyNpc(visibleObject);
  }

  protected boolean isEnemyPlayer(Player visibleObject) {
    return this.creator.isEnemyPlayer(visibleObject);
  }

  protected boolean isEnemySummon(Summon summon) {
    return this.creator.isEnemySummon(summon);
  }

  public NpcObjectType getNpcObjectType() {
    return NpcObjectType.SERVANT;
  }

  public Creature getActingCreature() {
    return this.creator;
  }

  public Creature getMaster() {
    return this.creator;
  }
}
