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
/*    */ public class SM_LEGION_UPDATE_TITLE
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int objectId;
/*    */   private int legionId;
/*    */   private String legionName;
/*    */   private int rank;
/*    */   
/*    */   public SM_LEGION_UPDATE_TITLE(int objectId, int legionId, String legionName, int rank) {
/* 37 */     this.objectId = objectId;
/* 38 */     this.legionId = legionId;
/* 39 */     this.legionName = legionName;
/* 40 */     this.rank = rank;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeImpl(AionConnection con, ByteBuffer buf) {
/* 46 */     writeD(buf, this.objectId);
/* 47 */     writeD(buf, this.legionId);
/* 48 */     writeS(buf, this.legionName);
/* 49 */     writeC(buf, this.rank);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_LEGION_UPDATE_TITLE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */