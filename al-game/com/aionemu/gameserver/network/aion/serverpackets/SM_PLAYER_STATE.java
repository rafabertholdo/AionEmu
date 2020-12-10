package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_PLAYER_STATE extends AionServerPacket {
  private int playerObjId;
  private int visualState;
  private int seeState;

  public SM_PLAYER_STATE(Player player) {
    this.playerObjId = player.getObjectId();
    this.visualState = player.getVisualState();
    this.seeState = player.getSeeState();
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.playerObjId);
    writeC(buf, this.visualState);
    writeC(buf, this.seeState);
    if (this.visualState == 64) {
      writeC(buf, 1);
    } else {
      writeC(buf, 0);
    }
  }
}
