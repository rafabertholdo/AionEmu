/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.account.Account;
/*    */ import com.aionemu.gameserver.model.account.PlayerAccountData;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_RESTORE_CHARACTER;
/*    */ import com.aionemu.gameserver.services.PlayerService;
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
/*    */ public class CM_RESTORE_CHARACTER
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int playOk2;
/*    */   private int chaOid;
/*    */   
/*    */   public CM_RESTORE_CHARACTER(int opcode) {
/* 49 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 58 */     this.playOk2 = readD();
/* 59 */     this.chaOid = readD();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 68 */     Account account = ((AionConnection)getConnection()).getAccount();
/* 69 */     PlayerAccountData pad = account.getPlayerAccountData(this.chaOid);
/*    */     
/* 71 */     boolean success = (pad != null && PlayerService.cancelPlayerDeletion(pad));
/* 72 */     sendPacket((AionServerPacket)new SM_RESTORE_CHARACTER(this.chaOid, success));
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_RESTORE_CHARACTER.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */