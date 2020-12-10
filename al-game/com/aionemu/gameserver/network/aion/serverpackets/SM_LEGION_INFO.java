package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.legion.Legion;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.Map;

public class SM_LEGION_INFO extends AionServerPacket {
  private Legion legion;

  public SM_LEGION_INFO(Legion legion) {
    this.legion = legion;
  }

  public void writeImpl(AionConnection con, ByteBuffer buf) {
    writeS(buf, this.legion.getLegionName());
    writeC(buf, this.legion.getLegionLevel());
    writeD(buf, this.legion.getLegionRank());
    writeC(buf, this.legion.getCenturionPermission1());
    writeC(buf, this.legion.getCenturionPermission2());
    writeC(buf, this.legion.getLegionarPermission1());
    writeC(buf, this.legion.getLegionarPermission2());
    writeD(buf, this.legion.getContributionPoints());
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);

    Map<Timestamp, String> announcementList = this.legion.getAnnouncementList().descendingMap();

    int i = 0;
    for (Timestamp unixTime : announcementList.keySet()) {

      writeS(buf, announcementList.get(unixTime));
      writeD(buf, (int) (unixTime.getTime() / 1000L));
      i++;
      if (i >= 7) {
        break;
      }
    }
    writeH(buf, 0);
  }
}
