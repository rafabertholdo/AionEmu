package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.controllers.ReviveType;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
























public class CM_REVIVE
  extends AionClientPacket
{
  private int reviveId;
  
  public CM_REVIVE(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.reviveId = readC();
  }





  
  protected void runImpl() {
    Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
    
    ReviveType reviveType = ReviveType.getReviveTypeById(this.reviveId);
    
    switch (reviveType) {
      
      case BIND_REVIVE:
        activePlayer.getReviveController().bindRevive();
        break;
      case REBIRTH_REVIVE:
        activePlayer.getReviveController().rebirthRevive();
        break;
      case ITEM_SELF_REVIVE:
        activePlayer.getReviveController().itemSelfRevive();
        break;
      case SKILL_REVIVE:
        activePlayer.getReviveController().skillRevive();
        break;
      case KISK_REVIVE:
        activePlayer.getReviveController().kiskRevive();
        break;
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_REVIVE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
