package com.aionemu.gameserver.services;

import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.configs.main.GroupConfig;
import com.aionemu.gameserver.controllers.attack.AggroInfo;
import com.aionemu.gameserver.controllers.attack.KillList;
import com.aionemu.gameserver.model.alliance.PlayerAlliance;
import com.aionemu.gameserver.model.alliance.PlayerAllianceMember;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.group.PlayerGroup;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.stats.AbyssRankEnum;
import com.aionemu.gameserver.utils.stats.StatFunctions;
import java.util.ArrayList;
import java.util.List;
import javolution.util.FastMap;

public class PvpService {
  private FastMap<Integer, KillList> pvpKillLists;

  public static final PvpService getInstance() {
    return SingletonHolder.instance;
  }

  private PvpService() {
    this.pvpKillLists = new FastMap();
  }

  private int getKillsFor(int winnerId, int victimId) {
    KillList winnerKillList = (KillList) this.pvpKillLists.get(Integer.valueOf(winnerId));

    if (winnerKillList == null)
      return 0;
    return winnerKillList.getKillsFor(victimId);
  }

  private void addKillFor(int winnerId, int victimId) {
    KillList winnerKillList = (KillList) this.pvpKillLists.get(Integer.valueOf(winnerId));
    if (winnerKillList == null) {

      winnerKillList = new KillList();
      this.pvpKillLists.put(Integer.valueOf(winnerId), winnerKillList);
    }
    winnerKillList.addKillFor(victimId);
  }

  public void doReward(Player victim) {
    Player winner = victim.getAggroList().getMostPlayerDamage();

    int totalDamage = victim.getAggroList().getTotalDamage();

    if (totalDamage == 0 || winner == null) {
      return;
    }

    if (getKillsFor(winner.getObjectId(), victim.getObjectId()) < CustomConfig.MAX_DAILY_PVP_KILLS) {
      winner.getAbyssRank().setAllKill();
    }

    PacketSendUtility.broadcastPacketAndReceive((VisibleObject) victim,
        (AionServerPacket) SM_SYSTEM_MESSAGE.STR_MSG_COMBAT_FRIENDLY_DEATH_TO_B(victim.getName(), winner.getName()));

    int playerDamage = 0;
    boolean success = false;

    for (AggroInfo aggro : victim.getAggroList().getFinalDamageList(true)) {

      if (aggro.getAttacker() instanceof Player) {

        success = rewardPlayer(victim, totalDamage, aggro);
      } else if (aggro.getAttacker() instanceof PlayerGroup) {

        success = rewardPlayerGroup(victim, totalDamage, aggro);
      } else if (aggro.getAttacker() instanceof PlayerAlliance) {

        success = rewardPlayerAlliance(victim, totalDamage, aggro);
      }

      if (success) {
        playerDamage += aggro.getDamage();
      }
    }

    int apLost = StatFunctions.calculatePvPApLost(victim, winner);
    int apActuallyLost = apLost * playerDamage / totalDamage;

    if (apActuallyLost > 0) {
      victim.getCommonData().addAp(-apActuallyLost);
    }
  }

  private boolean rewardPlayerGroup(Player victim, int totalDamage, AggroInfo aggro) {
    PlayerGroup group = (PlayerGroup) aggro.getAttacker();

    if (group.getGroupLeader().getCommonData().getRace() == victim.getCommonData().getRace()) {
      return false;
    }

    List<Player> players = new ArrayList<Player>();

    int maxRank = AbyssRankEnum.GRADE9_SOLDIER.getId();
    int maxLevel = 0;

    for (Player member : group.getMembers()) {

      if (MathUtil.isIn3dRange((VisibleObject) member, (VisibleObject) victim, GroupConfig.GROUP_MAX_DISTANCE)) {

        if (!member.getLifeStats().isAlreadyDead()) {

          players.add(member);
          if (member.getLevel() > maxLevel)
            maxLevel = member.getLevel();
          if (member.getAbyssRank().getRank().getId() > maxRank) {
            maxRank = member.getAbyssRank().getRank().getId();
          }
        }
      }
    }

    if (players.size() == 0) {
      return false;
    }
    int baseApReward = StatFunctions.calculatePvpApGained(victim, maxRank, maxLevel);
    float groupApPercentage = aggro.getDamage() / totalDamage;
    int apRewardPerMember = Math.round(baseApReward * groupApPercentage / players.size());

    if (apRewardPerMember > 0) {
      for (Player member : players) {

        int memberApGain = 1;
        if (getKillsFor(member.getObjectId(), victim.getObjectId()) < CustomConfig.MAX_DAILY_PVP_KILLS)
          memberApGain = Math.round(apRewardPerMember * member.getRates().getApPlayerRate());
        member.getCommonData().addAp(memberApGain);
        addKillFor(member.getObjectId(), victim.getObjectId());
      }
    }

    return true;
  }

  private boolean rewardPlayerAlliance(Player victim, int totalDamage, AggroInfo aggro) {
    PlayerAlliance alliance = (PlayerAlliance) aggro.getAttacker();

    if (alliance.getCaptain().getCommonData().getRace() == victim.getCommonData().getRace()) {
      return false;
    }

    List<Player> players = new ArrayList<Player>();

    int maxRank = AbyssRankEnum.GRADE9_SOLDIER.getId();
    int maxLevel = 0;

    for (PlayerAllianceMember allianceMember : alliance.getMembers()) {

      if (!allianceMember.isOnline())
        continue;
      Player member = allianceMember.getPlayer();
      if (MathUtil.isIn3dRange((VisibleObject) member, (VisibleObject) victim, GroupConfig.GROUP_MAX_DISTANCE)) {

        if (!member.getLifeStats().isAlreadyDead()) {

          players.add(member);
          if (member.getLevel() > maxLevel)
            maxLevel = member.getLevel();
          if (member.getAbyssRank().getRank().getId() > maxRank) {
            maxRank = member.getAbyssRank().getRank().getId();
          }
        }
      }
    }

    if (players.size() == 0) {
      return false;
    }
    int baseApReward = StatFunctions.calculatePvpApGained(victim, maxRank, maxLevel);
    float groupApPercentage = aggro.getDamage() / totalDamage;
    int apRewardPerMember = Math.round(baseApReward * groupApPercentage / players.size());

    if (apRewardPerMember > 0) {
      for (Player member : players) {

        int memberApGain = 1;
        if (getKillsFor(member.getObjectId(), victim.getObjectId()) < CustomConfig.MAX_DAILY_PVP_KILLS)
          memberApGain = Math.round(apRewardPerMember * member.getRates().getApPlayerRate());
        member.getCommonData().addAp(memberApGain);
        addKillFor(member.getObjectId(), victim.getObjectId());
      }
    }

    return true;
  }

  private boolean rewardPlayer(Player victim, int totalDamage, AggroInfo aggro) {
    Player winner = (Player) aggro.getAttacker();

    if (winner.getCommonData().getRace() == victim.getCommonData().getRace()) {
      return false;
    }
    int baseApReward = 1;

    if (getKillsFor(winner.getObjectId(), victim.getObjectId()) < CustomConfig.MAX_DAILY_PVP_KILLS) {
      baseApReward = StatFunctions.calculatePvpApGained(victim, winner.getAbyssRank().getRank().getId(),
          winner.getLevel());
    }
    int apPlayerReward = Math
        .round(baseApReward * winner.getRates().getApPlayerRate() * aggro.getDamage() / totalDamage);

    winner.getCommonData().addAp(apPlayerReward);
    addKillFor(winner.getObjectId(), victim.getObjectId());

    return true;
  }

  private static class SingletonHolder {
    protected static final PvpService instance = new PvpService();
  }
}
