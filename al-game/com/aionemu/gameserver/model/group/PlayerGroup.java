package com.aionemu.gameserver.model.group;

import com.aionemu.commons.objects.filter.ObjectFilter;
import com.aionemu.gameserver.configs.main.GroupConfig;
import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_GROUP_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_GROUP_MEMBER_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEAVE_GROUP_MEMBER;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.Collection;
import javolution.util.FastMap;

public class PlayerGroup extends AionObject {
  private LootGroupRules lootGroupRules = new LootGroupRules();

  private Player groupLeader;

  private FastMap<Integer, Player> groupMembers = (new FastMap()).shared();

  private int RoundRobinNr = 0;

  public PlayerGroup(int groupId, Player groupleader) {
    super(Integer.valueOf(groupId));
    this.groupMembers.put(Integer.valueOf(groupleader.getObjectId()), groupleader);
    setGroupLeader(groupleader);
    groupleader.setPlayerGroup(this);
    PacketSendUtility.sendPacket(this.groupLeader, (AionServerPacket) new SM_GROUP_INFO(this));
  }

  public int getGroupId() {
    return getObjectId();
  }

  public Player getGroupLeader() {
    return this.groupLeader;
  }

  public void setGroupLeader(Player groupLeader) {
    this.groupLeader = groupLeader;
  }

  public void addPlayerToGroup(Player newComer) {
    this.groupMembers.put(Integer.valueOf(newComer.getObjectId()), newComer);
    newComer.setPlayerGroup(this);
    PacketSendUtility.sendPacket(newComer, (AionServerPacket) new SM_GROUP_INFO(this));
    updateGroupUIToEvent(newComer, GroupEvent.ENTER);
  }

  public int getRoundRobinMember(Npc npc) {
    this.RoundRobinNr = ++this.RoundRobinNr % size();
    int i = 0;
    for (Player player : getMembers()) {

      if (i == this.RoundRobinNr) {

        if (MathUtil.isIn3dRange((VisibleObject) player, (VisibleObject) npc, GroupConfig.GROUP_MAX_DISTANCE)) {
          return player.getObjectId();
        }

        return 0;
      }

      i++;
    }
    return 0;
  }

  public void removePlayerFromGroup(Player player) {
    this.groupMembers.remove(Integer.valueOf(player.getObjectId()));
    player.setPlayerGroup(null);
    updateGroupUIToEvent(player, GroupEvent.LEAVE);

    PacketSendUtility.broadcastPacket(player, (AionServerPacket) new SM_LEAVE_GROUP_MEMBER(), true,
        new ObjectFilter<Player>() {
          public boolean acceptObject(Player object) {
            return (object.getPlayerGroup() == null);
          }
        });
  }

  public void disband() {
    this.groupMembers.clear();
  }

  public void onGroupMemberLogIn(Player player) {
    this.groupMembers.remove(Integer.valueOf(player.getObjectId()));
    this.groupMembers.put(Integer.valueOf(player.getObjectId()), player);
  }

  public boolean isFull() {
    return (this.groupMembers.size() == 6);
  }

  public Collection<Player> getMembers() {
    return this.groupMembers.values();
  }

  public Collection<Integer> getMemberObjIds() {
    return this.groupMembers.keySet();
  }

  public int size() {
    return this.groupMembers.size();
  }

  public LootGroupRules getLootGroupRules() {
    return this.lootGroupRules;
  }

  public void setLootGroupRules(LootGroupRules lgr) {
    this.lootGroupRules = lgr;
    for (Player member : this.groupMembers.values()) {
      PacketSendUtility.sendPacket(member, (AionServerPacket) new SM_GROUP_INFO(this));
    }
  }

  public void updateGroupUIToEvent(Player subjective, GroupEvent groupEvent) {
    boolean changeleader;
    switch (groupEvent) {

      case CHANGELEADER:
        for (Player member : getMembers()) {

          PacketSendUtility.sendPacket(member, (AionServerPacket) new SM_GROUP_INFO(this));
          if (subjective.equals(member))
            PacketSendUtility.sendPacket(member, (AionServerPacket) SM_SYSTEM_MESSAGE.CHANGE_GROUP_LEADER());
          PacketSendUtility.sendPacket(member,
              (AionServerPacket) new SM_GROUP_MEMBER_INFO(this, subjective, groupEvent));
        }
        return;

      case LEAVE:
        changeleader = false;
        if (subjective == getGroupLeader()) {

          setGroupLeader(getMembers().iterator().next());
          changeleader = true;
        }
        for (Player member : getMembers()) {

          if (changeleader) {

            PacketSendUtility.sendPacket(member, (AionServerPacket) new SM_GROUP_INFO(this));
            PacketSendUtility.sendPacket(member, (AionServerPacket) SM_SYSTEM_MESSAGE.CHANGE_GROUP_LEADER());
          }
          if (!subjective.equals(member))
            PacketSendUtility.sendPacket(member,
                (AionServerPacket) new SM_GROUP_MEMBER_INFO(this, subjective, groupEvent));
          if (size() > 1)
            PacketSendUtility.sendPacket(member,
                (AionServerPacket) SM_SYSTEM_MESSAGE.MEMBER_LEFT_GROUP(subjective.getName()));
        }
        eventToSubjective(subjective, GroupEvent.LEAVE);
        return;

      case ENTER:
        eventToSubjective(subjective, GroupEvent.ENTER);
        for (Player member : getMembers()) {

          if (!subjective.equals(member)) {
            PacketSendUtility.sendPacket(member,
                (AionServerPacket) new SM_GROUP_MEMBER_INFO(this, subjective, groupEvent));
          }
        }
        return;
    }

    for (Player member : getMembers()) {

      if (!subjective.equals(member)) {
        PacketSendUtility.sendPacket(member, (AionServerPacket) new SM_GROUP_MEMBER_INFO(this, subjective, groupEvent));
      }
    }
  }

  private void eventToSubjective(Player subjective, GroupEvent groupEvent) {
    for (Player member : getMembers()) {

      if (!subjective.equals(member))
        PacketSendUtility.sendPacket(subjective, (AionServerPacket) new SM_GROUP_MEMBER_INFO(this, member, groupEvent));
    }
    if (groupEvent == GroupEvent.LEAVE) {
      PacketSendUtility.sendPacket(subjective, (AionServerPacket) SM_SYSTEM_MESSAGE.YOU_LEFT_GROUP());
    }
  }

  public String getName() {
    return "Player Group";
  }
}
