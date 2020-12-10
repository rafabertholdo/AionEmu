package com.aionemu.gameserver.network.chatserver.serverpackets;

import com.aionemu.gameserver.configs.network.IPConfig;
import com.aionemu.gameserver.configs.network.NetworkConfig;
import com.aionemu.gameserver.network.chatserver.ChatServerConnection;
import com.aionemu.gameserver.network.chatserver.CsServerPacket;
import java.nio.ByteBuffer;

public class SM_CS_AUTH extends CsServerPacket {
  public SM_CS_AUTH() {
    super(0);
  }

  protected void writeImpl(ChatServerConnection con, ByteBuffer buf) {
    writeC(buf, getOpcode());
    writeC(buf, NetworkConfig.GAMESERVER_ID);
    writeC(buf, (IPConfig.getDefaultAddress()).length);
    writeB(buf, IPConfig.getDefaultAddress());
    writeS(buf, NetworkConfig.CHAT_PASSWORD);
  }
}
