package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_BLOCK_RESPONSE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.SocialService;
import com.aionemu.gameserver.world.World;
import org.apache.log4j.Logger;



















public class CM_BLOCK_ADD
  extends AionClientPacket
{
  private static Logger log = Logger.getLogger(CM_BLOCK_ADD.class);
  
  private String targetName;
  
  private String reason;
  
  public CM_BLOCK_ADD(int opcode) {
    super(opcode);
  }




  
  protected void readImpl() {
    this.targetName = readS();
    this.reason = readS();
  }






  
  protected void runImpl() {
    Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
    
    Player targetPlayer = World.getInstance().findPlayer(this.targetName);

    
    if (activePlayer.getName().equalsIgnoreCase(this.targetName)) {
      
      sendPacket((AionServerPacket)new SM_BLOCK_RESPONSE(4, this.targetName));

    
    }
    else if (activePlayer.getBlockList().isFull()) {
      
      sendPacket((AionServerPacket)new SM_BLOCK_RESPONSE(3, this.targetName));

    
    }
    else if (targetPlayer == null) {
      
      sendPacket((AionServerPacket)new SM_BLOCK_RESPONSE(2, this.targetName));

    
    }
    else if (activePlayer.getFriendList().getFriend(targetPlayer.getObjectId()) != null) {
      
      sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.BLOCKLIST_NO_BUDDY);

    
    }
    else if (activePlayer.getBlockList().contains(targetPlayer.getObjectId())) {
      
      sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.BLOCKLIST_ALREADY_BLOCKED);

    
    }
    else if (!SocialService.addBlockedUser(activePlayer, targetPlayer, this.reason)) {
      
      log.error("Failed to add " + targetPlayer.getName() + " to the block list for " + activePlayer.getName() + " - check database setup.");
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_BLOCK_ADD.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
