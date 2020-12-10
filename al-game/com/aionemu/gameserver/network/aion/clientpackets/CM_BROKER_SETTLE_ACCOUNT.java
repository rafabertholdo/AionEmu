package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.BrokerService;





















public class CM_BROKER_SETTLE_ACCOUNT
  extends AionClientPacket
{
  private int npcId;
  
  public CM_BROKER_SETTLE_ACCOUNT(int opcode) {
    super(opcode);
  }


  
  protected void readImpl() {
    this.npcId = readD();
  }


  
  protected void runImpl() {
    Player player = ((AionConnection)getConnection()).getActivePlayer();
    
    BrokerService.getInstance().settleAccount(player);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_BROKER_SETTLE_ACCOUNT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
