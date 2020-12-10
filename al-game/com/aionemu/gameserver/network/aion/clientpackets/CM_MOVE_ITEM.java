package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.ItemService;



























public class CM_MOVE_ITEM
  extends AionClientPacket
{
  private int targetObjectId;
  private int source;
  private int destination;
  private int slot;
  
  public CM_MOVE_ITEM(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.targetObjectId = readD();
    this.source = readC();
    this.destination = readC();
    this.slot = readH();
  }





  
  protected void runImpl() {
    Player player = ((AionConnection)getConnection()).getActivePlayer();
    ItemService.moveItem(player, this.targetObjectId, this.source, this.destination, this.slot);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_MOVE_ITEM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
