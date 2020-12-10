/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.gameserver.model.DescriptionId;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Storage;
/*     */ import com.aionemu.gameserver.model.templates.item.ArmorType;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemQuality;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemRemodelService
/*     */ {
/*     */   public static void remodelItem(Player player, int keepItemObjId, int extractItemObjId) {
/*  42 */     Storage inventory = player.getInventory();
/*  43 */     Item keepItem = inventory.getItemByObjId(keepItemObjId);
/*  44 */     Item extractItem = inventory.getItemByObjId(extractItemObjId);
/*     */     
/*  46 */     long remodelCost = player.getPrices().getPriceForService(1000L);
/*     */     
/*  48 */     if (keepItem == null || extractItem == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  54 */     if (player.getLevel() < 20) {
/*     */ 
/*     */       
/*  57 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_CHANGE_ITEM_SKIN_PC_LEVEL_LIMIT);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  62 */     if (extractItem.getItemTemplate().getTemplateId() == 168100000) {
/*     */       
/*  64 */       if (keepItem.getItemTemplate() == keepItem.getItemSkinTemplate()) {
/*     */         
/*  66 */         PacketSendUtility.sendMessage(player, "That item does not have a remodeled skin to remove.");
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*  71 */       if (!ItemService.decreaseKinah(player, remodelCost)) {
/*     */         
/*  73 */         PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_CHANGE_ITEM_SKIN_NOT_ENOUGH_GOLD(new DescriptionId(keepItem.getItemTemplate().getNameId())));
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*  79 */       ItemService.decreaseItemCount(player, extractItem, 1L);
/*     */ 
/*     */       
/*  82 */       keepItem.setItemSkinTemplate(keepItem.getItemTemplate());
/*     */ 
/*     */       
/*  85 */       if (!keepItem.getItemTemplate().isItemDyePermitted()) {
/*  86 */         keepItem.setItemColor(0);
/*     */       }
/*  88 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_CHANGE_ITEM_SKIN_SUCCEED(new DescriptionId(keepItem.getItemTemplate().getNameId())));
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  94 */     if (keepItem.getItemTemplate().getWeaponType() != extractItem.getItemSkinTemplate().getWeaponType() || (extractItem.getItemSkinTemplate().getArmorType() != ArmorType.CLOTHES && keepItem.getItemTemplate().getArmorType() != extractItem.getItemSkinTemplate().getArmorType()) || keepItem.getItemTemplate().getArmorType() == ArmorType.CLOTHES || keepItem.getItemTemplate().getItemSlot() != extractItem.getItemSkinTemplate().getItemSlot()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 100 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_CHANGE_ITEM_SKIN_NOT_COMPATIBLE(new DescriptionId(keepItem.getItemTemplate().getNameId()), new DescriptionId(extractItem.getItemSkinTemplate().getNameId())));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 111 */     if (keepItem.getItemTemplate().getItemQuality() == ItemQuality.EPIC || keepItem.getItemTemplate().getItemQuality() == ItemQuality.MYTHIC) {
/*     */ 
/*     */       
/* 114 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SYSTEM_MESSAGE(1300478, new Object[] { new DescriptionId(keepItem.getItemTemplate().getNameId()) }));
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 120 */     if (extractItem.getItemTemplate().getItemQuality() == ItemQuality.EPIC || extractItem.getItemTemplate().getItemQuality() == ItemQuality.MYTHIC) {
/*     */ 
/*     */       
/* 123 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SYSTEM_MESSAGE(1300482, new Object[] { new DescriptionId(extractItem.getItemTemplate().getNameId()) }));
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 131 */     if (!ItemService.decreaseKinah(player, remodelCost)) {
/*     */       
/* 133 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_CHANGE_ITEM_SKIN_NOT_ENOUGH_GOLD(new DescriptionId(keepItem.getItemTemplate().getNameId())));
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 139 */     ItemService.decreaseItemCount(player, extractItem, 1L);
/*     */ 
/*     */     
/* 142 */     keepItem.setItemSkinTemplate(extractItem.getItemSkinTemplate());
/*     */ 
/*     */     
/* 145 */     keepItem.setItemColor(extractItem.getItemColor());
/*     */     
/* 147 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SYSTEM_MESSAGE(1300483, new Object[] { new DescriptionId(keepItem.getItemTemplate().getNameId()) }));
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\ItemRemodelService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */