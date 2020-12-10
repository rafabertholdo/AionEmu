package com.aionemu.gameserver.services;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.stats.CreatureLifeStats;
import com.aionemu.gameserver.model.gameobjects.stats.PlayerLifeStats;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.concurrent.Future;

public class LifeStatsRestoreService {
  private static final int DEFAULT_DELAY = 6000;
  private static final int DEFAULT_FPREDUCE_DELAY = 1000;
  private static final int DEFAULT_FPRESTORE_DELAY = 2000;
  private static LifeStatsRestoreService instance = new LifeStatsRestoreService();

  public Future<?> scheduleRestoreTask(CreatureLifeStats<? extends Creature> lifeStats) {
    return ThreadPoolManager.getInstance().scheduleAtFixedRate(new HpMpRestoreTask(lifeStats), 1700L, 6000L);
  }

  public Future<?> scheduleHpRestoreTask(CreatureLifeStats<? extends Creature> lifeStats) {
    return ThreadPoolManager.getInstance().scheduleAtFixedRate(new HpRestoreTask(lifeStats), 1700L, 6000L);
  }

  public Future<?> scheduleFpReduceTask(PlayerLifeStats lifeStats) {
    return ThreadPoolManager.getInstance().scheduleAtFixedRate(new FpReduceTask(lifeStats), 2000L, 1000L);
  }

  public Future<?> scheduleFpRestoreTask(PlayerLifeStats lifeStats) {
    return ThreadPoolManager.getInstance().scheduleAtFixedRate(new FpRestoreTask(lifeStats), 2000L, 2000L);
  }

  public static LifeStatsRestoreService getInstance() {
    return instance;
  }

  private static class HpRestoreTask implements Runnable {
    private CreatureLifeStats<?> lifeStats;

    private HpRestoreTask(CreatureLifeStats<?> lifeStats) {
      this.lifeStats = lifeStats;
    }

    public void run() {
      if (this.lifeStats.isAlreadyDead() || this.lifeStats.isFullyRestoredHp()) {

        this.lifeStats.cancelRestoreTask();
      } else {

        this.lifeStats.restoreHp();
      }
    }
  }

  private static class HpMpRestoreTask implements Runnable {
    private CreatureLifeStats<?> lifeStats;

    private HpMpRestoreTask(CreatureLifeStats<?> lifeStats) {
      this.lifeStats = lifeStats;
    }

    public void run() {
      if (this.lifeStats.isAlreadyDead() || this.lifeStats.isFullyRestoredHpMp()) {

        this.lifeStats.cancelRestoreTask();
      } else {

        this.lifeStats.restoreHp();
        this.lifeStats.restoreMp();
      }
    }
  }

  private static class FpReduceTask implements Runnable {
    private PlayerLifeStats lifeStats;

    private FpReduceTask(PlayerLifeStats lifeStats) {
      this.lifeStats = lifeStats;
    }

    public void run() {
      if (this.lifeStats.isAlreadyDead()) {
        this.lifeStats.cancelFpReduce();
      }
      if (this.lifeStats.getCurrentFp() == 0) {

        if (this.lifeStats.getOwner().getFlyState() > 0) {
          this.lifeStats.getOwner().getFlyController().endFly();
        } else {
          this.lifeStats.triggerFpRestore();
        }

      } else {

        this.lifeStats.reduceFp(1);
      }
    }
  }

  private static class FpRestoreTask implements Runnable {
    private PlayerLifeStats lifeStats;

    private FpRestoreTask(PlayerLifeStats lifeStats) {
      this.lifeStats = lifeStats;
    }

    public void run() {
      if (this.lifeStats.isAlreadyDead() || this.lifeStats.isFlyTimeFullyRestored()) {

        this.lifeStats.cancelFpRestore();
      } else {

        this.lifeStats.restoreFp();
      }
    }
  }
}
