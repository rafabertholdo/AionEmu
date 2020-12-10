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
/*    */ public class SM_FLY_TIME
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int currentFp;
/*    */   private int maxFp;
/*    */   
/*    */   public SM_FLY_TIME(int currentFp, int maxFp) {
/* 35 */     this.currentFp = currentFp;
/* 36 */     this.maxFp = maxFp;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 45 */     writeD(buf, this.currentFp);
/* 46 */     writeD(buf, this.maxFp);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_FLY_TIME.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */