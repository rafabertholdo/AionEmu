package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.player.DeniedStatus;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.DuelService;
import com.aionemu.gameserver.world.World;




























public class CM_DUEL_REQUEST
  extends AionClientPacket
{
  private int objectId;
  
  public CM_DUEL_REQUEST(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.objectId = readD();
  }


  
  protected void runImpl() {
    Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
    AionObject target = World.getInstance().findAionObject(this.objectId);
    
    if (target == null) {
      return;
    }
    if (target instanceof Player) {
      
      Player targetPlayer = (Player)target;
      
      if (targetPlayer.getPlayerSettings().isInDeniedStatus(DeniedStatus.DUEL)) {
        
        sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_REJECTED_DUEL(targetPlayer.getName()));
        return;
      } 
      DuelService duelService = DuelService.getInstance();
      duelService.onDuelRequest(activePlayer, targetPlayer);
      duelService.confirmDuelWith(activePlayer, targetPlayer);
    }
    else {
      
      sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.DUEL_PARTNER_INVALID(target.getName()));
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_DUEL_REQUEST.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
