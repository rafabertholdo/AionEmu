package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;
import java.util.Set;

public class SM_RECIPE_LIST extends AionServerPacket {
  private Integer[] recipeIds;
  private int count;

  public SM_RECIPE_LIST(Set<Integer> recipeIds) {
    this.recipeIds = recipeIds.<Integer>toArray(new Integer[recipeIds.size()]);
    this.count = recipeIds.size();
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeH(buf, this.count);
    Integer[] arr$;
    int len$, i$;
    for (arr$ = this.recipeIds, len$ = arr$.length, i$ = 0; i$ < len$;) {
      int id = arr$[i$].intValue();

      writeD(buf, id);
      writeC(buf, 0);
      i$++;
    }

  }
}
