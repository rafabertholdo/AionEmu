package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.items.GodStone;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;
import java.util.List;

public class SM_UPDATE_PLAYER_APPEARANCE extends AionServerPacket {
  public int playerId;
  public int size;
  public List<Item> items;

  public SM_UPDATE_PLAYER_APPEARANCE(int playerId, List<Item> items) {
    this.playerId = playerId;
    this.items = items;
    this.size = items.size();
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.playerId);

    short mask = 0;
    for (Item item : this.items) {
      mask = (short) (mask | item.getEquipmentSlot());
    }

    writeH(buf, mask);

    for (Item item : this.items) {

      writeD(buf, item.getItemSkinTemplate().getTemplateId());
      GodStone godStone = item.getGodStone();
      writeD(buf, (godStone != null) ? godStone.getItemId() : 0);
      writeD(buf, item.getItemColor());
      writeH(buf, 0);
    }
  }
}
