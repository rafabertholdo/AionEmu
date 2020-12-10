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
/*    */ public class SM_NAME_CHANGE
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int playerObjectId;
/*    */   private String oldName;
/*    */   private String newName;
/*    */   
/*    */   public SM_NAME_CHANGE(int playerObjectId, String oldName, String newName) {
/* 37 */     this.playerObjectId = playerObjectId;
/* 38 */     this.oldName = oldName;
/* 39 */     this.newName = newName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 48 */     writeD(buf, 0);
/* 49 */     writeD(buf, 0);
/* 50 */     writeD(buf, this.playerObjectId);
/* 51 */     writeS(buf, this.oldName);
/* 52 */     writeS(buf, this.newName);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_NAME_CHANGE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */