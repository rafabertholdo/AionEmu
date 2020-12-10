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
/*    */ public class SM_PRIVATE_STORE_NAME
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int playerObjId;
/*    */   private String name;
/*    */   
/*    */   public SM_PRIVATE_STORE_NAME(int playerObjId, String name) {
/* 35 */     this.playerObjId = playerObjId;
/* 36 */     this.name = name;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 42 */     writeD(buf, this.playerObjId);
/* 43 */     writeS(buf, this.name);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_PRIVATE_STORE_NAME.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */