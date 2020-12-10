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
/*    */ public class SM_EXCHANGE_ADD_KINAH
/*    */   extends AionServerPacket
/*    */ {
/*    */   private long itemCount;
/*    */   private int action;
/*    */   
/*    */   public SM_EXCHANGE_ADD_KINAH(long itemCount, int action) {
/* 34 */     this.itemCount = itemCount;
/* 35 */     this.action = action;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 41 */     writeC(buf, this.action);
/* 42 */     writeD(buf, (int)this.itemCount);
/* 43 */     writeD(buf, 0);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_EXCHANGE_ADD_KINAH.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */