package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.configs.main.GSConfig;
import com.aionemu.gameserver.configs.network.NetworkConfig;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.services.ChatService;
import java.nio.ByteBuffer;



































public class SM_VERSION_CHECK
  extends AionServerPacket
{
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeC(buf, 0);
    writeC(buf, NetworkConfig.GAMESERVER_ID);
    writeD(buf, 100525);
    writeD(buf, 100518);
    writeD(buf, 0);
    writeD(buf, 100504);
    writeD(buf, 1278504349);
    writeC(buf, 0);
    writeC(buf, GSConfig.SERVER_COUNTRY_CODE);
    writeC(buf, 0);
    writeC(buf, GSConfig.SERVER_MODE);
    writeD(buf, (int)(System.currentTimeMillis() / 1000L));
    writeH(buf, 350);
    writeH(buf, 2561);
    writeH(buf, 2561);
    writeH(buf, 522);
    writeC(buf, 0);
    writeC(buf, 1);
    writeC(buf, 0);
    writeC(buf, 0);
    writeB(buf, ChatService.getIp());
    writeH(buf, ChatService.getPort());
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_VERSION_CHECK.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
