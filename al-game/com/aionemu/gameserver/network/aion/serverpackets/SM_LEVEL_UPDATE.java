package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_LEVEL_UPDATE extends AionServerPacket {
  private int targetObjectId;
  private int effect;
  private int level;

  public SM_LEVEL_UPDATE(int targetObjectId, int effect, int level) {
    this.targetObjectId = targetObjectId;
    this.effect = effect;
    this.level = level;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.targetObjectId);
    writeH(buf, this.effect);
    writeH(buf, this.level);
    writeH(buf, 0);
  }
}
