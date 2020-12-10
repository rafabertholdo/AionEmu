package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.DuelResult;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_DUEL extends AionServerPacket {
  private String playerName;
  private DuelResult result;
  private int requesterObjId;
  private int type;

  private SM_DUEL(int type) {
    this.type = type;
  }

  public static SM_DUEL SM_DUEL_STARTED(int requesterObjId) {
    SM_DUEL packet = new SM_DUEL(0);
    packet.setRequesterObjId(requesterObjId);
    return packet;
  }

  private void setRequesterObjId(int requesterObjId) {
    this.requesterObjId = requesterObjId;
  }

  public static SM_DUEL SM_DUEL_RESULT(DuelResult result, String playerName) {
    SM_DUEL packet = new SM_DUEL(1);
    packet.setPlayerName(playerName);
    packet.setResult(result);
    return packet;
  }

  private void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  private void setResult(DuelResult result) {
    this.result = result;
  }

  public void writeImpl(AionConnection con, ByteBuffer buf) {
    writeC(buf, this.type);

    switch (this.type) {

      case 0:
        writeD(buf, this.requesterObjId);

      case 1:
        writeC(buf, this.result.getResultId());
        writeD(buf, this.result.getMsgId());
        writeS(buf, this.playerName);

      case 224:
        return;
    }
    throw new IllegalArgumentException("invalid SM_DUEL packet type " + this.type);
  }
}
