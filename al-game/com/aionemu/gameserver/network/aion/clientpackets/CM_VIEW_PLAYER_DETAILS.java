package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.DeniedStatus;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_VIEW_PLAYER_DETAILS;
import com.aionemu.gameserver.world.World;
import org.apache.log4j.Logger;




















public class CM_VIEW_PLAYER_DETAILS
  extends AionClientPacket
{
  private static final Logger log = Logger.getLogger(CM_VIEW_PLAYER_DETAILS.class);
  
  private int targetObjectId;

  
  public CM_VIEW_PLAYER_DETAILS(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.targetObjectId = readD();
  }





  
  protected void runImpl() {
    Player player = World.getInstance().findPlayer(this.targetObjectId);
    if (player == null) {

      
      log.warn("CHECKPOINT: can't show player details for " + this.targetObjectId);
      
      return;
    } 
    if (player.getPlayerSettings().isInDeniedStatus(DeniedStatus.VEIW_DETAIL)) {
      
      sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_REJECTED_WATCH(player.getName()));
      return;
    } 
    sendPacket((AionServerPacket)new SM_VIEW_PLAYER_DETAILS(this.targetObjectId, player.getEquipment().getEquippedItemsWithoutStigma()));
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_VIEW_PLAYER_DETAILS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
