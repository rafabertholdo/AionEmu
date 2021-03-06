package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;
import java.util.Map;

public class SM_MACRO_LIST extends AionServerPacket {
  private Player player;

  public SM_MACRO_LIST(Player player) {
    this.player = player;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.player.getObjectId());
    writeC(buf, 1);

    int size = this.player.getMacroList().getSize();

    writeH(buf, -size);

    if (size > 0) {
      for (Map.Entry<Integer, String> entry : (Iterable<Map.Entry<Integer, String>>) this.player.getMacroList()
          .entrySet()) {

        writeC(buf, ((Integer) entry.getKey()).intValue());
        writeS(buf, entry.getValue());
      }
    }
  }
}
