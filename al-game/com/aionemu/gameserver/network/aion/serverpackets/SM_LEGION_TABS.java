package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.legion.LegionHistory;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;
import java.util.Collection;

public class SM_LEGION_TABS extends AionServerPacket {
  private int page;
  private Collection<LegionHistory> legionHistory;

  public SM_LEGION_TABS(Collection<LegionHistory> legionHistory) {
    this.legionHistory = legionHistory;
    this.page = 0;
  }

  public SM_LEGION_TABS(Collection<LegionHistory> legionHistory, int page) {
    this.legionHistory = legionHistory;
    this.page = page;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    if (this.legionHistory.size() < this.page * 8) {
      return;
    }

    int hisSize = this.legionHistory.size() - this.page * 8;

    if (this.page == 0 && this.legionHistory.size() > 8)
      hisSize = 8;
    if (this.page == 1 && this.legionHistory.size() > 16)
      hisSize = 8;
    if (this.page == 2 && this.legionHistory.size() > 24) {
      hisSize = 8;
    }
    writeD(buf, 18);
    writeD(buf, this.page);
    writeD(buf, hisSize);

    int i = 0;
    for (LegionHistory history : this.legionHistory) {

      if (i >= this.page * 8 && i <= 8 + this.page * 8) {

        writeD(buf, (int) (history.getTime().getTime() / 1000L));
        writeC(buf, history.getLegionHistoryType().getHistoryId());
        writeC(buf, 0);
        if (history.getName().length() > 0) {

          writeS(buf, history.getName());
          int size = 134 - history.getName().length() * 2 + 2;
          writeB(buf, new byte[size]);
        } else {

          writeB(buf, new byte[134]);
        }
      }
      i++;
      if (i >= 8 + this.page * 8)
        break;
    }
    writeH(buf, 0);
  }
}
