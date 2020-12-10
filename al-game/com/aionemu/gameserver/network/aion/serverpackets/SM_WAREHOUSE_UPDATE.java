package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.items.ItemId;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.InventoryPacket;
import java.nio.ByteBuffer;

public class SM_WAREHOUSE_UPDATE extends InventoryPacket {
  private int warehouseType;
  private Item item;

  public SM_WAREHOUSE_UPDATE(Item item, int warehouseType) {
    this.warehouseType = warehouseType;
    this.item = item;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeC(buf, this.warehouseType);
    writeH(buf, 13);
    writeH(buf, 1);

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
    ItemTemplate itemTemplate = item.getItemTemplate();
    writeD(buf, itemTemplate.getTemplateId());
    writeC(buf, 0);
    writeH(buf, 36);
    writeD(buf, itemTemplate.getNameId());
    writeH(buf, 0);
  }
}
