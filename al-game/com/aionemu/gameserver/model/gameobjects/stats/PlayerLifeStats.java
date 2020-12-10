package com.aionemu.gameserver.model.gameobjects.stats;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.alliance.PlayerAllianceEvent;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.model.group.GroupEvent;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
import com.aionemu.gameserver.network.aion.serverpackets.SM_FLY_TIME;
import com.aionemu.gameserver.network.aion.serverpackets.SM_STATUPDATE_HP;
import com.aionemu.gameserver.network.aion.serverpackets.SM_STATUPDATE_MP;
import com.aionemu.gameserver.services.AllianceService;
import com.aionemu.gameserver.services.LifeStatsRestoreService;
import com.aionemu.gameserver.taskmanager.tasks.PacketBroadcaster;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

public class PlayerLifeStats extends CreatureLifeStats<Player> {
  protected int currentFp;
  private final ReentrantLock fpLock = new ReentrantLock();

  private Future<?> flyRestoreTask;

  private Future<?> flyReduceTask;

  public PlayerLifeStats(Player owner, int currentHp, int currentMp, int currentFp) {
    super((Creature) owner, currentHp, currentMp);
    this.currentFp = currentFp;
  }

  public PlayerLifeStats(Player owner) {
    super((Creature) owner, owner.getGameStats().getCurrentStat(StatEnum.MAXHP),
        owner.getGameStats().getCurrentStat(StatEnum.MAXMP));

    this.currentFp = owner.getGameStats().getCurrentStat(StatEnum.FLY_TIME);
  }

  protected void onReduceHp() {
    sendHpPacketUpdate();
    triggerRestoreTask();
    sendGroupPacketUpdate();
  }

  protected void onReduceMp() {
    sendMpPacketUpdate();
    triggerRestoreTask();
    sendGroupPacketUpdate();
  }

  protected void onIncreaseMp(SM_ATTACK_STATUS.TYPE type, int value) {
    sendMpPacketUpdate();
    sendAttackStatusPacketUpdate(type, value);
    sendGroupPacketUpdate();
  }

  protected void onIncreaseHp(SM_ATTACK_STATUS.TYPE type, int value) {
    if (isFullyRestoredHp()) {

      this.owner.getAggroList().clear();
    }
    sendHpPacketUpdate();
    sendAttackStatusPacketUpdate(type, value);
    sendGroupPacketUpdate();
  }

  private void sendGroupPacketUpdate() {
    Player owner = getOwner();
    if (owner.isInGroup())
      owner.getPlayerGroup().updateGroupUIToEvent(owner, GroupEvent.MOVEMENT);
    if (owner.isInAlliance()) {
      AllianceService.getInstance().updateAllianceUIToEvent(owner, PlayerAllianceEvent.MOVEMENT);
    }
  }

  public Player getOwner() {
    return (Player) super.getOwner();
  }

  public void restoreHp() {
    int currentRegenHp = getOwner().getGameStats().getCurrentStat(StatEnum.REGEN_HP);
    if (getOwner().isInState(CreatureState.RESTING))
      currentRegenHp *= 8;
    increaseHp(SM_ATTACK_STATUS.TYPE.NATURAL_HP, currentRegenHp);
  }

  public void restoreMp() {
    int currentRegenMp = getOwner().getGameStats().getCurrentStat(StatEnum.REGEN_MP);
    if (getOwner().isInState(CreatureState.RESTING))
      currentRegenMp *= 8;
    increaseMp(SM_ATTACK_STATUS.TYPE.NATURAL_MP, currentRegenMp);
  }

  public void synchronizeWithMaxStats() {
    if (isAlreadyDead()) {
      return;
    }
    super.synchronizeWithMaxStats();
    int maxFp = getMaxFp();
    if (this.currentFp != maxFp) {
      this.currentFp = maxFp;
    }
  }

  public void updateCurrentStats() {
    super.updateCurrentStats();

    if (getMaxFp() < this.currentFp) {
      this.currentFp = getMaxFp();
    }
    if (!this.owner.isInState(CreatureState.FLYING)) {
      triggerFpRestore();
    }
  }

  public void sendHpPacketUpdate() {
    this.owner.addPacketBroadcastMask(PacketBroadcaster.BroadcastMode.UPDATE_PLAYER_HP_STAT);
  }

  public void sendHpPacketUpdateImpl() {
    if (this.owner == null) {
      return;
    }
    PacketSendUtility.sendPacket((Player) this.owner,
        (AionServerPacket) new SM_STATUPDATE_HP(this.currentHp, getMaxHp()));
  }

