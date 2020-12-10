package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;






















public class SM_DIALOG_WINDOW
  extends AionServerPacket
{
  private int targetObjectId;
  private int dialogID;
  private int questId = 0;

  
  public SM_DIALOG_WINDOW(int targetObjectId, int dlgID) {
    this.targetObjectId = targetObjectId;
    this.dialogID = dlgID;
  }

  
  public SM_DIALOG_WINDOW(int targetObjectId, int dlgID, int questId) {
    this.targetObjectId = targetObjectId;
    this.dialogID = dlgID;
    this.questId = questId;
  }





  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.targetObjectId);
    writeH(buf, this.dialogID);
    writeD(buf, this.questId);
    writeH(buf, 0);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_DIALOG_WINDOW.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
