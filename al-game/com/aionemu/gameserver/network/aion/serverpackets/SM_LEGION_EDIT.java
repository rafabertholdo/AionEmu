package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.legion.Legion;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_LEGION_EDIT extends AionServerPacket {
  private int type;
  private Legion legion;
  private int unixTime;
  private String announcement;

  public SM_LEGION_EDIT(int type) {
    this.type = type;
  }

  public SM_LEGION_EDIT(int type, Legion legion) {
    this.type = type;
    this.legion = legion;
  }

  public SM_LEGION_EDIT(int type, int unixTime) {
    this.type = type;
    this.unixTime = unixTime;
  }

  public SM_LEGION_EDIT(int type, int unixTime, String announcement) {
    this.type = type;
    this.announcement = announcement;
    this.unixTime = unixTime;
  }

  public void writeImpl(AionConnection con, ByteBuffer buf) {
    writeC(buf, this.type);
    switch (this.type) {

      case 0:
        writeC(buf, this.legion.getLegionLevel());
        break;

      case 1:
        writeD(buf, this.legion.getLegionRank());
        break;

      case 2:
        writeC(buf, this.legion.getCenturionPermission1());
        writeC(buf, this.legion.getCenturionPermission2());
        writeC(buf, this.legion.getLegionarPermission1());
        writeC(buf, this.legion.getLegionarPermission2());
        break;

      case 3:
        writeD(buf, this.legion.getContributionPoints());
        break;

      case 5:
        writeS(buf, this.announcement);
        writeD(buf, this.unixTime);
        break;

      case 6:
        writeD(buf, this.unixTime);
        break;
    }
  }
}
