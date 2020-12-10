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
/*    */ public class SM_UPDATE_NOTE
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int targetObjId;
/*    */   private String note;
/*    */   
/*    */   public SM_UPDATE_NOTE(int targetObjId, String note) {
/* 34 */     this.targetObjId = targetObjId;
/* 35 */     this.note = note;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 43 */     writeD(buf, this.targetObjId);
/* 44 */     writeS(buf, this.note);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_UPDATE_NOTE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */