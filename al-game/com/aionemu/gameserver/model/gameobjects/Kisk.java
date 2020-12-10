package com.aionemu.gameserver.model.gameobjects;

import com.aionemu.gameserver.controllers.NpcController;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.alliance.PlayerAllianceMember;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.legion.Legion;
import com.aionemu.gameserver.model.templates.NpcTemplate;
import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
import com.aionemu.gameserver.model.templates.stats.KiskStatsTemplate;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_KISK_UPDATE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.ArrayList;
import java.util.List;

public class Kisk extends Npc {
  private String ownerName;
  private Legion ownerLegion;
  private Race ownerRace;
  private int ownerObjectId;
  private KiskStatsTemplate kiskStatsTemplate;
  private int remainingResurrections;
  private long kiskSpawnTime;
  private final List<Player> kiskMembers = new ArrayList<Player>();
  private int currentMemberCount = 0;

  public Kisk(int objId, NpcController controller, SpawnTemplate spawnTemplate, NpcTemplate npcTemplate, Player owner) {
    super(objId, controller, spawnTemplate, (VisibleObjectTemplate) npcTemplate);

    this.kiskStatsTemplate = npcTemplate.getKiskStatsTemplate();
    if (this.kiskStatsTemplate == null) {
      this.kiskStatsTemplate = new KiskStatsTemplate();
    }
    this.remainingResurrections = this.kiskStatsTemplate.getMaxResurrects();
    this.kiskSpawnTime = System.currentTimeMillis() / 1000L;

    this.ownerName = owner.getName();
    this.ownerLegion = owner.getLegion();
    this.ownerRace = owner.getCommonData().getRace();
    this.ownerObjectId = owner.getObjectId();
  }

  public boolean isAggressiveTo(Creature creature) {
    if (creature instanceof Player) {

      Player player = (Player) creature;
      if (player.getCommonData().getRace() != this.ownerRace)
        return true;
    }
    return false;
  }

  protected boolean isEnemyNpc(Npc npc) {
    return (npc instanceof Monster || npc.isAggressiveTo(this));
  }

  protected boolean isEnemyPlayer(Player player) {
    return (player.getCommonData().getRace() != this.ownerRace);
  }

  public NpcObjectType getNpcObjectType() {
    return NpcObjectType.NORMAL;
  }

  public int getUseMask() {
    return this.kiskStatsTemplate.getUseMask();
  }

  public List<Player> getCurrentMemberList() {
    return this.kiskMembers;
  }

  public int getCurrentMemberCount() {
    return this.currentMemberCount;
  }

  public int getMaxMembers() {
    return this.kiskStatsTemplate.getMaxMembers();
  }

  public int getRemainingResurrects() {
    return this.remainingResurrections;
  }

  public int getMaxRessurects() {
    return this.kiskStatsTemplate.getMaxResurrects();
  }

  public int getRemainingLifetime() {
    long timeElapsed = System.currentTimeMillis() / 1000L - this.kiskSpawnTime;
    int timeRemaining = (int) (7200L - timeElapsed);
    return (timeRemaining > 0) ? timeRemaining : 0;
  }

  public boolean canBind(Player player) {
    String playerName = player.getName();

    if (playerName != this.ownerName) {
      boolean isMember;

      switch (getUseMask()) {

        case 1:
          if (this.ownerRace == player.getCommonData().getRace()) {
            return false;
          }
          break;
        case 2:
          if (this.ownerLegion == null)
            return false;
          if (!this.ownerLegion.isMember(player.getObjectId())) {
            return false;
          }
          break;
        case 3:
          return false;

        case 4:
          isMember = false;
          if (player.isInGroup()) {

            for (Player member : player.getPlayerGroup().getMembers()) {
              if (member.getObjectId() == this.ownerObjectId) {
                isMember = true;
              }
            }

          } else if (player.isInAlliance()) {

            for (PlayerAllianceMember allianceMember : player.getPlayerAlliance()
                .getMembersForGroup(player.getObjectId())) {

              if (allianceMember.getObjectId() == this.ownerObjectId) {
                isMember = true;
              }
            }
          }
          if (!isMember) {
            return false;
          }
          break;
        case 5:
          if (!player.isInAlliance() || player.getPlayerAlliance().getPlayer(this.ownerObjectId) == null) {
            return false;
          }
          break;
        default:
          return false;
      }

    }
    if (getCurrentMemberCount() >= getMaxMembers()) {
      return false;
    }
    return true;
  }

  public void addPlayer(Player player) {
    this.kiskMembers.add(player);
    player.setKisk(this);
    this.currentMemberCount++;
    broadcastKiskUpdate();
  }

  public void reAddPlayer(Player player) {
    this.kiskMembers.add(player);
    player.setKisk(this);
    PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_KISK_UPDATE(this));
  }

  public void removePlayer(Player player) {
    this.kiskMembers.remove(player);
    player.setKisk(null);
    this.currentMemberCount--;
    broadcastKiskUpdate();
  }

  private void broadcastKiskUpdate() {
    for (Player member : this.kiskMembers) {

      if (!getKnownList().knowns((AionObject) member))
        PacketSendUtility.sendPacket(member, (AionServerPacket) new SM_KISK_UPDATE(this));
    }
    for (VisibleObject obj : getKnownList().getKnownObjects().values()) {

      if (obj instanceof Player) {

        Player target = (Player) obj;
        if (target.getCommonData().getRace() == this.ownerRace) {
          PacketSendUtility.sendPacket(target, (AionServerPacket) new SM_KISK_UPDATE(this));
        }
      }
    }
  }

  public void broadcastPacket(SM_SYSTEM_MESSAGE message) {
    for (Player member : this.kiskMembers) {

      if (member != null) {
        PacketSendUtility.sendPacket(member, (AionServerPacket) message);
      }
    }
  }

  public void resurrectionUsed(Player player) {
    this.remainingResurrections--;
    if (this.remainingResurrections <= 0) {

      player.getKisk().getController().onDespawn(true);
    } else {

      broadcastKiskUpdate();
    }
  }

  public Race getOwnerRace() {
    return this.ownerRace;
  }

  public String getOwnerName() {
    return this.ownerName;
  }

  public int getOwnerObjectId() {
    return this.ownerObjectId;
  }
}
