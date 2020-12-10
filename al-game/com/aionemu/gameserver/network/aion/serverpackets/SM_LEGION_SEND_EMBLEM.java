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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SM_LEGION_SEND_EMBLEM
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int legionId;
/*    */   private int emblemId;
/*    */   private int color_r;
/*    */   private int color_g;
/*    */   private int color_b;
/*    */   private String legionName;
/*    */   
/*    */   public SM_LEGION_SEND_EMBLEM(int legionId, int emblemId, int color_r, int color_g, int color_b, String legionName) {
/* 45 */     this.legionId = legionId;
/* 46 */     this.emblemId = emblemId;
/* 47 */     this.color_r = color_r;
/* 48 */     this.color_g = color_g;
/* 49 */     this.color_b = color_b;
/* 50 */     this.legionName = legionName;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeImpl(AionConnection con, ByteBuffer buf) {
/* 56 */     writeD(buf, this.legionId);
/* 57 */     writeH(buf, this.emblemId);
/* 58 */     writeD(buf, 0);
/* 59 */     writeC(buf, 255);
/* 60 */     writeC(buf, this.color_r);
/* 61 */     writeC(buf, this.color_g);
/* 62 */     writeC(buf, this.color_b);
/* 63 */     writeS(buf, this.legionName);
/* 64 */     writeC(buf, 1);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_LEGION_SEND_EMBLEM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */