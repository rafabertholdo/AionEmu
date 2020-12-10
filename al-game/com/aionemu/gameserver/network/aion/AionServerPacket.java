package com.aionemu.gameserver.network.aion;

import com.aionemu.commons.network.packet.BaseServerPacket;
import com.aionemu.gameserver.network.Crypt;
import java.nio.ByteBuffer;






























public abstract class AionServerPacket
  extends BaseServerPacket
{
  private ByteBuffer buf;
  
  protected AionServerPacket() {
    setOpcode(ServerPacketsOpcodes.getOpcode((Class)getClass()));
  }








  
  private final void writeOP(ByteBuffer buf, int value) {
    byte op = Crypt.encodeOpcodec(value);
    buf.put(op);

    
    buf.put((byte)80);

    
    buf.put((byte)(op ^ 0xFFFFFFFF));
  }

  
  public final void write(AionConnection con) {
    write(con, this.buf);
  }






  
  public final void write(AionConnection con, ByteBuffer buf) {
    buf.putShort((short)0);
    writeOP(buf, getOpcode());
    writeImpl(con, buf);
    buf.flip();
    buf.putShort((short)buf.limit());
    ByteBuffer b = buf.slice();
    buf.position(0);
    con.encrypt(b);
  }







  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {}






  
  public ByteBuffer getBuf() {
    return this.buf;
  }




  
  public void setBuf(ByteBuffer buf) {
    this.buf = buf;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\AionServerPacket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
