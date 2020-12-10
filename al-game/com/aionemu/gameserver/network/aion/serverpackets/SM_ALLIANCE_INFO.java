package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.alliance.PlayerAlliance;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;
import java.util.List;

public class SM_ALLIANCE_INFO extends AionServerPacket {
  private PlayerAlliance alliance;

  public SM_ALLIANCE_INFO(PlayerAlliance alliance) {
    this.alliance = alliance;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeH(buf, 4);
    writeD(buf, this.alliance.getObjectId());
    writeD(buf, this.alliance.getCaptainObjectId());

    List<Integer> ids = this.alliance.getViceCaptainObjectIds();
    writeD(buf, (ids.size() > 0) ? ((Integer) ids.get(0)).intValue() : 0);
    writeD(buf, (ids.size() > 1) ? ((Integer) ids.get(1)).intValue() : 0);
    writeD(buf, (ids.size() > 2) ? ((Integer) ids.get(2)).intValue() : 0);
    writeD(buf, (ids.size() > 3) ? ((Integer) ids.get(3)).intValue() : 0);

    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeC(buf, 0);

    writeD(buf, 0);
    writeD(buf, 1000);
    writeD(buf, 1);
    writeD(buf, 1001);
    writeD(buf, 2);
    writeD(buf, 1002);
    writeD(buf, 3);
    writeD(buf, 1003);

    writeD(buf, 0);
    writeH(buf, 0);
  }
}
