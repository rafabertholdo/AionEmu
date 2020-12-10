package com.aionemu.gameserver.model.gameobjects.stats;

import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.dataholders.PlayerStatsData;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.stats.PlayerStatsTemplate;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_STATS_INFO;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class PlayerGameStats extends CreatureGameStats<Player> {
  private int currentRunSpeed = 0;
  private int currentFlySpeed = 0;
  private int currentAttackSpeed = 0;

  public PlayerGameStats(Player owner) {
    super(owner);
  }

  public PlayerGameStats(PlayerStatsData playerStatsData, Player owner) {
    super(owner);
    PlayerStatsTemplate pst = playerStatsData.getTemplate(owner.getPlayerClass(), owner.getLevel());
    initStats(pst, owner.getLevel());
    log.debug("loading base game stats for player " + owner.getName() + " (id " + owner.getObjectId() + "): " + this);
  }

  public void recomputeStats() {
    super.recomputeStats();
    int newRunSpeed = getCurrentStat(StatEnum.SPEED);
    int newFlySpeed = getCurrentStat(StatEnum.FLY_SPEED);
    int newAttackSpeed = getCurrentStat(StatEnum.ATTACK_SPEED);

    if (newRunSpeed != this.currentRunSpeed || this.currentFlySpeed != newFlySpeed
        || newAttackSpeed != this.currentAttackSpeed) {
      PacketSendUtility.broadcastPacket(this.owner,
          (AionServerPacket) new SM_EMOTION((Creature) this.owner, EmotionType.START_EMOTE2, 0, 0), true);
    }

    PacketSendUtility.sendPacket(this.owner, (AionServerPacket) new SM_STATS_INFO(this.owner));

    this.currentRunSpeed = newRunSpeed;
    this.currentFlySpeed = newFlySpeed;
    this.currentAttackSpeed = newAttackSpeed;
  }

  private void initStats(PlayerStatsTemplate pst, int level) {
    this.lock.writeLock().lock();

    try {
      initStats(pst.getMaxHp(), pst.getMaxMp(), pst.getPower(), pst.getHealth(), pst.getAgility(), pst.getAccuracy(),
          pst.getKnowledge(), pst.getWill(), pst.getMainHandAttack(), pst.getMainHandCritRate(),
          Math.round(pst.getAttackSpeed() * 1000.0F), 1500, Math.round(pst.getRunSpeed() * 1000.0F),
          Math.round(pst.getFlySpeed() * 1000.0F));

      setAttackCounter(1);
      initStat(StatEnum.PARRY, pst.getParry());
      initStat(StatEnum.BLOCK, pst.getBlock());
      initStat(StatEnum.EVASION, pst.getEvasion());
      initStat(StatEnum.MAGICAL_ACCURACY, pst.getMagicAccuracy());
      initStat(StatEnum.MAIN_HAND_ACCURACY, pst.getMainHandAccuracy());
      initStat(StatEnum.FLY_TIME, CustomConfig.BASE_FLYTIME);
      initStat(StatEnum.REGEN_HP, level + 3);
      initStat(StatEnum.REGEN_MP, level + 8);
      initStat(StatEnum.MAXDP, 4000);
    } finally {

      this.lock.writeLock().unlock();
    }
  }

  protected void initStats(int maxHp, int maxMp, int power, int health, int agility, int accuracy, int knowledge,
      int will, int mainHandAttack, int mainHandCritRate, int attackSpeed, int attackRange, int runSpeed,
      int flySpeed) {
    this.stats.clear();
    initStat(StatEnum.MAXHP, maxHp);
    initStat(StatEnum.MAXMP, maxMp);
    initStat(StatEnum.POWER, power);
    initStat(StatEnum.ACCURACY, accuracy);
    initStat(StatEnum.HEALTH, health);
    initStat(StatEnum.AGILITY, agility);
    initStat(StatEnum.KNOWLEDGE, knowledge);
    initStat(StatEnum.WILL, will);
    initStat(StatEnum.MAIN_HAND_POWER, Math.round(18.0F * power * 0.01F));
    initStat(StatEnum.MAIN_HAND_CRITICAL, mainHandCritRate);
    initStat(StatEnum.OFF_HAND_POWER, 0);
    initStat(StatEnum.OFF_HAND_CRITICAL, 0);
    initStat(StatEnum.ATTACK_SPEED, attackSpeed);
    initStat(StatEnum.MAIN_HAND_ATTACK_SPEED, attackSpeed);
    initStat(StatEnum.OFF_HAND_ATTACK_SPEED, 0);
    initStat(StatEnum.ATTACK_RANGE, attackRange);
    initStat(StatEnum.PHYSICAL_DEFENSE, 0);
    initStat(StatEnum.PARRY, Math.round(agility * 3.1F - 248.5F + 12.4F * this.owner.getLevel()));
    initStat(StatEnum.EVASION, Math.round(agility * 3.1F - 248.5F + 12.4F * this.owner.getLevel()));
    initStat(StatEnum.BLOCK, Math.round(agility * 3.1F - 248.5F + 12.4F * this.owner.getLevel()));
    initStat(StatEnum.DAMAGE_REDUCE, 0);
    initStat(StatEnum.MAIN_HAND_ACCURACY, Math.round((accuracy * 2 - 10 + 8 * this.owner.getLevel())));
    initStat(StatEnum.OFF_HAND_ACCURACY, Math.round((accuracy * 2 - 10 + 8 * this.owner.getLevel())));
    initStat(StatEnum.MAGICAL_RESIST, 0);
    initStat(StatEnum.WIND_RESISTANCE, 0);
    initStat(StatEnum.FIRE_RESISTANCE, 0);
    initStat(StatEnum.WATER_RESISTANCE, 0);
    initStat(StatEnum.EARTH_RESISTANCE, 0);
    initStat(StatEnum.MAGICAL_ACCURACY, Math.round(14.26F * this.owner.getLevel()));
    initStat(StatEnum.BOOST_MAGICAL_SKILL, 0);
    initStat(StatEnum.SPEED, runSpeed);
    initStat(StatEnum.FLY_SPEED, flySpeed);
    initStat(StatEnum.PVP_ATTACK_RATIO, 0);
    initStat(StatEnum.PVP_DEFEND_RATIO, 0);
    initStat(StatEnum.BOOST_CASTING_TIME, 100);
    initStat(StatEnum.BOOST_HATE, 100);
    initStat(StatEnum.BOOST_HEAL, 100);
  }

  public void doLevelUpgrade() {
    initStats(this.owner.getPlayerStatsTemplate(), this.owner.getLevel());
    recomputeStats();
  }
}
