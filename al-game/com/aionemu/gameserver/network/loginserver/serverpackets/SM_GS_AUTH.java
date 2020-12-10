package com.aionemu.gameserver.network.loginserver.serverpackets;

import com.aionemu.commons.network.IPRange;
import com.aionemu.gameserver.configs.network.IPConfig;
import com.aionemu.gameserver.configs.network.NetworkConfig;
import com.aionemu.gameserver.network.loginserver.LoginServerConnection;
import com.aionemu.gameserver.network.loginserver.LsServerPacket;
import java.nio.ByteBuffer;
import java.util.List;




























public class SM_GS_AUTH
  extends LsServerPacket
{
  public SM_GS_AUTH() {
    super(0);
  }





  
  protected void writeImpl(LoginServerConnection con, ByteBuffer buf) {
    writeC(buf, getOpcode());
    writeC(buf, NetworkConfig.GAMESERVER_ID);
    writeC(buf, (IPConfig.getDefaultAddress()).length);
    writeB(buf, IPConfig.getDefaultAddress());
    
    List<IPRange> ranges = IPConfig.getRanges();
    int size = ranges.size();
    writeD(buf, size);
    for (int i = 0; i < size; i++) {
      
      IPRange ipRange = ranges.get(i);
      byte[] min = ipRange.getMinAsByteArray();
      byte[] max = ipRange.getMaxAsByteArray();
      writeC(buf, min.length);
      writeB(buf, min);
      writeC(buf, max.length);
      writeB(buf, max);
      writeC(buf, (ipRange.getAddress()).length);
      writeB(buf, ipRange.getAddress());
    } 
    
    writeH(buf, NetworkConfig.GAME_PORT);
    writeD(buf, NetworkConfig.MAX_ONLINE_PLAYERS);
    writeS(buf, NetworkConfig.LOGIN_PASSWORD);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\loginserver\serverpackets\SM_GS_AUTH.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
