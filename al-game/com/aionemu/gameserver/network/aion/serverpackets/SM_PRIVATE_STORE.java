package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PrivateStore;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.model.trade.TradePSItem;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.InventoryPacket;
import java.nio.ByteBuffer;
import java.util.LinkedHashMap;

public class SM_PRIVATE_STORE extends InventoryPacket {
  private PrivateStore store;

  public SM_PRIVATE_STORE(PrivateStore store) {
    this.store = store;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    if (this.store != null) {

      Player storePlayer = this.store.getOwner();
      LinkedHashMap<Integer, TradePSItem> soldItems = this.store.getSoldItems();

      writeD(buf, storePlayer.getObjectId());
      writeH(buf, soldItems.size());
      for (Integer itemObjId : soldItems.keySet()) {

        Item item = storePlayer.getInventory().getItemByObjId(itemObjId.intValue());
        TradePSItem tradeItem = this.store.getTradeItemById(itemObjId.intValue());
        long price = tradeItem.getPrice();
        writeD(buf, itemObjId.intValue());
        writeD(buf, item.getItemTemplate().getTemplateId());
        writeH(buf, (int) tradeItem.getCount());
        writeD(buf, (int) price);

        ItemTemplate itemTemplate = item.getItemTemplate();

        if (itemTemplate.isWeapon()) {

          writeWeaponInfo(buf, item, false, false, true, false);
          continue;
        }
        if (itemTemplate.isArmor()) {

          writeArmorInfo(buf, item, false, true, false);

          continue;
        }
        writeGeneralItemInfo(buf, item, true, false);
      }
    }
  }
}
