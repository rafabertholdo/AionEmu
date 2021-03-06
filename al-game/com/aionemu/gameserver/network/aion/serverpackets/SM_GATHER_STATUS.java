package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_GATHER_STATUS extends AionServerPacket {
  private int status;
  private int playerobjid;
  private int gatherableobjid;

  public SM_GATHER_STATUS(int playerobjid, int gatherableobjid, int status) {
    this.playerobjid = playerobjid;
    this.gatherableobjid = gatherableobjid;
    this.status = status;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.playerobjid);
    writeD(buf, this.gatherableobjid);
    writeH(buf, 0);
    writeC(buf, this.status);
  }
}
