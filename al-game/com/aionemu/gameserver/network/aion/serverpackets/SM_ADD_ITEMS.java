/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Item;
/*    */ import com.aionemu.gameserver.model.items.ItemId;
/*    */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.InventoryPacket;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.List;
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
/*    */ public class SM_ADD_ITEMS
/*    */   extends InventoryPacket
/*    */ {
/*    */   private List<Item> items;
/*    */   private int size;
/*    */   
/*    */   public SM_ADD_ITEMS(List<Item> items) {
/* 40 */     this.items = items;
/* 41 */     this.size = items.size();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 51 */     writeH(buf, 25);
/* 52 */     writeH(buf, this.size);
/* 53 */     for (Item item : this.items) {
/*    */       
/* 55 */       writeGeneralInfo(buf, item);
/*    */       
/* 57 */       ItemTemplate itemTemplate = item.getItemTemplate();
/*    */       
/* 59 */       if (itemTemplate.getTemplateId() == ItemId.KINAH.value()) {
/*    */         
/* 61 */         writeKinah(buf, item, true); continue;
/*    */       } 
/* 63 */       if (itemTemplate.isWeapon()) {
/*    */         
/* 65 */         writeWeaponInfo(buf, item, true); continue;
/*    */       } 
/* 67 */       if (itemTemplate.isArmor()) {
/*    */         
/* 69 */         writeArmorInfo(buf, item, true, false, false); continue;
/*    */       } 
/* 71 */       if (itemTemplate.isStigma()) {
/*    */         
/* 73 */         writeStigmaInfo(buf, item);
/*    */         
/*    */         continue;
/*    */       } 
/* 77 */       writeGeneralItemInfo(buf, item, false, false);
/* 78 */       writeC(buf, 0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_ADD_ITEMS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */