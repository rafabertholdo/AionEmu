package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.Title;
import com.aionemu.gameserver.model.gameobjects.player.TitleList;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_TITLE_LIST extends AionServerPacket {
  private TitleList titleList;

  public SM_TITLE_LIST(Player player) {
    this.titleList = player.getTitleList();
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeC(buf, 0);
    writeH(buf, this.titleList.size());
    for (Title title : this.titleList.getTitles()) {

      writeD(buf, title.getTemplate().getTitleId());
      writeD(buf, 0);
    }
  }
}
