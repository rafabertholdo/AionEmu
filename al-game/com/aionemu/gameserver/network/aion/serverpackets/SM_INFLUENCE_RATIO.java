package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.siege.Influence;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.services.SiegeService;
import java.nio.ByteBuffer;

public class SM_INFLUENCE_RATIO extends AionServerPacket {
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    Influence inf = Influence.getInstance();

    writeD(buf, SiegeService.getInstance().getSiegeTime());
    writeF(buf, inf.getElyos());
    writeF(buf, inf.getAsmos());
    writeF(buf, inf.getBalaur());

    writeH(buf, 1);

    writeD(buf, 400010000);
    writeF(buf, inf.getElyos());
    writeF(buf, inf.getAsmos());
    writeF(buf, inf.getBalaur());
  }
}
