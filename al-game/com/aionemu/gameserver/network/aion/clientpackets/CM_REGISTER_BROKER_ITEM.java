/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.services.BrokerService;
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
/*    */ public class CM_REGISTER_BROKER_ITEM
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int brokerId;
/*    */   private int itemUniqueId;
/*    */   private int price;
/*    */   private int itemCount;
/*    */   
/*    */   public CM_REGISTER_BROKER_ITEM(int opcode) {
/* 38 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 44 */     this.brokerId = readD();
/* 45 */     this.itemUniqueId = readD();
/* 46 */     this.price = readD();
/* 47 */     readD();
/* 48 */     this.itemCount = readH();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 54 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/*    */     
/* 56 */     BrokerService.getInstance().registerItem(player, this.itemUniqueId, this.price);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_REGISTER_BROKER_ITEM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */