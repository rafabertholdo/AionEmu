package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MAY_LOGIN_INTO_GAME;


























public class CM_MAY_LOGIN_INTO_GAME
  extends AionClientPacket
{
  public CM_MAY_LOGIN_INTO_GAME(int opcode) {
    super(opcode);
  }






  
  protected void readImpl() {}






  
  protected void runImpl() {
    AionConnection client = (AionConnection)getConnection();
    
    client.sendPacket((AionServerPacket)new SM_MAY_LOGIN_INTO_GAME());
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_MAY_LOGIN_INTO_GAME.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
