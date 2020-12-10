package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.ItemService;









public class CM_SPLIT_ITEM
  extends AionClientPacket
{
  int sourceItemObjId;
  int sourceStorageType;
  long itemAmount;
  int destinationItemObjId;
  int destinationStorageType;
  int slotNum;
  
  public CM_SPLIT_ITEM(int opcode) {
    super(opcode);
  }


  
  protected void readImpl() {
    this.sourceItemObjId = readD();
    this.itemAmount = readD();
    
    byte[] zeros = readB(4);
    this.sourceStorageType = readC();
    this.destinationItemObjId = readD();
    this.destinationStorageType = readC();
    this.slotNum = readH();
  }



  
  protected void runImpl() {
    Player player = ((AionConnection)getConnection()).getActivePlayer();
    
    if (this.destinationItemObjId == 0) {
      ItemService.splitItem(player, this.sourceItemObjId, this.itemAmount, this.slotNum, this.sourceStorageType, this.destinationStorageType);
    } else {
      ItemService.mergeItems(player, this.sourceItemObjId, this.itemAmount, this.destinationItemObjId, this.sourceStorageType, this.destinationStorageType);
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_SPLIT_ITEM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
