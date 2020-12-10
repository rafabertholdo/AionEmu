package com.aionemu.gameserver.network.loginserver;

import com.aionemu.commons.network.packet.BaseServerPacket;
import java.nio.ByteBuffer;






























public abstract class LsServerPacket
  extends BaseServerPacket
{
  protected LsServerPacket(int opcode) {
    super(opcode);
  }







  
  public final void write(LoginServerConnection con, ByteBuffer buf) {
    buf.putShort((short)0);
    writeImpl(con, buf);
    buf.flip();
    buf.putShort((short)buf.limit());
    buf.position(0);
  }
  
  protected abstract void writeImpl(LoginServerConnection paramLoginServerConnection, ByteBuffer paramByteBuffer);
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\loginserver\LsServerPacket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
