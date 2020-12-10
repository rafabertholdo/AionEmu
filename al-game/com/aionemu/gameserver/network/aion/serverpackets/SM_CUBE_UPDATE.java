package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_CUBE_UPDATE extends AionServerPacket {
  private Player player;
  private int cubeType;
  private int advancedSlots;

  public SM_CUBE_UPDATE(Player player, int cubeType, int advancedSlots) {
    this.player = player;
    this.cubeType = cubeType;
    this.advancedSlots = advancedSlots;
  }

  public SM_CUBE_UPDATE(Player player, int cubeType) {
    this.player = player;
    this.cubeType = cubeType;
    this.advancedSlots = 0;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeC(buf, this.cubeType);
    writeC(buf, this.advancedSlots);
    switch (this.cubeType) {

      case 0:
        writeD(buf, this.player.getInventory().size());
        writeC(buf, this.player.getCubeSize());
        writeC(buf, 0);
        writeC(buf, 0);
        break;
    }
  }
}
