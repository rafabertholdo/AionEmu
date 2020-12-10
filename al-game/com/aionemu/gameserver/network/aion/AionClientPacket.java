package com.aionemu.gameserver.network.aion;

import com.aionemu.commons.network.packet.BaseClientPacket;
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

























public abstract class AionClientPacket
  extends BaseClientPacket<AionConnection>
  implements Cloneable
{
  private static final Logger log = Logger.getLogger(AionClientPacket.class);











  
  @Deprecated
  protected AionClientPacket(ByteBuffer buf, AionConnection client, int opcode) {
    super(buf, opcode);
    setConnection(client);
  }








  
  protected AionClientPacket(int opcode) {
    super(opcode);
  }






  
  public final void run() {
    try {
      runImpl();
    }
    catch (Throwable e) {
      
      String name = ((AionConnection)getConnection()).getAccount().getName();
      if (name == null) {
        name = ((AionConnection)getConnection()).getIP();
      }
      log.error("Error handling client (" + name + ") message :" + this, e);
    } 
  }







  
  protected void sendPacket(AionServerPacket msg) {
    ((AionConnection)getConnection()).sendPacket(msg);
  }







  
  public AionClientPacket clonePacket() {
    try {
      return (AionClientPacket)clone();
    }
    catch (CloneNotSupportedException e) {
      
      return null;
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\AionClientPacket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
