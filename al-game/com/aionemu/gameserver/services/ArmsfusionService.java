/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_ITEM;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ArmsfusionService
/*     */ {
/*     */   public static void fusionWeapons(Player player, int firstItemUniqueId, int secondItemUniqueId, int price) {
/*  36 */     Item firstItem = player.getInventory().getItemByObjId(firstItemUniqueId);
/*  37 */     if (firstItem == null) {
/*  38 */       firstItem = player.getEquipment().getEquippedItemByObjId(firstItemUniqueId);
/*     */     }
/*  40 */     Item secondItem = player.getInventory().getItemByObjId(secondItemUniqueId);
/*  41 */     if (secondItem == null) {
/*  42 */       secondItem = player.getEquipment().getEquippedItemByObjId(secondItemUniqueId);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  47 */     if (firstItem == null || secondItem == null || !(player.getTarget() instanceof com.aionemu.gameserver.model.gameobjects.Npc)) {
/*     */       return;
/*     */     }
/*     */     
/*  51 */     if (player.getInventory().getKinahItem().getItemCount() < price) {
/*     */       
/*  53 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_COMPOUND_ERROR_NOT_ENOUGH_MONEY(firstItem.getNameID(), secondItem.getNameID()));
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/*  60 */     if (firstItem.getItemTemplate().getWeaponType() != secondItem.getItemTemplate().getWeaponType()) {
/*     */       
/*  62 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_COMPOUND_ERROR_DIFFERENT_TYPE);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/*  69 */     if (secondItem.getItemTemplate().getLevel() > firstItem.getItemTemplate().getLevel()) {
/*     */       
/*  71 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_COMPOUND_ERROR_MAIN_REQUIRE_HIGHER_LEVEL);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  77 */     firstItem.setFusionedItem(secondItem.getItemTemplate().getTemplateId());
/*  78 */     ItemService.decreaseItemCount(player, secondItem, 1L);
/*     */     
/*  80 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_UPDATE_ITEM(firstItem));
/*     */     
/*  82 */     PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_COMPOUND_SUCCESS(firstItem.getNameID(), secondItem.getNameID()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void breakWeapons(Player player, int weaponToBreakUniqueId) {
/*  88 */     Item weaponToBreak = player.getInventory().getItemByObjId(weaponToBreakUniqueId);
/*  89 */     if (weaponToBreak == null) {
/*  90 */       weaponToBreak = player.getEquipment().getEquippedItemByObjId(weaponToBreakUniqueId);
/*     */     }
/*  92 */     if (weaponToBreak == null || player.getTarget() instanceof com.aionemu.gameserver.model.gameobjects.Npc) {
/*     */       return;
/*     */     }
/*  95 */     if (!weaponToBreak.hasFusionedItem()) {
/*     */       
/*  97 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_DECOMPOUND_ERROR_NOT_AVAILABLE(weaponToBreak.getNameID()));
/*     */       
/*     */       return;
/*     */     } 
/* 101 */     weaponToBreak.setFusionedItem(0);
/*     */     
/* 103 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_UPDATE_ITEM(weaponToBreak));
/*     */     
/* 105 */     PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_COMPOUNDED_ITEM_DECOMPOUND_SUCCESS(weaponToBreak.getNameID()));
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\ArmsfusionService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */