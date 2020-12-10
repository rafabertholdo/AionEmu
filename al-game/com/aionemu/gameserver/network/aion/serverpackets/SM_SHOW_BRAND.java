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
/*    */ public class SM_SHOW_BRAND
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int brandId;
/*    */   private int targetObjectId;
/*    */   
/*    */   public SM_SHOW_BRAND(int brandId, int targetObjectId) {
/* 34 */     this.brandId = brandId;
/* 35 */     this.targetObjectId = targetObjectId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 43 */     writeH(buf, 1);
/* 44 */     writeD(buf, this.brandId);
/* 45 */     writeD(buf, this.targetObjectId);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_SHOW_BRAND.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */