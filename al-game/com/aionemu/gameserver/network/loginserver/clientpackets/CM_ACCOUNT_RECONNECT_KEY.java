/*    */ package com.aionemu.gameserver.network.loginserver.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.network.loginserver.LoginServer;
/*    */ import com.aionemu.gameserver.network.loginserver.LsClientPacket;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CM_ACCOUNT_RECONNECT_KEY
/*    */   extends LsClientPacket
/*    */ {
/*    */   private int accountId;
/*    */   private int reconnectKey;
/*    */   
/*    */   public CM_ACCOUNT_RECONNECT_KEY(int opcode) {
/* 45 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 54 */     this.accountId = readD();
/* 55 */     this.reconnectKey = readD();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 64 */     LoginServer.getInstance().authReconnectionResponse(this.accountId, this.reconnectKey);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\loginserver\clientpackets\CM_ACCOUNT_RECONNECT_KEY.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */