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
/*    */ 
/*    */ public class SM_BAN
/*    */   extends LsServerPacket
/*    */ {
/*    */   private final byte type;
/*    */   private final int accountId;
/*    */   private final String ip;
/*    */   private final int time;
/*    */   private final int adminObjId;
/*    */   
/*    */   public SM_BAN(byte type, int accountId, String ip, int time, int adminObjId) {
/* 63 */     super(6);
/*    */     
/* 65 */     this.type = type;
/* 66 */     this.accountId = accountId;
/* 67 */     this.ip = ip;
/* 68 */     this.time = time;
/* 69 */     this.adminObjId = adminObjId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(LoginServerConnection con, ByteBuffer buf) {
/* 77 */     writeC(buf, getOpcode());
/*    */     
/* 79 */     writeC(buf, this.type);
/* 80 */     writeD(buf, this.accountId);
/* 81 */     writeS(buf, this.ip);
/* 82 */     writeD(buf, this.time);
/* 83 */     writeD(buf, this.adminObjId);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\loginserver\serverpackets\SM_BAN.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */