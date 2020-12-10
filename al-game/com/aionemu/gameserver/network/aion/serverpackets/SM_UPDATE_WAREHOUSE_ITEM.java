package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.items.ItemId;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.InventoryPacket;
import java.nio.ByteBuffer;

public class SM_UPDATE_WAREHOUSE_ITEM extends InventoryPacket {
  Item item;
  int warehouseType;

  public SM_UPDATE_WAREHOUSE_ITEM(Item item, int warehouseType) {
    this.item = item;
    this.warehouseType = warehouseType;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeGeneralInfo(buf, this.item);

    ItemTemplate itemTemplate = this.item.getItemTemplate();

    if (itemTemplate.getTemplateId() == ItemId.KINAH.value()) {

      writeKinah(buf, this.item, false);
    } else if (itemTemplate.isWeapon()) {

      writeWeaponInfo(buf, this.item, false);
    } else if (itemTemplate.isArmor()) {

      writeArmorInfo(buf, this.item, false, false, false);
    } else {

      writeGeneralItemInfo(buf, this.item, false, false);
    }
  }

  protected void writeGeneralInfo(ByteBuffer buf, Item item) {
    writeD(buf, item.getObjectId());
    writeC(buf, this.warehouseType);
    ItemTemplate itemTemplate = item.getItemTemplate();
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
