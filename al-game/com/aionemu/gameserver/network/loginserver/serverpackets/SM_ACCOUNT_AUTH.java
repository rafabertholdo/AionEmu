/*    */ package com.aionemu.gameserver.network.loginserver.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.network.loginserver.LoginServerConnection;
/*    */ import com.aionemu.gameserver.network.loginserver.LsServerPacket;
/*    */ import java.nio.ByteBuffer;
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
/*    */ public class SM_ACCOUNT_AUTH
/*    */   extends LsServerPacket
/*    */ {
/*    */   private final int accountId;
/*    */   private final int loginOk;
/*    */   private final int playOk1;
/*    */   private final int playOk2;
/*    */   
/*    */   public SM_ACCOUNT_AUTH(int accountId, int loginOk, int playOk1, int playOk2) {
/* 61 */     super(1);
/* 62 */     this.accountId = accountId;
/* 63 */     this.loginOk = loginOk;
/* 64 */     this.playOk1 = playOk1;
/* 65 */     this.playOk2 = playOk2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(LoginServerConnection con, ByteBuffer buf) {
/* 74 */     writeC(buf, getOpcode());
/* 75 */     writeD(buf, this.accountId);
/* 76 */     writeD(buf, this.loginOk);
/* 77 */     writeD(buf, this.playOk1);
/* 78 */     writeD(buf, this.playOk2);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\loginserver\serverpackets\SM_ACCOUNT_AUTH.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */