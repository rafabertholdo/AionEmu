package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.BrokerService;






















public class CM_REGISTER_BROKER_ITEM
  extends AionClientPacket
{
  private int brokerId;
  private int itemUniqueId;
  private int price;
  private int itemCount;
  
  public CM_REGISTER_BROKER_ITEM(int opcode) {
    super(opcode);
  }


  
  protected void readImpl() {
    this.brokerId = readD();
    this.itemUniqueId = readD();
    this.price = readD();
    readD();
    this.itemCount = readH();
  }


  
  protected void runImpl() {
    Player player = ((AionConnection)getConnection()).getActivePlayer();
    
    BrokerService.getInstance().registerItem(player, this.itemUniqueId, this.price);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_REGISTER_BROKER_ITEM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
