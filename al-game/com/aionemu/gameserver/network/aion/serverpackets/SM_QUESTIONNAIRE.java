package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_QUESTIONNAIRE extends AionServerPacket {
  private int messageId;
  private byte chunk;
  private byte count;
  private String html;

  public SM_QUESTIONNAIRE(int messageId, byte chunk, byte count, String html) {
    this.messageId = messageId;
    this.chunk = chunk;
    this.count = count;
    this.html = html;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.messageId);
    writeC(buf, this.chunk);
    writeC(buf, this.count);
    writeH(buf, this.html.length() * 2);
    writeS(buf, this.html);
  }
}
