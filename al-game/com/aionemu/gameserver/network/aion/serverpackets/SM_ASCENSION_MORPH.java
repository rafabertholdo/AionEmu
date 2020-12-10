package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_ASCENSION_MORPH extends AionServerPacket {
  private int inascension;

  public SM_ASCENSION_MORPH(int inascension) {
    this.inascension = inascension;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeC(buf, this.inascension);
  }
}
