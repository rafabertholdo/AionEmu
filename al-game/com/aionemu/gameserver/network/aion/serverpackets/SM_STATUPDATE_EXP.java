package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

























public class SM_STATUPDATE_EXP
  extends AionServerPacket
{
  private long currentExp;
  private long recoverableExp;
  private long maxExp;
  private long curBoostExp = 0L;
  private long maxBoostExp = 0L;







  
  public SM_STATUPDATE_EXP(long currentExp, long recoverableExp, long maxExp) {
    this.currentExp = currentExp;
    this.recoverableExp = recoverableExp;
    this.maxExp = maxExp;
  }





  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeQ(buf, this.currentExp);
    writeQ(buf, this.recoverableExp);
    writeQ(buf, this.maxExp);
    writeQ(buf, this.curBoostExp);
    writeQ(buf, this.maxBoostExp);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_STATUPDATE_EXP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
