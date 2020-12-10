/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Kisk;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
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
/*    */ public class SM_SET_BIND_POINT
/*    */   extends AionServerPacket
/*    */ {
/*    */   private final int mapId;
/*    */   private final float x;
/*    */   private final float y;
/*    */   private final float z;
/*    */   private final Kisk kisk;
/*    */   
/*    */   public SM_SET_BIND_POINT(int mapId, float x, float y, float z, Player player) {
/* 43 */     this.mapId = mapId;
/* 44 */     this.x = x;
/* 45 */     this.y = y;
/* 46 */     this.z = z;
/* 47 */     this.kisk = player.getKisk();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 57 */     writeC(buf, (this.kisk == null) ? 0 : 4);
/*    */     
/* 59 */     writeC(buf, 1);
/* 60 */     writeD(buf, this.mapId);
/* 61 */     writeF(buf, this.x);
/* 62 */     writeF(buf, this.y);
/* 63 */     writeF(buf, this.z);
/* 64 */     writeD(buf, (this.kisk == null) ? 0 : this.kisk.getObjectId());
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_SET_BIND_POINT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */