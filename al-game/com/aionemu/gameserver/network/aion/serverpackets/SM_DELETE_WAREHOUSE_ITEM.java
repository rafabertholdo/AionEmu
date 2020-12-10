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
/*    */ public class SM_DELETE_WAREHOUSE_ITEM
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int warehouseType;
/*    */   private int itemObjId;
/*    */   
/*    */   public SM_DELETE_WAREHOUSE_ITEM(int warehouseType, int itemObjId) {
/* 38 */     this.warehouseType = warehouseType;
/* 39 */     this.itemObjId = itemObjId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 46 */     writeC(buf, this.warehouseType);
/* 47 */     writeD(buf, this.itemObjId);
/* 48 */     writeC(buf, 14);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_DELETE_WAREHOUSE_ITEM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */