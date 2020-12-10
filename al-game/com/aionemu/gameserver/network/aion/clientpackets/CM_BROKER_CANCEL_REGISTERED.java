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
/*    */ public class CM_BROKER_CANCEL_REGISTERED
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int npcId;
/*    */   private int brokerItemId;
/*    */   
/*    */   public CM_BROKER_CANCEL_REGISTERED(int opcode) {
/* 35 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 41 */     this.npcId = readD();
/* 42 */     this.brokerItemId = readD();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 48 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/*    */     
/* 50 */     BrokerService.getInstance().cancelRegisteredItem(player, this.brokerItemId);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_BROKER_CANCEL_REGISTERED.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */