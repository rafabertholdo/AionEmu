/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.account.PlayerAccountData;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DELETE_CHARACTER;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
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
/*    */ 
/*    */ public class CM_DELETE_CHARACTER
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int playOk2;
/*    */   private int chaOid;
/*    */   
/*    */   public CM_DELETE_CHARACTER(int opcode) {
/* 50 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 59 */     this.playOk2 = readD();
/* 60 */     this.chaOid = readD();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 69 */     AionConnection client = (AionConnection)getConnection();
/* 70 */     PlayerAccountData playerAccData = client.getAccount().getPlayerAccountData(this.chaOid);
/* 71 */     if (playerAccData != null && !playerAccData.isLegionMember()) {
/*    */       
/* 73 */       PlayerService.deletePlayer(playerAccData);
/* 74 */       client.sendPacket((AionServerPacket)new SM_DELETE_CHARACTER(this.chaOid, playerAccData.getDeletionTimeInSeconds()));
/*    */     }
/*    */     else {
/*    */       
/* 78 */       client.sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.STR_DELETE_CHARACTER_IN_LEGION());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_DELETE_CHARACTER.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */