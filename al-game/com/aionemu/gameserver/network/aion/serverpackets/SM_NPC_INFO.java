package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.NpcType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Kisk;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.Summon;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.model.items.ItemSlot;
import com.aionemu.gameserver.model.items.NpcEquippedGear;
import com.aionemu.gameserver.model.templates.NpcTemplate;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;
import java.util.Map;

public class SM_NPC_INFO extends AionServerPacket {
  private Creature npc;
  private NpcTemplate npcTemplate;
  private int npcId;
  private int masterObjId;
  private String masterName = "";
  private float speed = 0.3F;

  private final int npcTypeId;

  public SM_NPC_INFO(Npc npc, Player player) {
    this.npc = (Creature) npc;
    this.npcTemplate = npc.getObjectTemplate();
    this.npcTypeId = player.isAggroIconTo(npc.getTribe()) ? NpcType.AGGRESSIVE.getId()
        : this.npcTemplate.getNpcType().getId();

    this.npcId = npc.getNpcId();
  }

  public SM_NPC_INFO(Player player, Kisk kisk) {
    this.npc = (Creature) kisk;
    this.npcTypeId = kisk.isAggroFrom(player) ? NpcType.ATTACKABLE.getId() : NpcType.NON_ATTACKABLE.getId();

    this.npcTemplate = kisk.getObjectTemplate();
    this.npcId = kisk.getNpcId();

    this.masterObjId = kisk.getOwnerObjectId();
    this.masterName = kisk.getOwnerName();
  }

  public SM_NPC_INFO(Summon summon) {
    this.npc = (Creature) summon;
    this.npcTemplate = summon.getObjectTemplate();
    this.npcTypeId = this.npcTemplate.getNpcType().getId();
    this.npcId = summon.getNpcId();
    Player owner = summon.getMaster();
    if (owner != null) {

      this.masterObjId = owner.getObjectId();
      this.masterName = owner.getName();
      this.speed = owner.getGameStats().getCurrentStat(StatEnum.SPEED) / 1000.0F;
    } else {

      this.masterName = "LOST";
    }
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeF(buf, this.npc.getX());
    writeF(buf, this.npc.getY());
    writeF(buf, this.npc.getZ());
    writeD(buf, this.npc.getObjectId());
    writeD(buf, this.npcId);
    writeD(buf, this.npcId);

    writeC(buf, this.npcTypeId);

    writeH(buf, this.npc.getState());

    writeC(buf, this.npc.getHeading());
    writeD(buf, this.npcTemplate.getNameId());
    writeD(buf, this.npcTemplate.getTitleId());

    writeH(buf, 0);
    writeC(buf, 0);
    writeD(buf, 0);

    writeD(buf, this.masterObjId);
    writeS(buf, this.masterName);

    int maxHp = this.npc.getLifeStats().getMaxHp();
    int currHp = this.npc.getLifeStats().getCurrentHp();
    writeC(buf, 100 * currHp / maxHp);
    writeD(buf, this.npc.getGameStats().getCurrentStat(StatEnum.MAXHP));
    writeC(buf, this.npc.getLevel());

    NpcEquippedGear gear = this.npcTemplate.getEquipment();
    if (gear == null) {
      writeH(buf, 0);
    } else {

      writeH(buf, gear.getItemsMask());
      for (Map.Entry<ItemSlot, ItemTemplate> item : (Iterable<Map.Entry<ItemSlot, ItemTemplate>>) gear.getItems()) {

        writeD(buf, ((ItemTemplate) item.getValue()).getTemplateId());
        writeD(buf, 0);
        writeD(buf, 0);
        writeH(buf, 0);
      }
    }

    writeF(buf, 1.5F);
    writeF(buf, this.npcTemplate.getHeight());
    writeF(buf, this.npc.getMoveController().getSpeed());

    writeH(buf, 2000);
    writeH(buf, 2000);

    writeC(buf, 0);

    writeF(buf, this.npc.getX());
    writeF(buf, this.npc.getY());
    writeF(buf, this.npc.getZ());
    writeC(buf, 0);
    SpawnTemplate spawn = this.npc.getSpawn();
    if (spawn == null) {
      writeH(buf, 0);
    } else {
      writeH(buf, spawn.getStaticid());
    }
    writeC(buf, 0);
    writeC(buf, 0);
    writeC(buf, 0);
    writeC(buf, 0);
    writeC(buf, 0);
    writeC(buf, 0);
    writeC(buf, 0);
    writeC(buf, 0);
    writeC(buf, this.npc.getVisualState());

    writeH(buf, this.npc.getNpcObjectType().getId());
    writeC(buf, 0);
    writeD(buf, (this.npc.getTarget() == null) ? 0 : this.npc.getTarget().getObjectId());
  }
}
