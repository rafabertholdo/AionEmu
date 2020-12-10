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
/*    */ public class SM_LS_CONTROL
/*    */   extends LsServerPacket
/*    */ {
/*    */   private final String accountName;
/*    */   private final String adminName;
/*    */   private final String playerName;
/*    */   private final int param;
/*    */   private final int type;
/*    */   
/*    */   public SM_LS_CONTROL(String accountName, String playerName, String adminName, int param, int type) {
/* 45 */     super(5);
/* 46 */     this.accountName = accountName;
/* 47 */     this.param = param;
/* 48 */     this.playerName = playerName;
/* 49 */     this.adminName = adminName;
/* 50 */     this.type = type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(LoginServerConnection con, ByteBuffer buf) {
/* 59 */     writeC(buf, getOpcode());
/* 60 */     writeC(buf, this.type);
/* 61 */     writeS(buf, this.adminName);
/* 62 */     writeS(buf, this.accountName);
/* 63 */     writeS(buf, this.playerName);
/* 64 */     writeC(buf, this.param);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\loginserver\serverpackets\SM_LS_CONTROL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */