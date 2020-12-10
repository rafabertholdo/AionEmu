package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.ExchangeService;





















public class CM_EXCHANGE_ADD_ITEM
  extends AionClientPacket
{
  public int itemObjId;
  public int itemCount;
  
  public CM_EXCHANGE_ADD_ITEM(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.itemObjId = readD();
    this.itemCount = readD();
  }





  
  protected void runImpl() {
    Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
    ExchangeService.getInstance().addItem(activePlayer, this.itemObjId, this.itemCount);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_EXCHANGE_ADD_ITEM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
