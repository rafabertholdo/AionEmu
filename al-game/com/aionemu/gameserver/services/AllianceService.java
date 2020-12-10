package com.aionemu.gameserver.services;

import com.aionemu.gameserver.configs.main.GroupConfig;
import com.aionemu.gameserver.model.alliance.PlayerAlliance;
import com.aionemu.gameserver.model.alliance.PlayerAllianceEvent;
import com.aionemu.gameserver.model.alliance.PlayerAllianceMember;
import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Monster;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.aionemu.gameserver.model.group.PlayerGroup;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ALLIANCE_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ALLIANCE_MEMBER_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEAVE_GROUP_MEMBER;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_ID;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_INFO;
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
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import javolution.util.FastMap;
import org.apache.log4j.Logger;

public class AllianceService {
  private static final Logger log = Logger.getLogger(AllianceService.class);

  private FastMap<Integer, ScheduledFuture<?>> playerAllianceRemovalTasks;

  private final FastMap<Integer, PlayerAlliance> allianceMembers;

  public static final AllianceService getInstance() {
    return SingletonHolder.instance;
  }

  public AllianceService() {
    this.allianceMembers = new FastMap();
    this.playerAllianceRemovalTasks = new FastMap();

    log.info("AllianceService: Initialized.");
  }

  private void addAllianceMemberToCache(Player player) {
    if (!this.allianceMembers.containsKey(Integer.valueOf(player.getObjectId()))) {
      this.allianceMembers.put(Integer.valueOf(player.getObjectId()), player.getPlayerAlliance());
    }
  }

  private void removeAllianceMemberFromCache(int playerObjId) {
    if (this.allianceMembers.containsKey(Integer.valueOf(playerObjId))) {
      this.allianceMembers.remove(Integer.valueOf(playerObjId));
    }
  }

  public boolean isAllianceMember(int playerObjId) {
    return this.allianceMembers.containsKey(Integer.valueOf(playerObjId));
  }

  public PlayerAlliance getPlayerAlliance(int playerObjId) {
    return (PlayerAlliance) this.allianceMembers.get(Integer.valueOf(playerObjId));
  }

  private void addAllianceRemovalTask(int playerObjectId, ScheduledFuture<?> task) {
    if (!this.playerAllianceRemovalTasks.containsKey(Integer.valueOf(playerObjectId))) {
      this.playerAllianceRemovalTasks.put(Integer.valueOf(playerObjectId), task);
    }
  }

  private void cancelRemovalTask(int playerObjectId) {
    if (this.playerAllianceRemovalTasks.containsKey(Integer.valueOf(playerObjectId))) {

      ((ScheduledFuture) this.playerAllianceRemovalTasks.get(Integer.valueOf(playerObjectId))).cancel(true);
      this.playerAllianceRemovalTasks.remove(Integer.valueOf(playerObjectId));
    }
  }

  public void scheduleRemove(final Player player) {
    ScheduledFuture<?> future = ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            AllianceService.this.removeMemberFromAlliance(player.getPlayerAlliance(), player.getObjectId(), PlayerAllianceEvent.LEAVE_TIMEOUT, new String[0]);
          }
        }(GroupConfig.ALLIANCE_REMOVE_TIME * 1000));
    
    addAllianceRemovalTask(player.getObjectId(), future);
    player.getPlayerAlliance().onPlayerDisconnect(player);
  }

  public void invitePlayerToAlliance(Player inviter, Player invited) {
    if (RestrictionsManager.canInviteToAlliance(inviter, invited)) {

      RequestResponseHandler responseHandler = getResponseHandler(inviter, invited);

      boolean result = invited.getResponseRequester().putRequest(70004, responseHandler);

      if (result) {

        if (invited.isInGroup()) {

          PacketSendUtility.sendPacket(inviter,
              (AionServerPacket) SM_SYSTEM_MESSAGE.STR_PARTY_ALLIANCE_INVITED_HIS_PARTY(invited.getName()));
        } else {

          PacketSendUtility.sendPacket(inviter,
              (AionServerPacket) SM_SYSTEM_MESSAGE.STR_FORCE_INVITED_HIM(invited.getName()));
        }

        PacketSendUtility.sendPacket(invited,
            (AionServerPacket) new SM_QUESTION_WINDOW(70004, 0, new Object[] { inviter.getName() }));
      }
    }
  }

  private RequestResponseHandler getResponseHandler(final Player inviter, final Player invited) {
    RequestResponseHandler responseHandler = new RequestResponseHandler((Creature) inviter) {
      public void acceptRequest(Creature requester, Player responder) {
        List<Player> playersToAdd = new ArrayList<Player>();
        PlayerAlliance alliance = inviter.getPlayerAlliance();

        if (alliance == null) {

          alliance = new PlayerAlliance(IDFactory.getInstance().nextId(), inviter.getObjectId());

          if (inviter.isInGroup()) {

            PlayerGroup group = inviter.getPlayerGroup();
            playersToAdd.addAll(group.getMembers());

            for (Player member : group.getMembers()) {
              GroupService.getInstance().removePlayerFromGroup(member);
            }
          } else {

            playersToAdd.add(inviter);
          }
        } else {
          if (alliance.size() == 24) {

            PacketSendUtility.sendMessage(invited, "That alliance is already full.");
            PacketSendUtility.sendMessage(inviter, "Your alliance is already full.");
            return;
          }
          if (invited.isInGroup() && invited.getPlayerGroup().size() + alliance.size() > 24) {

            PacketSendUtility.sendMessage(invited, "That alliance is now too full for your group to join.");
            PacketSendUtility.sendMessage(inviter, "Your alliance is now too full for that group to join.");

            return;
          }
        }
        if (invited.isInGroup()) {

          PlayerGroup group = invited.getPlayerGroup();
          playersToAdd.addAll(group.getMembers());

          for (Player member : group.getMembers()) {
            GroupService.getInstance().removePlayerFromGroup(member);
          }
        } else {

          playersToAdd.add(invited);
        }

        for (Player member : playersToAdd) {
          AllianceService.this.addMemberToAlliance(alliance, member);
        }
      }

      public void denyRequest(Creature requester, Player responder) {
        PacketSendUtility.sendPacket(inviter,
            (AionServerPacket) SM_SYSTEM_MESSAGE.STR_PARTY_ALLIANCE_HE_REJECT_INVITATION(responder.getName()));
      }
    };
    return responseHandler;
  }

  protected void addMemberToAlliance(PlayerAlliance alliance, Player newMember) {
    alliance.addMember(newMember);
    addAllianceMemberToCache(newMember);

    PacketSendUtility.sendPacket(newMember, (AionServerPacket) new SM_ALLIANCE_INFO(alliance));
    PacketSendUtility.sendPacket(newMember, (AionServerPacket) new SM_SHOW_BRAND(0, 0));
    PacketSendUtility.sendPacket(newMember, (AionServerPacket) SM_SYSTEM_MESSAGE.STR_FORCE_ENTERED_FORCE());

    broadcastAllianceMemberInfo(alliance, newMember.getObjectId(), PlayerAllianceEvent.ENTER, new String[0]);
    sendOtherMemberInfo(alliance, newMember);
  }

  public void handleGroupChange(PlayerAlliance alliance, int playerObjectId, int allianceGroupId, int secondObjectId) {
    if (allianceGroupId == 0) {

      alliance.swapPlayers(playerObjectId, secondObjectId);

      broadcastAllianceMemberInfo(alliance, playerObjectId, PlayerAllianceEvent.MEMBER_GROUP_CHANGE, new String[0]);
      broadcastAllianceMemberInfo(alliance, secondObjectId, PlayerAllianceEvent.MEMBER_GROUP_CHANGE, new String[0]);
    } else {

      alliance.setAllianceGroupFor(playerObjectId, allianceGroupId);
      broadcastAllianceMemberInfo(alliance, playerObjectId, PlayerAllianceEvent.MEMBER_GROUP_CHANGE, new String[0]);
    }
  }

  private void broadcastAllianceMemberInfo(PlayerAlliance alliance, int playerObjectId, PlayerAllianceEvent event,
      String... params) {
    PlayerAllianceMember memberToUpdate = alliance.getPlayer(playerObjectId);
    broadcastAllianceMemberInfo(alliance, memberToUpdate, event, params);
  }

  private void broadcastAllianceMemberInfo(PlayerAlliance alliance, PlayerAllianceMember memberToUpdate,
      PlayerAllianceEvent event, String... params) {
    for (PlayerAllianceMember allianceMember : alliance.getMembers()) {

      if (!allianceMember.isOnline())
        continue;
      Player member = allianceMember.getPlayer();
      PacketSendUtility.sendPacket(member, (AionServerPacket) new SM_ALLIANCE_MEMBER_INFO(memberToUpdate, event));
      PacketSendUtility.sendPacket(member, (AionServerPacket) new SM_PLAYER_ID((AionObject) memberToUpdate));
      switch (event) {

        case ENTER:
          if (!member.equals(memberToUpdate.getPlayer())) {
            PacketSendUtility.sendPacket(member,
                (AionServerPacket) SM_SYSTEM_MESSAGE.STR_FORCE_HE_ENTERED_FORCE(memberToUpdate.getName()));
          }
        case LEAVE:
          PacketSendUtility.sendPacket(member,
              (AionServerPacket) SM_SYSTEM_MESSAGE.STR_PARTY_ALLIANCE_HE_LEAVED_PARTY(memberToUpdate.getName()));

        case LEAVE_TIMEOUT:
          PacketSendUtility.sendPacket(member,
              (AionServerPacket) SM_SYSTEM_MESSAGE.STR_FORCE_HE_BECOME_OFFLINE_TIMEOUT(memberToUpdate.getName()));

        case BANNED:
          if (member.equals(memberToUpdate.getPlayer())) {
            PacketSendUtility.sendPacket(member, (AionServerPacket) SM_SYSTEM_MESSAGE.STR_FORCE_BAN_ME(params[0]));
            continue;
          }
          PacketSendUtility.sendPacket(member,
              (AionServerPacket) SM_SYSTEM_MESSAGE.STR_FORCE_BAN_HIM(params[0], memberToUpdate.getName()));
      }
    }
  }

  public void broadcastAllianceInfo(PlayerAlliance alliance, PlayerAllianceEvent event, String... params) {
    SM_ALLIANCE_INFO packet = new SM_ALLIANCE_INFO(alliance);
    for (PlayerAllianceMember allianceMember : alliance.getMembers()) {

      if (!allianceMember.isOnline())
        continue;
      Player member = allianceMember.getPlayer();
      PacketSendUtility.sendPacket(member, (AionServerPacket) packet);
      switch (event) {

        case APPOINT_CAPTAIN:
          PacketSendUtility.sendPacket(member,
              (AionServerPacket) SM_SYSTEM_MESSAGE.STR_FORCE_CHANGE_LEADER(params[0], alliance.getCaptain().getName()));

        case APPOINT_VICE_CAPTAIN:
          PacketSendUtility.sendPacket(member,
              (AionServerPacket) SM_SYSTEM_MESSAGE.STR_FORCE_PROMOTE_MANAGER(params[0]));

        case DEMOTE_VICE_CAPTAIN:
          PacketSendUtility.sendPacket(member,
              (AionServerPacket) SM_SYSTEM_MESSAGE.STR_FORCE_DEMOTE_MANAGER(params[0]));
      }
    }
  }

  private void sendOtherMemberInfo(PlayerAlliance alliance, Player memberToSend) {
    for (PlayerAllianceMember allianceMember : alliance.getMembers()) {

      if (!allianceMember.isOnline() || memberToSend.getObjectId() == allianceMember.getObjectId())
        continue;
      PacketSendUtility.sendPacket(memberToSend,
          (AionServerPacket) new SM_ALLIANCE_MEMBER_INFO(allianceMember, PlayerAllianceEvent.UPDATE));
      PacketSendUtility.sendPacket(memberToSend, (AionServerPacket) new SM_PLAYER_ID((AionObject) allianceMember));
    }
  }

  public void playerStatusInfo(Player actingMember, int status, int playerObjId) {
    String oldLeader;
    PlayerAlliance alliance = actingMember.getPlayerAlliance();

    if (alliance == null) {
      PacketSendUtility.sendMessage(actingMember, "Your alliance is null...");
    }

    switch (status) {

      case 12:
        removeMemberFromAlliance(alliance, actingMember.getObjectId(), PlayerAllianceEvent.LEAVE, new String[0]);
        break;
      case 14:
        removeMemberFromAlliance(alliance, playerObjId, PlayerAllianceEvent.BANNED,
            new String[] { actingMember.getName() });
        break;
      case 15:
        oldLeader = alliance.getCaptain().getName();
        alliance.setLeader(playerObjId);
        broadcastAllianceInfo(alliance, PlayerAllianceEvent.APPOINT_CAPTAIN,
            new String[] { oldLeader, alliance.getCaptain().getName() });
        break;
      case 19:
        PacketSendUtility.sendMessage(actingMember,
            "Readiness check is not implmeneted yet. (ID: " + playerObjId + ")");
        break;
      case 23:
        alliance.promoteViceLeader(playerObjId);
        broadcastAllianceInfo(alliance, PlayerAllianceEvent.APPOINT_VICE_CAPTAIN,
            new String[] { alliance.getPlayer(playerObjId).getName() });
        break;
      case 24:
        alliance.demoteViceLeader(playerObjId);
        broadcastAllianceInfo(alliance, PlayerAllianceEvent.DEMOTE_VICE_CAPTAIN,
            new String[] { alliance.getPlayer(playerObjId).getName() });
        break;
    }
  }

  private void removeMemberFromAlliance(PlayerAlliance alliance, int memberObjectId, PlayerAllianceEvent event,
      String... params) {
    PlayerAllianceMember allianceMember = alliance.getPlayer(memberObjectId);

    if (allianceMember == null) {
      return;
    }
    if (allianceMember.isOnline()) {

      allianceMember.getPlayer().setPlayerAlliance(null);
      PacketSendUtility.sendPacket(allianceMember.getPlayer(), (AionServerPacket) new SM_LEAVE_GROUP_MEMBER());
    }

    alliance.removeMember(memberObjectId);
    broadcastAllianceMemberInfo(alliance, allianceMember, event, params);
    removeAllianceMemberFromCache(memberObjectId);

    if (alliance.size() == 1) {

      Player player = alliance.getCaptain().getPlayer();
      removeMemberFromAlliance(alliance, alliance.getCaptainObjectId(), event, new String[0]);
      if (player != null) {
        PacketSendUtility.sendPacket(player, (AionServerPacket) SM_SYSTEM_MESSAGE.STR_PARTY_ALLIANCE_DISPERSED());
      }
    }
  }

  public void setAlliance(Player player) {
    if (!isAllianceMember(player.getObjectId())) {
      return;
    }
    PlayerAlliance alliance = getPlayerAlliance(player.getObjectId());

    if (alliance.size() == 0) {

      removeAllianceMemberFromCache(player.getObjectId());

      return;
    }
    player.setPlayerAlliance(alliance);
    cancelRemovalTask(player.getObjectId());
  }

  public void onLogin(Player player) {
    PlayerAlliance alliance = player.getPlayerAlliance();

    alliance.onPlayerLogin(player);

    PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAYER_INFO(player, false));

    PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_ALLIANCE_INFO(alliance));
    PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_SHOW_BRAND(0, 0));

    broadcastAllianceMemberInfo(alliance, player.getObjectId(), PlayerAllianceEvent.RECONNECT, new String[0]);
    sendOtherMemberInfo(alliance, player);
  }

  public void onLogout(Player player) {
    scheduleRemove(player);
  }

  public void updateAllianceUIToEvent(Player player, PlayerAllianceEvent event) {
    SM_ALLIANCE_MEMBER_INFO packet;
    PlayerAlliance alliance = player.getPlayerAlliance();
    switch (event) {

      case MOVEMENT:
      case UPDATE:
        packet = new SM_ALLIANCE_MEMBER_INFO(alliance.getPlayer(player.getObjectId()), event);
        for (PlayerAllianceMember allianceMember : alliance.getMembers()) {

          if (allianceMember.isOnline() && !player.equals(allianceMember.getPlayer())) {
            PacketSendUtility.sendPacket(allianceMember.getPlayer(), (AionServerPacket) packet);
          }
        }
        break;
    }
  }

  public void showBrand(PlayerAlliance alliance, int brandId, int targetObjectId) {
    for (PlayerAllianceMember allianceMember : alliance.getMembers()) {

      if (!allianceMember.isOnline())
        continue;
      PacketSendUtility.sendPacket(allianceMember.getPlayer(),
          (AionServerPacket) new SM_SHOW_BRAND(brandId, targetObjectId));
    }
  }

  public void doReward(PlayerAlliance alliance, Monster owner) {
    List<Player> players = new ArrayList<Player>();
    int partyLvlSum = 0;
    int highestLevel = 0;
    for (PlayerAllianceMember allianceMember : alliance.getMembers()) {

      if (!allianceMember.isOnline())
        continue;
      Player member = allianceMember.getPlayer();
      if (MathUtil.isIn3dRange((VisibleObject) member, (VisibleObject) owner, GroupConfig.GROUP_MAX_DISTANCE)) {

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

    double mod = 1.0D;
    if (players.size() == 0)
      return;
    if (players.size() > 1) {
      mod = (1 + (players.size() - 1) * 10 / 100);
    }
    expReward = (long) (expReward * mod);

    for (Player member : players) {

      long currentExp = member.getCommonData().getExp();
      long reward = expReward * member.getLevel() / partyLvlSum;
      reward *= member.getRates().getGroupXpRate();
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

    Player leader = alliance.getCaptain().getPlayer();

    if (leader == null)
      return;
    DropService.getInstance().registerDrop((Npc) owner, leader, highestLevel);
  }

  private static class SingletonHolder {
    protected static final AllianceService instance = new AllianceService();
  }
}
