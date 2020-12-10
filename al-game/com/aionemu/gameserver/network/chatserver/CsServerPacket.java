package com.aionemu.gameserver.network.chatserver;

import com.aionemu.commons.network.packet.BaseServerPacket;
import java.nio.ByteBuffer;

public abstract class CsServerPacket extends BaseServerPacket {
  protected CsServerPacket(int opcode) {
    super(opcode);
  }

  public final void write(ChatServerConnection con, ByteBuffer buf) {
    buf.putShort((short) 0);
    writeImpl(con, buf);
    buf.flip();
    buf.putShort((short) buf.limit());
    buf.position(0);
  }

  protected abstract void writeImpl(ChatServerConnection paramChatServerConnection, ByteBuffer paramByteBuffer);
}
