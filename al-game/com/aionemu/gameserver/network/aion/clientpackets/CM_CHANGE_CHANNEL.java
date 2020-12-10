package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.TeleportService;





















public class CM_CHANGE_CHANNEL
  extends AionClientPacket
{
  private int channel;
  
  public CM_CHANGE_CHANNEL(int opcode) {
    super(opcode);
  }


  
  protected void readImpl() {
    this.channel = readD();
  }


  
  protected void runImpl() {
    Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
    TeleportService.changeChannel(activePlayer, this.channel);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_CHANGE_CHANNEL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
