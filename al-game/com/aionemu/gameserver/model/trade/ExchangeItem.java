/*    */ package com.aionemu.gameserver.model.trade;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Item;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExchangeItem
/*    */ {
/*    */   private int itemObjId;
/*    */   private long itemCount;
/*    */   private int itemDesc;
/*    */   private Item item;
/*    */   
/*    */   public ExchangeItem(int itemObjId, long itemCount, Item item) {
/* 41 */     this.itemObjId = itemObjId;
/* 42 */     this.itemCount = itemCount;
/* 43 */     this.item = item;
/* 44 */     this.itemDesc = item.getItemTemplate().getNameId();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setItem(Item item) {
/* 52 */     this.item = item;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addCount(long countToAdd) {
/* 60 */     this.itemCount += countToAdd;
/* 61 */     this.item.setItemCount(this.itemCount);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Item getItem() {
/* 69 */     return this.item;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getItemObjId() {
/* 77 */     return this.itemObjId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getItemCount() {
/* 85 */     return this.itemCount;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getItemDesc() {
/* 93 */     return this.itemDesc;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\trade\ExchangeItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */