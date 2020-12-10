package com.aionemu.gameserver.model.gameobjects.stats;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
import com.aionemu.gameserver.services.LifeStatsRestoreService;

public class NpcLifeStats extends CreatureLifeStats<Npc> {
  public NpcLifeStats(Npc owner) {
    super((Creature) owner, owner.getGameStats().getCurrentStat(StatEnum.MAXHP),
        owner.getGameStats().getCurrentStat(StatEnum.MAXMP));
  }

  protected void onIncreaseHp(SM_ATTACK_STATUS.TYPE type, int value) {
    sendAttackStatusPacketUpdate(type, value);
  }

  protected void onIncreaseMp(SM_ATTACK_STATUS.TYPE type, int value) {
  }

  protected void onReduceHp() {
  }

  protected void onReduceMp() {
  }

  protected void triggerRestoreTask() {
    if (this.lifeRestoreTask == null && !this.alreadyDead) {
      this.lifeRestoreTask = LifeStatsRestoreService.getInstance().scheduleHpRestoreTask(this);
    }
  }
}
