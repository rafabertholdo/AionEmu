/*    */ package com.aionemu.gameserver.model.trade;
/*    */ 
/*    */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
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
/*    */ public class TradeItem
/*    */ {
/*    */   private int itemId;
/*    */   private long count;
/*    */   private ItemTemplate itemTemplate;
/*    */   
/*    */   public TradeItem(int itemId, long count) {
/* 34 */     this.itemId = itemId;
/* 35 */     this.count = count;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemTemplate getItemTemplate() {
/* 43 */     return this.itemTemplate;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setItemTemplate(ItemTemplate itemTemplate) {
/* 52 */     this.itemTemplate = itemTemplate;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getItemId() {
/* 60 */     return this.itemId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getCount() {
/* 68 */     return this.count;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void decreaseCount(long decreaseCount) {
/* 76 */     if (decreaseCount < this.count)
/* 77 */       this.count -= decreaseCount; 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\trade\TradeItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */