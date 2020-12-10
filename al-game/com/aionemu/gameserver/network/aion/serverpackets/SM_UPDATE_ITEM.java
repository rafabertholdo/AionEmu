package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.items.ItemId;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.InventoryPacket;
import java.nio.ByteBuffer;

public class SM_UPDATE_ITEM extends InventoryPacket {
  private Item item;
  private boolean isWeaponSwitch = false;

  public SM_UPDATE_ITEM(Item item) {
    this.item = item;
  }

  public SM_UPDATE_ITEM(Item item, boolean isWeaponSwitch) {
    this.item = item;
    this.isWeaponSwitch = isWeaponSwitch;
  }

  protected void writeGeneralInfo(ByteBuffer buf, Item item) {
    writeD(buf, item.getObjectId());
    ItemTemplate itemTemplate = item.getItemTemplate();
    writeH(buf, 36);
    writeD(buf, itemTemplate.getNameId());
    writeH(buf, 0);
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeGeneralInfo(buf, this.item);

    ItemTemplate itemTemplate = this.item.getItemTemplate();

    if (itemTemplate.getTemplateId() == ItemId.KINAH.value()) {

      writeKinah(buf, this.item, true);
    } else if (itemTemplate.isWeapon()) {

      writeWeaponInfo(buf, this.item, true, this.isWeaponSwitch, false, false);
    } else if (itemTemplate.isArmor()) {

      writeArmorInfo(buf, this.item, true, false, false);
    } else if (itemTemplate.isStigma()) {

      writeStigmaInfo(buf, this.item);
    } else {

      writeGeneralItemInfo(buf, this.item);
    }
  }

  protected void writeGeneralItemInfo(ByteBuffer buf, Item item) {
    writeH(buf, 22);
    writeC(buf, 0);
    writeH(buf, item.getItemMask());
    writeD(buf, (int) item.getItemCount());
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeH(buf, 0);
    writeC(buf, 0);
    writeH(buf, 0);
    writeH(buf, item.getEquipmentSlot());
  }

  protected void writeStigmaInfo(ByteBuffer buf, Item item) {
    int itemSlotId = item.getEquipmentSlot();
    writeH(buf, 5);
    writeC(buf, 6);
    writeD(buf, item.isEquipped() ? itemSlotId : 0);
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
    writeC(buf, 26);
    writeC(buf, 0);
  }
}
