package com.aionemu.gameserver.model.gameobjects.stats;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Summon;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SUMMON_UPDATE;
import com.aionemu.gameserver.services.LifeStatsRestoreService;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class SummonLifeStats extends CreatureLifeStats<Summon> {
  public SummonLifeStats(Summon owner) {
    super((Creature) owner, owner.getGameStats().getCurrentStat(StatEnum.MAXHP),
        owner.getGameStats().getCurrentStat(StatEnum.MAXMP));
  }

  protected void onIncreaseHp(SM_ATTACK_STATUS.TYPE type, int value) {
    Player player = getOwner().getMaster();
    sendAttackStatusPacketUpdate(type, value);

    if (player instanceof Player) {
      PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_SUMMON_UPDATE(getOwner()));
    }
  }

  protected void onIncreaseMp(SM_ATTACK_STATUS.TYPE type, int value) {
  }

  protected void onReduceHp() {
  }

  protected void onReduceMp() {
  }

  public Summon getOwner() {
    return (Summon) super.getOwner();
  }

  protected void triggerRestoreTask() {
    if (this.lifeRestoreTask == null && !this.alreadyDead) {
      this.lifeRestoreTask = LifeStatsRestoreService.getInstance().scheduleHpRestoreTask(this);
    }
  }
}
