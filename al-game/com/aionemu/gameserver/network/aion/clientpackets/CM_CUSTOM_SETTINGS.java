package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CUSTOM_SETTINGS;
import com.aionemu.gameserver.utils.PacketSendUtility;


















public class CM_CUSTOM_SETTINGS
  extends AionClientPacket
{
  private int display;
  private int deny;
  
  public CM_CUSTOM_SETTINGS(int opcode) {
    super(opcode);
  }










  
  protected void readImpl() {
    this.display = readH();







    
    this.deny = readH();
  }





  
  protected void runImpl() {
    Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
    activePlayer.getPlayerSettings().setDisplay(this.display);
    activePlayer.getPlayerSettings().setDeny(this.deny);
    
    PacketSendUtility.broadcastPacket(activePlayer, (AionServerPacket)new SM_CUSTOM_SETTINGS(activePlayer), true);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_CUSTOM_SETTINGS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
