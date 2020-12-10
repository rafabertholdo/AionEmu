package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.DropService;























public class CM_GROUP_LOOT
  extends AionClientPacket
{
  private int groupId;
  private int unk1;
  private int unk2;
  private int itemId;
  private int unk3;
  private int npcId;
  private int distributionId;
  private int roll;
  private long bid;
  
  public CM_GROUP_LOOT(int opcode) {
    super(opcode);
  }


  
  protected void readImpl() {
    this.groupId = readD();
    this.unk1 = readD();
    this.unk2 = readD();
    this.itemId = readD();
    this.unk3 = readC();
    this.npcId = readD();
    this.distributionId = readC();
    this.roll = readD();
    this.bid = readQ();
  }





  
  protected void runImpl() {
    Player player = ((AionConnection)getConnection()).getActivePlayer();
    if (player == null) {
      return;
    }
    switch (this.distributionId) {
      
      case 2:
        DropService.getInstance().handleRoll(player, this.roll, this.itemId, this.npcId);
        break;
      case 3:
        DropService.getInstance().handleBid(player, this.bid, this.itemId, this.npcId);
        break;
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_GROUP_LOOT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
