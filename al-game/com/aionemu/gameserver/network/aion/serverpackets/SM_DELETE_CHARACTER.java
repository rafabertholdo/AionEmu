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
/*    */ 
/*    */ public class SM_DELETE_CHARACTER
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int playerObjId;
/*    */   private int deletionTime;
/*    */   
/*    */   public SM_DELETE_CHARACTER(int playerObjId, int deletionTime) {
/* 40 */     this.playerObjId = playerObjId;
/* 41 */     this.deletionTime = deletionTime;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 50 */     if (this.playerObjId != 0) {
/*    */       
/* 52 */       writeD(buf, 0);
/* 53 */       writeD(buf, this.playerObjId);
/* 54 */       writeD(buf, this.deletionTime);
/*    */     }
/*    */     else {
/*    */       
/* 58 */       writeD(buf, 16);
/* 59 */       writeD(buf, 0);
/* 60 */       writeD(buf, 0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_DELETE_CHARACTER.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */