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
/*    */ public class SM_DP_INFO
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int playerObjectId;
/*    */   private int currentDp;
/*    */   
/*    */   public SM_DP_INFO(int playerObjectId, int currentDp) {
/* 36 */     this.playerObjectId = playerObjectId;
/* 37 */     this.currentDp = currentDp;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 46 */     writeD(buf, this.playerObjectId);
/* 47 */     writeH(buf, this.currentDp);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_DP_INFO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */