package com.aionemu.gameserver.services;

import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.Storage;
import com.aionemu.gameserver.model.templates.item.ArmorType;
import com.aionemu.gameserver.model.templates.item.ItemQuality;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class ItemRemodelService {
  public static void remodelItem(Player player, int keepItemObjId, int extractItemObjId) {
    Storage inventory = player.getInventory();
    Item keepItem = inventory.getItemByObjId(keepItemObjId);
    Item extractItem = inventory.getItemByObjId(extractItemObjId);

    long remodelCost = player.getPrices().getPriceForService(1000L);

    if (keepItem == null || extractItem == null) {
      return;
    }

    if (player.getLevel() < 20) {

      PacketSendUtility.sendPacket(player, (AionServerPacket) SM_SYSTEM_MESSAGE.STR_CHANGE_ITEM_SKIN_PC_LEVEL_LIMIT);

      return;
    }

    if (extractItem.getItemTemplate().getTemplateId() == 168100000) {

      if (keepItem.getItemTemplate() == keepItem.getItemSkinTemplate()) {

        PacketSendUtility.sendMessage(player, "That item does not have a remodeled skin to remove.");

        return;
      }

      if (!ItemService.decreaseKinah(player, remodelCost)) {

        PacketSendUtility.sendPacket(player, (AionServerPacket) SM_SYSTEM_MESSAGE
            .STR_CHANGE_ITEM_SKIN_NOT_ENOUGH_GOLD(new DescriptionId(keepItem.getItemTemplate().getNameId())));

        return;
      }

      ItemService.decreaseItemCount(player, extractItem, 1L);

      keepItem.setItemSkinTemplate(keepItem.getItemTemplate());

      if (!keepItem.getItemTemplate().isItemDyePermitted()) {
        keepItem.setItemColor(0);
      }
      PacketSendUtility.sendPacket(player, (AionServerPacket) SM_SYSTEM_MESSAGE
          .STR_CHANGE_ITEM_SKIN_SUCCEED(new DescriptionId(keepItem.getItemTemplate().getNameId())));

      return;
    }

    if (keepItem.getItemTemplate().getWeaponType() != extractItem.getItemSkinTemplate().getWeaponType()
        || (extractItem.getItemSkinTemplate().getArmorType() != ArmorType.CLOTHES
            && keepItem.getItemTemplate().getArmorType() != extractItem.getItemSkinTemplate().getArmorType())
        || keepItem.getItemTemplate().getArmorType() == ArmorType.CLOTHES
        || keepItem.getItemTemplate().getItemSlot() != extractItem.getItemSkinTemplate().getItemSlot()) {

      PacketSendUtility.sendPacket(player,
          (AionServerPacket) SM_SYSTEM_MESSAGE.STR_CHANGE_ITEM_SKIN_NOT_COMPATIBLE(
              new DescriptionId(keepItem.getItemTemplate().getNameId()),
              new DescriptionId(extractItem.getItemSkinTemplate().getNameId())));

      return;
    }

    if (keepItem.getItemTemplate().getItemQuality() == ItemQuality.EPIC
        || keepItem.getItemTemplate().getItemQuality() == ItemQuality.MYTHIC) {

      PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_SYSTEM_MESSAGE(1300478,
          new Object[] { new DescriptionId(keepItem.getItemTemplate().getNameId()) }));

      return;
    }

    if (extractItem.getItemTemplate().getItemQuality() == ItemQuality.EPIC
        || extractItem.getItemTemplate().getItemQuality() == ItemQuality.MYTHIC) {

      PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_SYSTEM_MESSAGE(1300482,
          new Object[] { new DescriptionId(extractItem.getItemTemplate().getNameId()) }));

      return;
    }

    if (!ItemService.decreaseKinah(player, remodelCost)) {

      PacketSendUtility.sendPacket(player, (AionServerPacket) SM_SYSTEM_MESSAGE
          .STR_CHANGE_ITEM_SKIN_NOT_ENOUGH_GOLD(new DescriptionId(keepItem.getItemTemplate().getNameId())));

      return;
    }

    ItemService.decreaseItemCount(player, extractItem, 1L);

    keepItem.setItemSkinTemplate(extractItem.getItemSkinTemplate());

    keepItem.setItemColor(extractItem.getItemColor());

    PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_SYSTEM_MESSAGE(1300483,
        new Object[] { new DescriptionId(keepItem.getItemTemplate().getNameId()) }));
  }
}
