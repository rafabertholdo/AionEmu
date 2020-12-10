package com.aionemu.gameserver.model.alliance;

import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ALLIANCE_MEMBER_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.AllianceService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javolution.util.FastMap;

public class PlayerAlliance extends AionObject {
  private int captainObjectId;
  private List<Integer> viceCaptainObjectIds = new ArrayList<Integer>();

  private FastMap<Integer, PlayerAllianceMember> allianceMembers = (new FastMap()).shared();
  private FastMap<Integer, PlayerAllianceGroup> allianceGroupForMember = (new FastMap()).shared();

  private FastMap<Integer, PlayerAllianceGroup> allianceGroups = (new FastMap()).shared();

  public PlayerAlliance(int objectId, int leaderObjectId) {
    super(Integer.valueOf(objectId));
    setLeader(leaderObjectId);
  }

  public void addMember(Player member) {
    PlayerAllianceGroup group = getOpenAllianceGroup();
    PlayerAllianceMember allianceMember = new PlayerAllianceMember(member);
    group.addMember(allianceMember);

    this.allianceMembers.put(Integer.valueOf(member.getObjectId()), allianceMember);
    this.allianceGroupForMember.put(Integer.valueOf(member.getObjectId()), group);

    member.setPlayerAlliance(this);
  }

  private PlayerAllianceGroup getOpenAllianceGroup() {
    for (int i = 1000; i <= 1004; i++) {

      PlayerAllianceGroup group = (PlayerAllianceGroup) this.allianceGroups.get(Integer.valueOf(i));

      if (group == null) {

        group = new PlayerAllianceGroup(this);
        group.setAllianceId(i);
        this.allianceGroups.put(Integer.valueOf(i), group);
        return group;
      }

      if (group.getMembers().size() < 6)
        return group;
    }
    throw new RuntimeException("All Alliance Groups Full.");
  }

  public void removeMember(int memberObjectId) {
    ((PlayerAllianceGroup) this.allianceGroupForMember.get(Integer.valueOf(memberObjectId)))
        .removeMember(memberObjectId);
    this.allianceGroupForMember.remove(Integer.valueOf(memberObjectId));
    this.allianceMembers.remove(Integer.valueOf(memberObjectId));

    if (this.viceCaptainObjectIds.contains(Integer.valueOf(memberObjectId))) {
      this.viceCaptainObjectIds.remove(this.viceCaptainObjectIds.indexOf(Integer.valueOf(memberObjectId)));
    }

    if (memberObjectId == this.captainObjectId) {

      if (this.viceCaptainObjectIds.size() > 0) {

        int newCaptain = ((Integer) this.viceCaptainObjectIds.get(0)).intValue();
        this.viceCaptainObjectIds.remove(this.viceCaptainObjectIds.indexOf(Integer.valueOf(newCaptain)));
        this.captainObjectId = newCaptain;
      } else if (this.allianceMembers.size() != 0) {

        PlayerAllianceMember newCaptain = this.allianceMembers.values().iterator().next();
        this.captainObjectId = newCaptain.getObjectId();
      }
    }

    AllianceService.getInstance().broadcastAllianceInfo(this, PlayerAllianceEvent.UPDATE, new String[0]);
  }

  public void setLeader(int newLeaderObjectId) {
    if (this.viceCaptainObjectIds.contains(Integer.valueOf(newLeaderObjectId))) {

      this.viceCaptainObjectIds.remove(this.viceCaptainObjectIds.indexOf(Integer.valueOf(newLeaderObjectId)));
      this.viceCaptainObjectIds.add(Integer.valueOf(this.captainObjectId));
    }
    this.captainObjectId = newLeaderObjectId;
  }

  public void promoteViceLeader(int viceLeaderObjectId) {
    this.viceCaptainObjectIds.add(Integer.valueOf(viceLeaderObjectId));
  }

  public void demoteViceLeader(int viceLeaderObjectId) {
    this.viceCaptainObjectIds.remove(this.viceCaptainObjectIds.indexOf(Integer.valueOf(viceLeaderObjectId)));
  }

  public PlayerAllianceMember getCaptain() {
    return getPlayer(getCaptainObjectId());
  }

  public int getCaptainObjectId() {
    return this.captainObjectId;
  }

  public List<Integer> getViceCaptainObjectIds() {
    return this.viceCaptainObjectIds;
  }

  public int getAllianceIdFor(int playerObjectId) {
    if (!this.allianceGroupForMember.containsKey(Integer.valueOf(playerObjectId))) {
      return 0;
    }
    return ((PlayerAllianceGroup) this.allianceGroupForMember.get(Integer.valueOf(playerObjectId))).getAllianceId();
  }

  public PlayerAllianceMember getPlayer(int playerObjectId) {
    return (PlayerAllianceMember) this.allianceMembers.get(Integer.valueOf(playerObjectId));
  }

  public int size() {
    return getMembers().size();
  }

  public boolean isFull() {
    return (size() >= 24);
  }

  public Collection<PlayerAllianceMember> getMembers() {
    return this.allianceMembers.values();
  }

  public boolean hasAuthority(int playerObjectId) {
    return (playerObjectId == this.captainObjectId
        || this.viceCaptainObjectIds.contains(Integer.valueOf(playerObjectId)));
  }

  public String getName() {
    return "Player Alliance";
  }

  public void swapPlayers(int playerObjectId1, int playerObjectId2) {
    PlayerAllianceGroup group1 = (PlayerAllianceGroup) this.allianceGroupForMember
        .get(Integer.valueOf(playerObjectId1));
    PlayerAllianceGroup group2 = (PlayerAllianceGroup) this.allianceGroupForMember
        .get(Integer.valueOf(playerObjectId2));

    PlayerAllianceMember player1 = group1.removeMember(playerObjectId1);
    PlayerAllianceMember player2 = group2.removeMember(playerObjectId2);

    group1.addMember(player2);
    group2.addMember(player1);

    this.allianceGroupForMember.put(Integer.valueOf(playerObjectId1), group2);
    this.allianceGroupForMember.put(Integer.valueOf(playerObjectId2), group1);
  }

  public void setAllianceGroupFor(int memberObjectId, int allianceGroupId) {
    PlayerAllianceGroup leavingGroup = (PlayerAllianceGroup) this.allianceGroupForMember
        .get(Integer.valueOf(memberObjectId));
    PlayerAllianceMember member = leavingGroup.getMemberById(memberObjectId);
    leavingGroup.removeMember(memberObjectId);

    PlayerAllianceGroup group = (PlayerAllianceGroup) this.allianceGroups.get(Integer.valueOf(allianceGroupId));

    if (group == null) {

      group = new PlayerAllianceGroup(this);
      group.setAllianceId(allianceGroupId);
      this.allianceGroups.put(Integer.valueOf(allianceGroupId), group);
    }

    group.addMember(member);
    this.allianceGroupForMember.put(Integer.valueOf(memberObjectId), group);
  }

  public PlayerAllianceGroup getPlayerAllianceGroupForMember(int objectId) {
    return (PlayerAllianceGroup) this.allianceGroupForMember.get(Integer.valueOf(objectId));
  }

  public void onPlayerLogin(Player player) {
    ((PlayerAllianceMember) this.allianceMembers.get(Integer.valueOf(player.getObjectId()))).onLogin(player);
  }

  public void onPlayerDisconnect(Player player) {
    PlayerAllianceMember allianceMember = (PlayerAllianceMember) this.allianceMembers
        .get(Integer.valueOf(player.getObjectId()));
    allianceMember.onDisconnect();

    for (PlayerAllianceMember member : this.allianceMembers.values()) {

      if (member.isOnline()) {

        PacketSendUtility.sendPacket(member.getPlayer(),
            (AionServerPacket) SM_SYSTEM_MESSAGE.STR_FORCE_HE_BECOME_OFFLINE(player.getName()));
        PacketSendUtility.sendPacket(member.getPlayer(),
            (AionServerPacket) new SM_ALLIANCE_MEMBER_INFO(allianceMember, PlayerAllianceEvent.DISCONNECTED));
      }
    }
  }

  public Collection<PlayerAllianceMember> getMembersForGroup(int playerObjectId) {
    PlayerAllianceGroup group = (PlayerAllianceGroup) this.allianceGroupForMember.get(Integer.valueOf(playerObjectId));

    if (group == null)
      return (new FastMap()).values();
    return group.getMembers();
  }
}
