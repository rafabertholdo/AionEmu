package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.items.ItemId;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.InventoryPacket;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

public class SM_WAREHOUSE_INFO extends InventoryPacket {
  private int warehouseType;
  private List<Item> itemList;
  private boolean firstPacket;
  private int expandLvl;

  public SM_WAREHOUSE_INFO(List<Item> items, int warehouseType, int expandLvl, boolean firstPacket) {
    this.warehouseType = warehouseType;
    this.expandLvl = expandLvl;
    this.firstPacket = firstPacket;
    if (items == null) {
      this.itemList = Collections.emptyList();
    } else {
      this.itemList = items;
    }
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeC(buf, this.warehouseType);
    writeC(buf, this.firstPacket ? 1 : 0);
    writeC(buf, this.expandLvl);
    writeH(buf, 0);
    writeH(buf, this.itemList.size());
    for (Item item : this.itemList) {

      writeGeneralInfo(buf, item);

      ItemTemplate itemTemplate = item.getItemTemplate();

      if (itemTemplate.getTemplateId() == ItemId.KINAH.value()) {
        writeKinah(buf, item, false);
        continue;
      }
      if (itemTemplate.isWeapon()) {
        writeWeaponInfo(buf, item, false);
        continue;
      }
      if (itemTemplate.isArmor()) {
        writeArmorInfo(buf, item, false, false, false);
        continue;
      }
      writeGeneralItemInfo(buf, item, false, false);
    }
  }

  protected void writeGeneralInfo(ByteBuffer buf, Item item) {
    writeD(buf, item.getObjectId());
    ItemTemplate itemTemplate = item.getItemTemplate();
    writeD(buf, itemTemplate.getTemplateId());
    writeC(buf, 0);
    writeH(buf, 36);
    writeD(buf, itemTemplate.getNameId());
    writeH(buf, 0);
  }

  protected void writeKinah(ByteBuffer buf, Item item, boolean isInventory) {
    writeH(buf, 22);
    writeC(buf, 0);
    writeH(buf, item.getItemMask());
    writeQ(buf, item.getItemCount());
    writeD(buf, 0);
    writeD(buf, 0);
    writeH(buf, 0);
    writeC(buf, 0);
    writeC(buf, 255);
    writeC(buf, 255);
  }
}
