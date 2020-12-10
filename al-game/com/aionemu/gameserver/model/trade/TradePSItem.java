/*    */ package com.aionemu.gameserver.model.trade;
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
/*    */ public class TradePSItem
/*    */   extends TradeItem
/*    */ {
/*    */   private int itemObjId;
/*    */   private long price;
/*    */   
/*    */   public TradePSItem(int itemObjId, int itemId, long count, long price) {
/* 34 */     super(itemId, count);
/* 35 */     setPrice(price);
/* 36 */     setItemObjId(itemObjId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setPrice(long price) {
/* 45 */     this.price = price;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getPrice() {
/* 53 */     return this.price;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setItemObjId(int itemObjId) {
/* 62 */     this.itemObjId = itemObjId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getItemObjId() {
/* 70 */     return this.itemObjId;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\trade\TradePSItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */