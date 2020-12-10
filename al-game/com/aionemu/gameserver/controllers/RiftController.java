package com.aionemu.gameserver.controllers;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_RIFT_ANNOUNCE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_RIFT_STATUS;
import com.aionemu.gameserver.services.RespawnService;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.spawnengine.RiftSpawnManager;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapInstance;

public class RiftController extends NpcController {
  private boolean isMaster = false;
  private SpawnTemplate slaveSpawnTemplate;
  private Npc slave;
  private Integer maxEntries;
  private Integer maxLevel;
  private int usedEntries;
  private boolean isAccepting;
  private RiftSpawnManager.RiftEnum riftTemplate;

  public RiftController(Npc slave, RiftSpawnManager.RiftEnum riftTemplate) {
    this.riftTemplate = riftTemplate;
    if (slave != null) {

      this.slave = slave;
      this.slaveSpawnTemplate = slave.getSpawn();
      this.maxEntries = Integer.valueOf(riftTemplate.getEntries());
      this.maxLevel = Integer.valueOf(riftTemplate.getMaxLevel());

      this.isMaster = true;
      this.isAccepting = true;
    }
  }

  public void onDialogRequest(Player player) {
    if (!this.isMaster && !this.isAccepting) {
      return;
    }
    RequestResponseHandler responseHandler = new RequestResponseHandler((Creature) getOwner()) {

      public void acceptRequest(Creature requester, Player responder) {
        if (!RiftController.this.isAccepting) {
          return;
        }
        int worldId = RiftController.this.slaveSpawnTemplate.getWorldId();
        float x = RiftController.this.slaveSpawnTemplate.getX();
        float y = RiftController.this.slaveSpawnTemplate.getY();
        float z = RiftController.this.slaveSpawnTemplate.getZ();

        TeleportService.teleportTo(responder, worldId, x, y, z, 0);
        RiftController.this.usedEntries++;

        if (RiftController.this.usedEntries >= RiftController.this.maxEntries.intValue()) {

          RiftController.this.isAccepting = false;

          RespawnService.scheduleDecayTask(RiftController.this.getOwner());
          RespawnService.scheduleDecayTask(RiftController.this.slave);
        }
        PacketSendUtility.broadcastPacket((VisibleObject) RiftController.this.getOwner(),
            (AionServerPacket) new SM_RIFT_STATUS(RiftController.this.getOwner().getObjectId(),
                RiftController.this.usedEntries, RiftController.this.maxEntries.intValue(),
                RiftController.this.maxLevel.intValue()));
      }

      public void denyRequest(Creature requester, Player responder) {
      }
    };
    boolean requested = player.getResponseRequester().putRequest(160019, responseHandler);
    if (requested) {
      PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_QUESTION_WINDOW(160019, 0, new Object[0]));
    }
  }

  public void see(VisibleObject object) {
    if (!this.isMaster) {
      return;
    }
    if (object instanceof Player) {
      PacketSendUtility.sendPacket((Player) object, (AionServerPacket) new SM_RIFT_STATUS(getOwner().getObjectId(),
          this.usedEntries, this.maxEntries.intValue(), this.maxLevel.intValue()));
    }
  }

  public void sendMessage(Player activePlayer) {
    if (this.isMaster && getOwner().isSpawned()) {
      PacketSendUtility.sendPacket(activePlayer,
          (AionServerPacket) new SM_RIFT_ANNOUNCE(this.riftTemplate.getDestination()));
    }
  }

  public void sendAnnounce() {
    if (this.isMaster && getOwner().isSpawned()) {

      WorldMapInstance worldInstance = getOwner().getPosition().getMapRegion().getParent();
      for (Player player : worldInstance.getAllWorldMapPlayers()) {

        if (player.isSpawned())
          sendMessage(player);
      }
    }
  }
}
