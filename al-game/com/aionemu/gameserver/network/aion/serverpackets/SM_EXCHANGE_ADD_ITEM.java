/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Item;
/*    */ import com.aionemu.gameserver.model.items.ItemId;
/*    */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.InventoryPacket;
/*    */ import java.nio.ByteBuffer;
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
/*    */ public class SM_EXCHANGE_ADD_ITEM
/*    */   extends InventoryPacket
/*    */ {
/*    */   private int action;
/*    */   private Item item;
/*    */   
/*    */   public SM_EXCHANGE_ADD_ITEM(int action, Item item) {
/* 39 */     this.action = action;
/* 40 */     this.item = item;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 47 */     writeC(buf, this.action);
/*    */     
/* 49 */     writeGeneralInfo(buf, this.item);
/*    */     
/* 51 */     ItemTemplate itemTemplate = this.item.getItemTemplate();
/*    */     
/* 53 */     if (itemTemplate.getTemplateId() == ItemId.KINAH.value()) {
/*    */       
/* 55 */       writeKinah(buf, this.item, true);
/*    */     }
/* 57 */     else if (itemTemplate.isWeapon()) {
/*    */       
/* 59 */       writeWeaponInfo(buf, this.item, true);
/*    */     }
/* 61 */     else if (itemTemplate.isArmor()) {
/*    */       
/* 63 */       writeArmorInfo(buf, this.item, true, false, false);
/*    */     }
/*    */     else {
/*    */       
/* 67 */       writeGeneralItemInfo(buf, this.item, false, false);
/* 68 */       writeC(buf, 0);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeGeneralInfo(ByteBuffer buf, Item item) {
/* 75 */     ItemTemplate itemTemplate = item.getItemTemplate();
/* 76 */     writeD(buf, itemTemplate.getTemplateId());
/* 77 */     writeD(buf, item.getObjectId());
/* 78 */     writeH(buf, 36);
/* 79 */     writeD(buf, itemTemplate.getNameId());
/* 80 */     writeH(buf, 0);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_EXCHANGE_ADD_ITEM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */