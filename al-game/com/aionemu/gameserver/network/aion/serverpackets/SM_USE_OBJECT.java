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
/*    */ public class SM_USE_OBJECT
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int playerObjId;
/*    */   private int targetObjId;
/*    */   private int time;
/*    */   private int actionType;
/*    */   
/*    */   public SM_USE_OBJECT(int playerObjId, int targetObjId, int time, int actionType) {
/* 37 */     this.playerObjId = playerObjId;
/* 38 */     this.targetObjId = targetObjId;
/* 39 */     this.time = time;
/* 40 */     this.actionType = actionType;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 46 */     writeD(buf, this.playerObjId);
/* 47 */     writeD(buf, this.targetObjId);
/* 48 */     writeD(buf, this.time);
/* 49 */     writeC(buf, this.actionType);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_USE_OBJECT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */