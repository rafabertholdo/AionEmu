package com.aionemu.gameserver.network.factories;

import com.aionemu.gameserver.network.loginserver.LoginServerConnection;
import com.aionemu.gameserver.network.loginserver.LsClientPacket;
import com.aionemu.gameserver.network.loginserver.LsPacketHandler;
import com.aionemu.gameserver.network.loginserver.clientpackets.CM_ACCOUNT_RECONNECT_KEY;
import com.aionemu.gameserver.network.loginserver.clientpackets.CM_ACOUNT_AUTH_RESPONSE;
import com.aionemu.gameserver.network.loginserver.clientpackets.CM_BAN_RESPONSE;
import com.aionemu.gameserver.network.loginserver.clientpackets.CM_GS_AUTH_RESPONSE;
import com.aionemu.gameserver.network.loginserver.clientpackets.CM_LS_CONTROL_RESPONSE;
import com.aionemu.gameserver.network.loginserver.clientpackets.CM_REQUEST_KICK_ACCOUNT;






















public class LsPacketHandlerFactory
{
  private LsPacketHandler handler = new LsPacketHandler();

  
  public static final LsPacketHandlerFactory getInstance() {
    return SingletonHolder.instance;
  }





  
  private LsPacketHandlerFactory() {
    addPacket((LsClientPacket)new CM_ACCOUNT_RECONNECT_KEY(3), new LoginServerConnection.State[] { LoginServerConnection.State.AUTHED });
    addPacket((LsClientPacket)new CM_ACOUNT_AUTH_RESPONSE(1), new LoginServerConnection.State[] { LoginServerConnection.State.AUTHED });
    addPacket((LsClientPacket)new CM_GS_AUTH_RESPONSE(0), new LoginServerConnection.State[] { LoginServerConnection.State.CONNECTED });
    addPacket((LsClientPacket)new CM_REQUEST_KICK_ACCOUNT(2), new LoginServerConnection.State[] { LoginServerConnection.State.AUTHED });
    addPacket((LsClientPacket)new CM_LS_CONTROL_RESPONSE(4), new LoginServerConnection.State[] { LoginServerConnection.State.AUTHED });
    addPacket((LsClientPacket)new CM_BAN_RESPONSE(5), new LoginServerConnection.State[] { LoginServerConnection.State.AUTHED });
  }


  
  private void addPacket(LsClientPacket prototype, LoginServerConnection.State... states) {
    this.handler.addPacketPrototype(prototype, states);
  }

  
  public LsPacketHandler getPacketHandler() {
    return this.handler;
  }

  
  private static class SingletonHolder
  {
    protected static final LsPacketHandlerFactory instance = new LsPacketHandlerFactory();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\factories\LsPacketHandlerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
