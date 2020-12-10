package com.aionemu.gameserver.services;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_ITEM;
import com.aionemu.gameserver.utils.PacketSendUtility;
























public class ArmsfusionService
{
  public static void fusionWeapons(Player player, int firstItemUniqueId, int secondItemUniqueId, int price) {
    Item firstItem = player.getInventory().getItemByObjId(firstItemUniqueId);
    if (firstItem == null) {
      firstItem = player.getEquipment().getEquippedItemByObjId(firstItemUniqueId);
    }
    Item secondItem = player.getInventory().getItemByObjId(secondItemUniqueId);
    if (secondItem == null) {
      secondItem = player.getEquipment().getEquippedItemByObjId(secondItemUniqueId);
    }


    
    if (firstItem == null || secondItem == null || !(player.getTarget() instanceof com.aionemu.gameserver.model.gameobjects.Npc)) {
      return;
    }
    
    if (player.getInventory().getKinahItem().getItemCount() < price) {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_COMPOUND_ERROR_NOT_ENOUGH_MONEY(firstItem.getNameID(), secondItem.getNameID()));

      
      return;
    } 

    
    if (firstItem.getItemTemplate().getWeaponType() != secondItem.getItemTemplate().getWeaponType()) {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_COMPOUND_ERROR_DIFFERENT_TYPE);

      
      return;
    } 

    
    if (secondItem.getItemTemplate().getLevel() > firstItem.getItemTemplate().getLevel()) {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_COMPOUND_ERROR_MAIN_REQUIRE_HIGHER_LEVEL);

      
      return;
    } 
    
    firstItem.setFusionedItem(secondItem.getItemTemplate().getTemplateId());
    ItemService.decreaseItemCount(player, secondItem, 1L);
    
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_UPDATE_ITEM(firstItem));
    
    PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_COMPOUND_SUCCESS(firstItem.getNameID(), secondItem.getNameID()));
  }


  
  public static void breakWeapons(Player player, int weaponToBreakUniqueId) {
    Item weaponToBreak = player.getInventory().getItemByObjId(weaponToBreakUniqueId);
    if (weaponToBreak == null) {
      weaponToBreak = player.getEquipment().getEquippedItemByObjId(weaponToBreakUniqueId);
    }
    if (weaponToBreak == null || player.getTarget() instanceof com.aionemu.gameserver.model.gameobjects.Npc) {
      return;
    }
    if (!weaponToBreak.hasFusionedItem()) {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_DECOMPOUND_ERROR_NOT_AVAILABLE(weaponToBreak.getNameID()));
      
      return;
    } 
    weaponToBreak.setFusionedItem(0);
    
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_UPDATE_ITEM(weaponToBreak));
    
    PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_COMPOUNDED_ITEM_DECOMPOUND_SUCCESS(weaponToBreak.getNameID()));
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\ArmsfusionService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
