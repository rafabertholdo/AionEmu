package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_BLOCK_LIST;






















public class CM_SHOW_BLOCKLIST
  extends AionClientPacket
{
  public CM_SHOW_BLOCKLIST(int opcode) {
    super(opcode);
  }







  
  protected void readImpl() {}






  
  protected void runImpl() {
    sendPacket((AionServerPacket)new SM_BLOCK_LIST());
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_SHOW_BLOCKLIST.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
