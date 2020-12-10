package com.aionemu.gameserver.network.loginserver.clientpackets;

import com.aionemu.gameserver.network.loginserver.LoginServer;
import com.aionemu.gameserver.network.loginserver.LoginServerConnection;
import com.aionemu.gameserver.network.loginserver.LsClientPacket;
import com.aionemu.gameserver.network.loginserver.LsServerPacket;
import com.aionemu.gameserver.network.loginserver.serverpackets.SM_ACCOUNT_LIST;
import com.aionemu.gameserver.network.loginserver.serverpackets.SM_GS_AUTH;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import org.apache.log4j.Logger;


























public class CM_GS_AUTH_RESPONSE
  extends LsClientPacket
{
  protected static final Logger log = Logger.getLogger(CM_GS_AUTH_RESPONSE.class);




  
  private int response;




  
  public CM_GS_AUTH_RESPONSE(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.response = readC();
  }








  
  protected void runImpl() {
    if (this.response == 0) {
      
      ((LoginServerConnection)getConnection()).setState(LoginServerConnection.State.AUTHED);
      sendPacket((LsServerPacket)new SM_ACCOUNT_LIST(LoginServer.getInstance().getLoggedInAccounts()));



    
    }
    else if (this.response == 1) {
      
      log.fatal("GameServer is not authenticated at LoginServer side, shutting down!");
      System.exit(1);


    
    }
    else if (this.response == 2) {
      
      log.info("GameServer is already registered at LoginServer side! trying again...");


      
      ThreadPoolManager.getInstance().schedule(new Runnable()
          {
            public void run()
            {
              ((LoginServerConnection)CM_GS_AUTH_RESPONSE.this.getConnection()).sendPacket((LsServerPacket)new SM_GS_AUTH());
            }
          },  10000L);
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\loginserver\clientpackets\CM_GS_AUTH_RESPONSE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
