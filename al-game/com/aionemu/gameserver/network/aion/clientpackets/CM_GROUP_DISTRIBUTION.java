package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.restrictions.RestrictionsManager;
import com.aionemu.gameserver.services.GroupService;





















public class CM_GROUP_DISTRIBUTION
  extends AionClientPacket
{
  private int amount;
  
  public CM_GROUP_DISTRIBUTION(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.amount = readD();
  }





  
  protected void runImpl() {
    if (this.amount < 1) {
      return;
    }
    Player player = ((AionConnection)getConnection()).getActivePlayer();
    
    if (!RestrictionsManager.canTrade(player)) {
      return;
    }
    GroupService.getInstance().groupDistribution(player, this.amount);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_GROUP_DISTRIBUTION.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
