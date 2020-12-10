/*    */ package com.aionemu.gameserver.network.loginserver.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.account.AccountTime;
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
/*    */ public class CM_ACOUNT_AUTH_RESPONSE
/*    */   extends LsClientPacket
/*    */ {
/*    */   private int accountId;
/*    */   private boolean result;
/*    */   private String accountName;
/*    */   private AccountTime accountTime;
/*    */   private byte accessLevel;
/*    */   private byte membership;
/*    */   
/*    */   public CM_ACOUNT_AUTH_RESPONSE(int opcode) {
/* 63 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 72 */     this.accountId = readD();
/* 73 */     this.result = (readC() == 1);
/*    */     
/* 75 */     if (this.result) {
/*    */       
/* 77 */       this.accountName = readS();
/* 78 */       this.accountTime = new AccountTime();
/*    */       
/* 80 */       this.accountTime.setAccumulatedOnlineTime(readQ());
/* 81 */       this.accountTime.setAccumulatedRestTime(readQ());
/*    */       
/* 83 */       this.accessLevel = (byte)readC();
/* 84 */       this.membership = (byte)readC();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 94 */     LoginServer.getInstance().accountAuthenticationResponse(this.accountId, this.accountName, this.result, this.accountTime, this.accessLevel, this.membership);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\loginserver\clientpackets\CM_ACOUNT_AUTH_RESPONSE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */