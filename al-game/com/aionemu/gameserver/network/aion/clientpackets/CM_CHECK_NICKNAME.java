/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_NICKNAME_CHECK_RESPONSE;
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
/*    */ public class CM_CHECK_NICKNAME
/*    */   extends AionClientPacket
/*    */ {
/*    */   private String nick;
/*    */   
/*    */   public CM_CHECK_NICKNAME(int opcode) {
/* 44 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 53 */     this.nick = readS();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 62 */     AionConnection client = (AionConnection)getConnection();
/*    */     
/* 64 */     if (!PlayerService.isValidName(this.nick)) {
/*    */       
/* 66 */       client.sendPacket((AionServerPacket)new SM_NICKNAME_CHECK_RESPONSE(5));
/*    */     }
/* 68 */     else if (!PlayerService.isFreeName(this.nick)) {
/*    */       
/* 70 */       client.sendPacket((AionServerPacket)new SM_NICKNAME_CHECK_RESPONSE(10));
/*    */     }
/*    */     else {
/*    */       
/* 74 */       client.sendPacket((AionServerPacket)new SM_NICKNAME_CHECK_RESPONSE(0));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_CHECK_NICKNAME.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */