package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.teleport.TeleporterTemplate;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

public class SM_TELEPORT_MAP extends AionServerPacket {
  private int targetObjectId;
  private Player player;
  private TeleporterTemplate teleport;
  public Npc npc;
  private static final Logger log = Logger.getLogger(SM_TELEPORT_MAP.class);

  public SM_TELEPORT_MAP(Player player, int targetObjectId, TeleporterTemplate teleport) {
    this.player = player;
    this.targetObjectId = targetObjectId;
    this.npc = (Npc) World.getInstance().findAionObject(targetObjectId);
    this.teleport = teleport;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    if (this.teleport != null && this.teleport.getNpcId() != 0 && this.teleport.getTeleportId() != 0) {

      writeD(buf, this.targetObjectId);
      writeH(buf, this.teleport.getTeleportId());
    } else {

      PacketSendUtility.sendMessage(this.player,
          "Missing info at npc_teleporter.xml with npcid: " + this.npc.getNpcId());
      log.info(
          String.format("Missing teleport info with npcid: %d", new Object[] { Integer.valueOf(this.npc.getNpcId()) }));
    }
  }
}
