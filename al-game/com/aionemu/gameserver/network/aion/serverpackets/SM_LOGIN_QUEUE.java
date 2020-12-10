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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SM_LOGIN_QUEUE
/*    */   extends AionServerPacket
/*    */ {
/* 35 */   private int waitingPosition = 5;
/* 36 */   private int waitingTime = 60;
/* 37 */   private int waitingCount = 50;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeImpl(AionConnection con, ByteBuffer buf) {
/* 43 */     writeD(buf, this.waitingPosition);
/* 44 */     writeD(buf, this.waitingTime);
/* 45 */     writeD(buf, this.waitingCount);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_LOGIN_QUEUE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */