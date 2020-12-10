package com.aionemu.gameserver.services;

import com.aionemu.gameserver.configs.main.GroupConfig;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Monster;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.aionemu.gameserver.model.group.GroupEvent;
import com.aionemu.gameserver.model.group.LootDistribution;
import com.aionemu.gameserver.model.group.LootGroupRules;
import com.aionemu.gameserver.model.group.LootRuleType;
import com.aionemu.gameserver.model.group.PlayerGroup;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_GROUP_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_GROUP_MEMBER_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SHOW_BRAND;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.restrictions.RestrictionsManager;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.idfactory.IDFactory;
import com.aionemu.gameserver.utils.stats.StatFunctions;
import com.aionemu.gameserver.world.WorldType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import javolution.util.FastMap;
import org.apache.log4j.Logger;

public class GroupService {
  private static final Logger log = Logger.getLogger(GroupService.class);

  private final FastMap<Integer, PlayerGroup> groupMembers;

  private FastMap<Integer, ScheduledFuture<?>> playerGroup;

  private List<Player> inRangePlayers;

  public static final GroupService getInstance() {
    return SingletonHolder.instance;
  }

  private GroupService() {
    this.groupMembers = new FastMap();
    this.playerGroup = new FastMap();

    log.info("GroupService: Initialized.");
  }

  private void addGroupMemberToCache(Player player) {
    if (!this.groupMembers.containsKey(Integer.valueOf(player.getObjectId()))) {
      this.groupMembers.put(Integer.valueOf(player.getObjectId()), player.getPlayerGroup());
    }
  }

  private void removeGroupMemberFromCache(int playerObjId) {
    if (this.groupMembers.containsKey(Integer.valueOf(playerObjId))) {
      this.groupMembers.remove(Integer.valueOf(playerObjId));
    }
  }

  public boolean isGroupMember(int playerObjId) {
    return this.groupMembers.containsKey(Integer.valueOf(playerObjId));
  }

  private PlayerGroup getGroup(int playerObjId) {
    return (PlayerGroup) this.groupMembers.get(Integer.valueOf(playerObjId));
  }

  public void invitePlayerToGroup(final Player inviter, final Player invited) {
    if (RestrictionsManager.canInviteToGroup(inviter, invited)) {

      RequestResponseHandler responseHandler = new RequestResponseHandler((Creature) inviter) {
        public void acceptRequest(Creature requester, Player responder) {
          PlayerGroup group = inviter.getPlayerGroup();
          if (group != null && group.isFull()) {
            return;
          }
          PacketSendUtility.sendPacket(inviter,
              (AionServerPacket) SM_SYSTEM_MESSAGE.REQUEST_GROUP_INVITE(invited.getName()));
          if (group != null) {

            inviter.getPlayerGroup().addPlayerToGroup(invited);
            GroupService.this.addGroupMemberToCache(invited);
          } else {

            new PlayerGroup(IDFactory.getInstance().nextId(), inviter);
            inviter.getPlayerGroup().addPlayerToGroup(invited);
            GroupService.this.addGroupMemberToCache(inviter);
            GroupService.this.addGroupMemberToCache(invited);
          }
        }

        public void denyRequest(Creature requester, Player responder) {
          PacketSendUtility.sendPacket(inviter,
              (AionServerPacket) SM_SYSTEM_MESSAGE.REJECT_GROUP_INVITE(responder.getName()));
        }
      };

      boolean result = invited.getResponseRequester().putRequest(60000, responseHandler);

      if (result) {
        PacketSendUtility.sendPacket(invited,
            (AionServerPacket) new SM_QUESTION_WINDOW(60000, 0, new Object[] { inviter.getName() }));
      }
    }
  }

  public void removePlayerFromGroup(Player player) {
    if (player.isInGroup()) {

      PlayerGroup group = player.getPlayerGroup();

      group.removePlayerFromGroup(player);
      removeGroupMemberFromCache(player.getObjectId());

      if (group.size() < 2) {
        disbandGroup(group);
      }
    }
  }

  private void setGroupLeader(Player player) {
    PlayerGroup group = player.getPlayerGroup();

    group.setGroupLeader(player);
    group.updateGroupUIToEvent(player.getPlayerGroup().getGroupLeader(), GroupEvent.CHANGELEADER);
  }

  public void playerStatusInfo(int status, Player player) {
    switch (status) {

      case 2:
        removePlayerFromGroup(player);
        break;
      case 3:
        setGroupLeader(player);
        break;
      case 6:
        removePlayerFromGroup(player);
        break;
    }
  }

  public void groupDistribution(Player player, long amount) {
    PlayerGroup pg = player.getPlayerGroup();
    if (pg == null) {
      return;
    }
    long availableKinah = player.getInventory().getKinahItem().getItemCount();
    if (availableKinah < amount) {
      return;
    }

    long rewardcount = (pg.size() - 1);
    if (rewardcount <= amount) {

      long reward = amount / rewardcount;

      for (Player groupMember : pg.getMembers()) {

        if (groupMember.equals(player)) {
          ItemService.decreaseKinah(groupMember, amount);
          continue;
        }
        ItemService.increaseKinah(groupMember, reward);
      }
    }
  }

  public void doReward(PlayerGroup group, Monster owner) {
    List<Player> players = new ArrayList<Player>();
    int partyLvlSum = 0;
    int highestLevel = 0;
    for (Player member : group.getMembers()) {

      if (member.isOnline()
          && MathUtil.isIn3dRange((VisibleObject) member, (VisibleObject) owner, GroupConfig.GROUP_MAX_DISTANCE)) {

        if (member.getLifeStats().isAlreadyDead())
          continue;
        players.add(member);
        partyLvlSum += member.getLevel();
        if (member.getLevel() > highestLevel) {
          highestLevel = member.getLevel();
        }
      }
    }

    if (players.size() == 0) {
      return;
    }

    int apRewardPerMember = 0;
    WorldType worldType = owner.getWorldType();

    if (worldType == WorldType.ABYSS) {

      apRewardPerMember = Math
          .round((StatFunctions.calculateGroupAPReward(highestLevel, (Creature) owner) / players.size()));
    }

    long expReward = StatFunctions.calculateGroupExperienceReward(highestLevel, (Creature) owner);

    int bonus = 1;
    if (players.size() == 0)
      return;
    if (players.size() > 0) {
      bonus = 100 + (players.size() - 1) * 10;
    }
    for (Player member : players) {

      long currentExp = member.getCommonData().getExp();
      long reward = expReward * bonus * member.getLevel() / (partyLvlSum * 100);
      reward *= member.getRates().getGroupXpRate();

      if (highestLevel - member.getLevel() >= 10)
        reward = 0L;
      member.getCommonData().setExp(currentExp + reward);

      PacketSendUtility.sendPacket(member, (AionServerPacket) SM_SYSTEM_MESSAGE.EXP(Long.toString(reward)));

      int currentDp = member.getCommonData().getDp();
      int dpReward = StatFunctions.calculateGroupDPReward(member, (Creature) owner);
      member.getCommonData().setDp(dpReward + currentDp);

      if (apRewardPerMember > 0) {
        member.getCommonData().addAp(Math.round(apRewardPerMember * member.getRates().getApNpcRate()));
      }
      QuestEngine.getInstance()
          .onKill(new QuestEnv((VisibleObject) owner, member, Integer.valueOf(0), Integer.valueOf(0)));
    }

    setInRangePlayers(players);
    Player leader = group.getGroupLeader();
    DropService.getInstance().registerDrop((Npc) owner, leader, highestLevel);
  }

  public void showBrand(PlayerGroup playerGroup, int brandId, int targetObjectId) {
    for (Player member : playerGroup.getMembers()) {
      PacketSendUtility.sendPacket(member, (AionServerPacket) new SM_SHOW_BRAND(brandId, targetObjectId));
    }
  }

  private void disbandGroup(PlayerGroup group) {
    IDFactory.getInstance().releaseId(group.getGroupId());
    group.getGroupLeader().setPlayerGroup(null);
    PacketSendUtility.sendPacket(group.getGroupLeader(), (AionServerPacket) SM_SYSTEM_MESSAGE.DISBAND_GROUP());
    group.disband();
  }

  public void onLogin(Player activePlayer) {
    PlayerGroup group = activePlayer.getPlayerGroup();

    PacketSendUtility.sendPacket(activePlayer, (AionServerPacket) new SM_GROUP_INFO(group));
    for (Player member : group.getMembers()) {

      if (!activePlayer.equals(member)) {
        PacketSendUtility.sendPacket(activePlayer,
            (AionServerPacket) new SM_GROUP_MEMBER_INFO(group, member, GroupEvent.ENTER));
      }
    }
  }

  private void addPlayerGroupCache(int playerObjId, ScheduledFuture<?> future) {
    if (!this.playerGroup.containsKey(Integer.valueOf(playerObjId))) {
      this.playerGroup.put(Integer.valueOf(playerObjId), future);
    }
  }

  private void cancelScheduleRemove(int playerObjId) {
    if (this.playerGroup.containsKey(Integer.valueOf(playerObjId))) {

      ((ScheduledFuture) this.playerGroup.get(Integer.valueOf(playerObjId))).cancel(true);
      this.playerGroup.remove(Integer.valueOf(playerObjId));
    }
  }

  public void scheduleRemove(final Player player) {
    ScheduledFuture<?> future = ThreadPoolManager.getInstance().schedule(new Runnable() {
      public void run() {
        GroupService.this.removePlayerFromGroup(player);
        GroupService.this.playerGroup.remove(Integer.valueOf(player.getObjectId()));
      }
    }, (GroupConfig.GROUP_REMOVE_TIME * 1000));
    addPlayerGroupCache(player.getObjectId(), future);
    player.getPlayerGroup().getMembers().remove(Integer.valueOf(player.getObjectId()));

    for (Player groupMember : player.getPlayerGroup().getMembers()) {

      PacketSendUtility.sendPacket(groupMember,
          (AionServerPacket) SM_SYSTEM_MESSAGE.PARTY_HE_BECOME_OFFLINE(player.getName()));
    }
  }

  public void setGroup(Player player) {
    if (!isGroupMember(player.getObjectId())) {
      return;
    }
    PlayerGroup group = getGroup(player.getObjectId());
    if (group.size() < 2) {

      removeGroupMemberFromCache(player.getObjectId());
      cancelScheduleRemove(player.getObjectId());
      return;
    }
    player.setPlayerGroup(group);
    group.onGroupMemberLogIn(player);
    cancelScheduleRemove(player.getObjectId());
    if (group.getGroupLeader().getObjectId() == player.getObjectId()) {
      group.setGroupLeader(player);
    }
  }

  public List<Integer> getMembersToRegistrateByRules(Player player, PlayerGroup group, Npc npc) {
    int roundRobinMember;
    LootGroupRules lootRules = group.getLootGroupRules();
    LootRuleType lootRule = lootRules.getLootRule();
    List<Integer> luckyMembers = new ArrayList<Integer>();

    switch (lootRule) {

      case NORMAL:
        roundRobinMember = group.getRoundRobinMember(npc);
        if (roundRobinMember != 0) {

          luckyMembers.add(Integer.valueOf(roundRobinMember));
          break;
        }
      case ROLL_DICE:
        luckyMembers = getGroupMembers(group, false);
        break;
      case BID:
        luckyMembers.add(Integer.valueOf(group.getGroupLeader().getObjectId()));
        break;
    }
    return luckyMembers;
  }

  public List<Integer> getGroupMembers(PlayerGroup group, boolean except) {
    List<Integer> luckyMembers = new ArrayList<Integer>();
    for (Iterator<Integer> i$ = group.getMemberObjIds().iterator(); i$.hasNext();) {
      int memberObjId = ((Integer) i$.next()).intValue();

      if (except) {

        if (group.getGroupLeader().getObjectId() != memberObjId)
          luckyMembers.add(Integer.valueOf(memberObjId));
        continue;
      }
      luckyMembers.add(Integer.valueOf(memberObjId));
    }

    return luckyMembers;
  }

  public Player getLuckyPlayer(Player player) {
    PlayerGroup group = player.getPlayerGroup();
    switch (group.getLootGroupRules().getAutodistribution()) {

      case NORMAL:
        return player;

      case ROLL_DICE:
        return player;

      case BID:
        return player;
    }
    return player;
  }

  public void setInRangePlayers(List<Player> inRangePlayers) {
    this.inRangePlayers = inRangePlayers;
  }

  public List<Player> getInRangePlayers() {
    return this.inRangePlayers;
  }

  private static class SingletonHolder {
    protected static final GroupService instance = new GroupService();
  }
}
