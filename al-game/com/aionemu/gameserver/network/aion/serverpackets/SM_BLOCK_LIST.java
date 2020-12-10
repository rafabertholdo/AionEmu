package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.BlockList;
import com.aionemu.gameserver.model.gameobjects.player.BlockedPlayer;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_BLOCK_LIST extends AionServerPacket {
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    BlockList list = con.getActivePlayer().getBlockList();
    writeH(buf, list.getSize());
    writeC(buf, 0);
    for (BlockedPlayer player : list.getBlockedList()) {

      writeS(buf, player.getName());
      writeS(buf, player.getReason());
    }
  }
}
