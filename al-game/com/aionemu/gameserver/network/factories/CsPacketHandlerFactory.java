package com.aionemu.gameserver.network.factories;

import com.aionemu.gameserver.network.chatserver.ChatServerConnection;
import com.aionemu.gameserver.network.chatserver.CsClientPacket;
import com.aionemu.gameserver.network.chatserver.CsPacketHandler;
import com.aionemu.gameserver.network.chatserver.clientpackets.CM_CS_AUTH_RESPONSE;
import com.aionemu.gameserver.network.chatserver.clientpackets.CM_CS_PLAYER_AUTH_RESPONSE;

public class CsPacketHandlerFactory {
  private CsPacketHandler handler = new CsPacketHandler();

  public CsPacketHandlerFactory() {
    addPacket((CsClientPacket) new CM_CS_AUTH_RESPONSE(0),
        new ChatServerConnection.State[] { ChatServerConnection.State.CONNECTED });
    addPacket((CsClientPacket) new CM_CS_PLAYER_AUTH_RESPONSE(1),
        new ChatServerConnection.State[] { ChatServerConnection.State.AUTHED });
  }

  private void addPacket(CsClientPacket prototype, ChatServerConnection.State... states) {
    this.handler.addPacketPrototype(prototype, states);
  }

  public CsPacketHandler getPacketHandler() {
    return this.handler;
  }
}
