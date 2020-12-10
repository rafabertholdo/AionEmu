package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUIT_RESPONSE;
import com.aionemu.gameserver.network.loginserver.LoginServer;
import com.aionemu.gameserver.services.PlayerService;































public class CM_QUIT
  extends AionClientPacket
{
  private boolean logout;
  
  public CM_QUIT(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.logout = (readC() == 1);
  }





  
  protected void runImpl() {
    AionConnection client = (AionConnection)getConnection();
    
    if (client.getState() == AionConnection.State.IN_GAME) {
      
      Player player = client.getActivePlayer();
      
      if (!this.logout) {
        LoginServer.getInstance().aionClientDisconnected(client.getAccount().getId());
      }
      PlayerService.playerLoggedOut(player);



      
      client.setActivePlayer(null);
    } 
    
    if (this.logout) {
      
      sendPacket((AionServerPacket)new SM_QUIT_RESPONSE());
    }
    else {
      
      client.close((AionServerPacket)new SM_QUIT_RESPONSE(), true);
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_QUIT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
