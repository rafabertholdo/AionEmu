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
/*    */ 
/*    */ 
/*    */ public class SM_STATUPDATE_MP
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int currentMp;
/*    */   private int maxMp;
/*    */   
/*    */   public SM_STATUPDATE_MP(int currentMp, int maxMp) {
/* 43 */     this.currentMp = currentMp;
/* 44 */     this.maxMp = maxMp;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 53 */     writeD(buf, this.currentMp);
/* 54 */     writeD(buf, this.maxMp);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_STATUPDATE_MP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */