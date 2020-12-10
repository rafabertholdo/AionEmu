/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
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
/*    */ public class SM_GATHER_STATUS
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int status;
/*    */   private int playerobjid;
/*    */   private int gatherableobjid;
/*    */   
/*    */   public SM_GATHER_STATUS(int playerobjid, int gatherableobjid, int status) {
/* 37 */     this.playerobjid = playerobjid;
/* 38 */     this.gatherableobjid = gatherableobjid;
/* 39 */     this.status = status;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 50 */     writeD(buf, this.playerobjid);
/* 51 */     writeD(buf, this.gatherableobjid);
/* 52 */     writeH(buf, 0);
/* 53 */     writeC(buf, this.status);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_GATHER_STATUS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */