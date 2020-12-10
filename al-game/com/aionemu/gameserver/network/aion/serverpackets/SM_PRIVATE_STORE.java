/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Item;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.PrivateStore;
/*    */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*    */ import com.aionemu.gameserver.model.trade.TradePSItem;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.InventoryPacket;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.LinkedHashMap;
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
/*    */ public class SM_PRIVATE_STORE
/*    */   extends InventoryPacket
/*    */ {
/*    */   private PrivateStore store;
/*    */   
/*    */   public SM_PRIVATE_STORE(PrivateStore store) {
/* 40 */     this.store = store;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 46 */     if (this.store != null) {
/*    */       
/* 48 */       Player storePlayer = this.store.getOwner();
/* 49 */       LinkedHashMap<Integer, TradePSItem> soldItems = this.store.getSoldItems();
/*    */       
/* 51 */       writeD(buf, storePlayer.getObjectId());
/* 52 */       writeH(buf, soldItems.size());
/* 53 */       for (Integer itemObjId : soldItems.keySet()) {
/*    */         
/* 55 */         Item item = storePlayer.getInventory().getItemByObjId(itemObjId.intValue());
/* 56 */         TradePSItem tradeItem = this.store.getTradeItemById(itemObjId.intValue());
/* 57 */         long price = tradeItem.getPrice();
/* 58 */         writeD(buf, itemObjId.intValue());
/* 59 */         writeD(buf, item.getItemTemplate().getTemplateId());
/* 60 */         writeH(buf, (int)tradeItem.getCount());
/* 61 */         writeD(buf, (int)price);
/*    */         
/* 63 */         ItemTemplate itemTemplate = item.getItemTemplate();
/*    */         
/* 65 */         if (itemTemplate.isWeapon()) {
/*    */           
/* 67 */           writeWeaponInfo(buf, item, false, false, true, false); continue;
/*    */         } 
/* 69 */         if (itemTemplate.isArmor()) {
/*    */           
/* 71 */           writeArmorInfo(buf, item, false, true, false);
/*    */           
/*    */           continue;
/*    */         } 
/* 75 */         writeGeneralItemInfo(buf, item, true, false);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_PRIVATE_STORE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */