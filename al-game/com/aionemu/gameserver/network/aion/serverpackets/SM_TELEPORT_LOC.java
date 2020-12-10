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
/*    */ public class SM_TELEPORT_LOC
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int mapId;
/*    */   private float x;
/*    */   private float y;
/*    */   private float z;
/*    */   
/*    */   public SM_TELEPORT_LOC(int mapId, float x, float y, float z) {
/* 38 */     this.mapId = mapId;
/* 39 */     this.x = x;
/* 40 */     this.y = y;
/* 41 */     this.z = z;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 50 */     writeC(buf, 3);
/* 51 */     writeC(buf, 144);
/* 52 */     writeC(buf, 158);
/* 53 */     writeD(buf, this.mapId);
/* 54 */     writeF(buf, this.x);
/* 55 */     writeF(buf, this.y);
/* 56 */     writeF(buf, this.z);
/* 57 */     writeC(buf, 0);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_TELEPORT_LOC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */