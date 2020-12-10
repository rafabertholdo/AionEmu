package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_LEGION_SEND_EMBLEM extends AionServerPacket {
  private int legionId;
  private int emblemId;
  private int color_r;
  private int color_g;
  private int color_b;
  private String legionName;

  public SM_LEGION_SEND_EMBLEM(int legionId, int emblemId, int color_r, int color_g, int color_b, String legionName) {
    this.legionId = legionId;
    this.emblemId = emblemId;
    this.color_r = color_r;
    this.color_g = color_g;
    this.color_b = color_b;
    this.legionName = legionName;
  }

  public void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.legionId);
    writeH(buf, this.emblemId);
    writeD(buf, 0);
    writeC(buf, 255);
    writeC(buf, this.color_r);
    writeC(buf, this.color_g);
    writeC(buf, this.color_b);
    writeS(buf, this.legionName);
    writeC(buf, 1);
  }
}
