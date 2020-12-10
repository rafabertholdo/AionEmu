package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.services.QuestService;
import java.nio.ByteBuffer;
import java.util.List;

public class SM_NEARBY_QUESTS extends AionServerPacket {
  private Integer[] questIds;
  private int size;

  public SM_NEARBY_QUESTS(List<Integer> questIds) {
    this.questIds = questIds.<Integer>toArray(new Integer[questIds.size()]);
    this.size = questIds.size();
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    int playerLevel = con.getActivePlayer().getLevel();
    writeD(buf, this.size);
    Integer[] arr$;
    int len$, i$;
    for (arr$ = this.questIds, len$ = arr$.length, i$ = 0; i$ < len$;) {
      int id = arr$[i$].intValue();

      writeH(buf, id);
      if (QuestService.checkLevelRequirement(id, playerLevel)) {
        writeH(buf, 0);
      } else {
        writeH(buf, 2);
      }
      i$++;
    }

  }
}
