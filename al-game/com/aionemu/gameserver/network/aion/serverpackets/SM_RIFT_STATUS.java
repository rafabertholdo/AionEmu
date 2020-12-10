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
/*    */ public class SM_RIFT_STATUS
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int usedEntries;
/*    */   private int maxEntries;
/*    */   private int maxLevel;
/*    */   private int targetObjectId;
/*    */   
/*    */   public SM_RIFT_STATUS(int targetObjId, int usedEntries, int maxEntries, int maxLevel) {
/* 37 */     this.targetObjectId = targetObjId;
/* 38 */     this.usedEntries = usedEntries;
/* 39 */     this.maxEntries = maxEntries;
/* 40 */     this.maxLevel = maxLevel;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 47 */     writeD(buf, this.targetObjectId);
/* 48 */     writeD(buf, this.usedEntries);
/* 49 */     writeD(buf, this.maxEntries);
/* 50 */     writeD(buf, 6793);
/* 51 */     writeD(buf, 25);
/* 52 */     writeD(buf, this.maxLevel);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_RIFT_STATUS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */