package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.Storage;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.ItemService;




















public class CM_DELETE_ITEM
  extends AionClientPacket
{
  public int objId;
  
  public CM_DELETE_ITEM(int opcode) {
    super(opcode);
  }


  
  protected void readImpl() {
    this.objId = readD();
  }


  
  protected void runImpl() {
    Player player = ((AionConnection)getConnection()).getActivePlayer();
    Storage bag = player.getInventory();
    Item resultItem = bag.getItemByObjId(this.objId);
    
    if (resultItem != null)
      ItemService.removeItemByObjectId(player, this.objId, true); 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_DELETE_ITEM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
