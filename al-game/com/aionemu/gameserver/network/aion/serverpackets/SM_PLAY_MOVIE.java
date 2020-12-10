package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_PLAY_MOVIE extends AionServerPacket {
  private int type = 1;
  private int movieId = 0;

  public SM_PLAY_MOVIE(int type, int movieId) {
    this.type = type;
    this.movieId = movieId;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeC(buf, this.type);
    writeD(buf, 0);
    writeD(buf, 0);
    writeH(buf, this.movieId);
    writeD(buf, 0);
  }
}
