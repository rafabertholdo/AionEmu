package com.aionemu.commons.network.packet;

import java.nio.ByteBuffer;




























public abstract class BaseServerPacket
  extends BasePacket
{
  protected BaseServerPacket(int opcode) {
    super(BasePacket.PacketType.SERVER, opcode);
  }





  
  protected BaseServerPacket() {
    super(BasePacket.PacketType.SERVER);
  }







  
  protected final void writeD(ByteBuffer buf, int value) {
    buf.putInt(value);
  }







  
  protected final void writeH(ByteBuffer buf, int value) {
    buf.putShort((short)value);
  }







  
  protected final void writeC(ByteBuffer buf, int value) {
    buf.put((byte)value);
  }







  
  protected final void writeDF(ByteBuffer buf, double value) {
    buf.putDouble(value);
  }







  
  protected final void writeF(ByteBuffer buf, float value) {
    buf.putFloat(value);
  }







  
  protected final void writeQ(ByteBuffer buf, long value) {
    buf.putLong(value);
  }







  
  protected final void writeS(ByteBuffer buf, String text) {
    if (text == null) {
      
      buf.putChar(false);
    }
    else {
      
      int len = text.length();
      for (int i = 0; i < len; i++)
        buf.putChar(text.charAt(i)); 
      buf.putChar(false);
    } 
  }







  
  protected final void writeB(ByteBuffer buf, byte[] data) {
    buf.put(data);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\network\packet\BaseServerPacket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