  public void sendMpPacketUpdate() {
    this.owner.addPacketBroadcastMask(PacketBroadcaster.BroadcastMode.UPDATE_PLAYER_MP_STAT);
  }

  public void sendMpPacketUpdateImpl() {
    if (this.owner == null) {
      return;
    }
    PacketSendUtility.sendPacket((Player) this.owner,
        (AionServerPacket) new SM_STATUPDATE_MP(this.currentMp, getMaxMp()));
  }

  public int getCurrentFp() {
    return this.currentFp;
  }

  public int getMaxFp() {
    return this.owner.getGameStats().getCurrentStat(StatEnum.FLY_TIME);
  }

  public int getFpPercentage() {
    return 100 * this.currentFp / getMaxFp();
  }

  public int increaseFp(int value) {
    this.fpLock.lock();

    try {
      if (isAlreadyDead()) {
        return 0;
      }
      int newFp = this.currentFp + value;
      if (newFp > getMaxFp()) {
        newFp = getMaxFp();
      }
      if (this.currentFp != newFp) {
        this.currentFp = newFp;
        onIncreaseFp();
      }

    } finally {

      this.fpLock.unlock();
    }

    return this.currentFp;
  }

  public int reduceFp(int value) {
    this.fpLock.lock();

    try {
      int newFp = this.currentFp - value;

      if (newFp < 0) {
        newFp = 0;
      }
      this.currentFp = newFp;
    } finally {

      this.fpLock.unlock();
    }

    onReduceFp();

    return this.currentFp;
  }

  public int setCurrentFp(int value) {
    this.fpLock.lock();

    try {
      int newFp = value;

      if (newFp < 0) {
        newFp = 0;
      }
      this.currentFp = newFp;
    } finally {

      this.fpLock.unlock();
    }

    onReduceFp();

    return this.currentFp;
  }

  protected void onIncreaseFp() {
    this.owner.addPacketBroadcastMask(PacketBroadcaster.BroadcastMode.UPDATE_PLAYER_FLY_TIME);
  }

  protected void onReduceFp() {
    this.owner.addPacketBroadcastMask(PacketBroadcaster.BroadcastMode.UPDATE_PLAYER_FLY_TIME);
  }

  public void sendFpPacketUpdateImpl() {
    if (this.owner == null) {
      return;
    }
    PacketSendUtility.sendPacket((Player) this.owner, (AionServerPacket) new SM_FLY_TIME(this.currentFp, getMaxFp()));
  }

  public void restoreFp() {
    increaseFp(1);
  }

  public void triggerFpRestore() {
    cancelFpReduce();

    if (this.flyRestoreTask == null && !this.alreadyDead && !isFlyTimeFullyRestored()) {
      this.flyRestoreTask = LifeStatsRestoreService.getInstance().scheduleFpRestoreTask(this);
    }
  }

  public void cancelFpRestore() {
    if (this.flyRestoreTask != null && !this.flyRestoreTask.isCancelled()) {

      this.flyRestoreTask.cancel(false);
      this.flyRestoreTask = null;
    }
  }

  public void triggerFpReduce() {
    cancelFpRestore();

    if (this.flyReduceTask == null && !this.alreadyDead
        && getOwner().getAccessLevel() < AdminConfig.GM_FLIGHT_UNLIMITED) {

      this.flyReduceTask = LifeStatsRestoreService.getInstance().scheduleFpReduceTask(this);
    }
  }

  public void cancelFpReduce() {
    if (this.flyReduceTask != null && !this.flyReduceTask.isCancelled()) {

      this.flyReduceTask.cancel(false);
      this.flyReduceTask = null;
    }
  }

  public boolean isFlyTimeFullyRestored() {
    return (getMaxFp() == this.currentFp);
  }

  public void cancelAllTasks() {
    super.cancelAllTasks();
    cancelFpReduce();
    cancelFpRestore();
  }

  public void triggerRestoreOnRevive() {
    super.triggerRestoreOnRevive();
    triggerFpRestore();
  }

  protected void sendAttackStatusPacketUpdate(SM_ATTACK_STATUS.TYPE type, int value) {
    if (this.owner == null) {
      return;
    }

    PacketSendUtility.sendPacket((Player) this.owner,
        (AionServerPacket) new SM_ATTACK_STATUS(this.owner, type, 0, value));
    PacketSendUtility.broadcastPacket((VisibleObject) this.owner,
        (AionServerPacket) new SM_ATTACK_STATUS(this.owner, 0));
  }
}
