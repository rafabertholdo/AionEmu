package com.aionemu.gameserver.itemengine.actions;

import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.Kisk;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.concurrent.Future;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ToyPetSpawnAction")
public class ToyPetSpawnAction extends AbstractItemAction {
  @XmlAttribute
  protected int npcid;
  @XmlAttribute
  protected int time;

  public int getNpcId() {
    return this.npcid;
  }

  public int getTime() {
    return this.time;
  }

  public boolean canAct(Player player, Item parentItem, Item targetItem) {
    if (player.getFlyState() != 0) {

      PacketSendUtility.sendPacket(player,
          (AionServerPacket) SM_SYSTEM_MESSAGE.STR_CANNOT_USE_BINDSTONE_ITEM_WHILE_FLYING);
      return false;
    }
    if (player.isInInstance()) {

      PacketSendUtility.sendPacket(player,
          (AionServerPacket) SM_SYSTEM_MESSAGE.STR_CANNOT_REGISTER_BINDSTONE_FAR_FROM_NPC);
      return false;
    }

    return true;
  }

  public void act(Player player, Item parentItem, Item targetItem) {
    SpawnEngine spawnEngine = SpawnEngine.getInstance();
    float x = player.getX();
    float y = player.getY();
    float z = player.getZ();
    byte heading = (byte) ((player.getHeading() + 60) % 120);
    int worldId = player.getWorldId();
    int instanceId = player.getInstanceId();

    SpawnTemplate spawn = spawnEngine.addNewSpawn(worldId, instanceId, this.npcid, x, y, z, heading, 0, 0, true, true);

    final Kisk kisk = spawnEngine.spawnKisk(spawn, instanceId, player);

    Future<?> task = ThreadPoolManager.getInstance().schedule(new Runnable() {

      public void run() {
        kisk.getController().onDespawn(true);
      }
    }, 7200000L);

    kisk.getController().addTask(TaskId.DESPAWN, task);

    PacketSendUtility.broadcastPacket(player, (AionServerPacket) new SM_ITEM_USAGE_ANIMATION(player.getObjectId(),
        parentItem.getObjectId(), parentItem.getItemTemplate().getTemplateId()), true);

    ItemService.decreaseItemCount(player, parentItem, 1L);
  }
}
