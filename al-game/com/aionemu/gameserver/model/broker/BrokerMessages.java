/*    */ package com.aionemu.gameserver.model.broker;
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
/*    */ public enum BrokerMessages
/*    */ {
/* 25 */   NO_ENOUGHT_KINAH(1),
/* 26 */   CANT_REGISTER_ITEM(2);
/*    */   
/*    */   private int id;
/*    */ 
/*    */   
/*    */   BrokerMessages(int id) {
/* 32 */     this.id = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 37 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\broker\BrokerMessages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */