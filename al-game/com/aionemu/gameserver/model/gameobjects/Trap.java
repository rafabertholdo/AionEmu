package com.aionemu.gameserver.model.gameobjects;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.npcai.TrapAi;
import com.aionemu.gameserver.controllers.NpcController;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;

public class Trap extends Npc {
  private int skillId;
  private Creature creator;

  public Trap(int objId, NpcController controller, SpawnTemplate spawnTemplate, VisibleObjectTemplate objectTemplate) {
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

  public byte getLevel() {
    return (this.creator == null) ? 1 : this.creator.getLevel();
  }

  public void initializeAi() {
    this.ai = (AI<? extends Creature>) new TrapAi();
    this.ai.setOwner(this);
  }

  protected boolean isEnemyNpc(Npc visibleObject) {
    return this.creator.isEnemyNpc(visibleObject);
  }

  protected boolean isEnemyPlayer(Player visibleObject) {
    return this.creator.isEnemyPlayer(visibleObject);
  }

  public NpcObjectType getNpcObjectType() {
    return NpcObjectType.TRAP;
  }

  public Creature getActingCreature() {
    return this.creator;
  }

  public Creature getMaster() {
    return this.creator;
  }
}
