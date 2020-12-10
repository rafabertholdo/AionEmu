package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.utils.gametime.GameTimeManager;
import java.nio.ByteBuffer;

public class SM_GAME_TIME extends AionServerPacket {
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, GameTimeManager.getGameTime().getTime());
  }
}
