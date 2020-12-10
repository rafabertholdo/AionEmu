package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.ItemRemodelService;





















public class CM_ITEM_REMODEL
  extends AionClientPacket
{
  private int keepItemId;
  private int extractItemId;
  
  public CM_ITEM_REMODEL(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    readD();
    this.keepItemId = readD();
    this.extractItemId = readD();
  }





  
  protected void runImpl() {
    Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
    ItemRemodelService.remodelItem(activePlayer, this.keepItemId, this.extractItemId);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_ITEM_REMODEL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
