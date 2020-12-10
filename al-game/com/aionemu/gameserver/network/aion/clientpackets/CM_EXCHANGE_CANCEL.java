/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.services.ExchangeService;
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
/*    */ public class CM_EXCHANGE_CANCEL
/*    */   extends AionClientPacket
/*    */ {
/*    */   public CM_EXCHANGE_CANCEL(int opcode) {
/* 35 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 47 */     Player activePlayer = ((AionConnection)getConnection()).getActivePlayer();
/* 48 */     ExchangeService.getInstance().cancelExchange(activePlayer);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_EXCHANGE_CANCEL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */