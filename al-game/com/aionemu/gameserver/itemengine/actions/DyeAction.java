/*    */ package com.aionemu.gameserver.itemengine.actions;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Item;
/*    */ import com.aionemu.gameserver.model.gameobjects.PersistentState;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_ITEM;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_PLAYER_APPEARANCE;
/*    */ import com.aionemu.gameserver.services.ItemService;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "DyeAction")
/*    */ public class DyeAction
/*    */   extends AbstractItemAction
/*    */ {
/*    */   @XmlAttribute(name = "color")
/*    */   protected String color;
/*    */   
/*    */   public boolean canAct(Player player, Item parentItem, Item targetItem) {
/* 50 */     if (targetItem == null) {
/*    */       
/* 52 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_ITEM_ERROR);
/* 53 */       return false;
/*    */     } 
/*    */     
/* 56 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void act(Player player, Item parentItem, Item targetItem) {
/* 62 */     if (targetItem.getItemTemplate().isItemDyePermitted()) {
/*    */       
/* 64 */       if (this.color.equals("no")) {
/*    */         
/* 66 */         targetItem.setItemColor(0);
/*    */       }
/*    */       else {
/*    */         
/* 70 */         int rgb = Integer.parseInt(this.color, 16);
/* 71 */         int bgra = 0xFF | (rgb & 0xFF) << 24 | (rgb & 0xFF00) << 8 | (rgb & 0xFF0000) >>> 8;
/* 72 */         targetItem.setItemColor(bgra);
/*    */       } 
/*    */ 
/*    */       
/* 76 */       if (player.getEquipment().getEquippedItemByObjId(targetItem.getObjectId()) != null) {
/*    */         
/* 78 */         PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_UPDATE_PLAYER_APPEARANCE(player.getObjectId(), player.getEquipment().getEquippedItemsWithoutStigma()), true);
/* 79 */         player.getEquipment().setPersistentState(PersistentState.UPDATE_REQUIRED);
/*    */       
/*    */       }
/*    */       else {
/*    */         
/* 84 */         player.getInventory().setPersistentState(PersistentState.UPDATE_REQUIRED);
/*    */       } 
/* 86 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_UPDATE_ITEM(targetItem));
/* 87 */       ItemService.decreaseItemCount(player, parentItem, 1L);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\itemengine\actions\DyeAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */