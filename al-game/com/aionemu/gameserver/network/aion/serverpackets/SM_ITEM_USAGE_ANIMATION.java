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
/*    */ public class SM_ITEM_USAGE_ANIMATION
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int playerObjId;
/*    */   private int itemObjId;
/*    */   private int itemId;
/*    */   private int time;
/*    */   private int end;
/*    */   private int unk;
/*    */   
/*    */   public SM_ITEM_USAGE_ANIMATION(int playerObjId, int itemObjId, int itemId) {
/* 38 */     this.playerObjId = playerObjId;
/* 39 */     this.itemObjId = itemObjId;
/* 40 */     this.itemId = itemId;
/* 41 */     this.time = 0;
/* 42 */     this.end = 1;
/* 43 */     this.unk = 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public SM_ITEM_USAGE_ANIMATION(int playerObjId, int itemObjId, int itemId, int time, int end, int unk) {
/* 48 */     this.playerObjId = playerObjId;
/* 49 */     this.itemObjId = itemObjId;
/* 50 */     this.itemId = itemId;
/* 51 */     this.time = time;
/* 52 */     this.end = end;
/* 53 */     this.unk = unk;
/*    */   }
/*    */ 
/*    */   
/*    */   public SM_ITEM_USAGE_ANIMATION(int playerObjId, int itemObjId, int itemId, int time, int end) {
/* 58 */     this.playerObjId = playerObjId;
/* 59 */     this.itemObjId = itemObjId;
/* 60 */     this.itemId = itemId;
/* 61 */     this.time = time;
/* 62 */     this.end = end;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 68 */     writeD(buf, this.playerObjId);
/* 69 */     writeD(buf, this.playerObjId);
/*    */     
/* 71 */     writeD(buf, this.itemObjId);
/* 72 */     writeD(buf, this.itemId);
/*    */     
/* 74 */     writeD(buf, this.time);
/* 75 */     writeC(buf, this.end);
/* 76 */     writeC(buf, 1);
/* 77 */     writeC(buf, 0);
/* 78 */     writeD(buf, this.unk);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_ITEM_USAGE_ANIMATION.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */