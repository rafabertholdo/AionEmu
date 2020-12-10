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
/*    */ public class SM_LEGION_LEAVE_MEMBER
/*    */   extends AionServerPacket
/*    */ {
/*    */   private String name;
/*    */   private String name1;
/*    */   private int playerObjId;
/*    */   private int msgId;
/*    */   
/*    */   public SM_LEGION_LEAVE_MEMBER(int msgId, int playerObjId, String name) {
/* 38 */     this.msgId = msgId;
/* 39 */     this.playerObjId = playerObjId;
/* 40 */     this.name = name;
/*    */   }
/*    */ 
/*    */   
/*    */   public SM_LEGION_LEAVE_MEMBER(int msgId, int playerObjId, String name, String name1) {
/* 45 */     this.msgId = msgId;
/* 46 */     this.playerObjId = playerObjId;
/* 47 */     this.name = name;
/* 48 */     this.name1 = name1;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeImpl(AionConnection con, ByteBuffer buf) {
/* 54 */     writeD(buf, this.playerObjId);
/* 55 */     writeC(buf, 0);
/* 56 */     writeD(buf, 0);
/* 57 */     writeD(buf, this.msgId);
/* 58 */     writeS(buf, this.name);
/* 59 */     writeS(buf, this.name1);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_LEGION_LEAVE_MEMBER.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */