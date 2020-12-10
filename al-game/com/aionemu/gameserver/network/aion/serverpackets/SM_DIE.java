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
/*    */ public class SM_DIE
/*    */   extends AionServerPacket
/*    */ {
/*    */   private boolean hasRebirth;
/*    */   private boolean hasItem;
/*    */   private int remainingKiskTime;
/*    */   
/*    */   public SM_DIE(boolean hasRebirth, boolean hasItem, int remainingKiskTime) {
/* 37 */     this.hasRebirth = hasRebirth;
/* 38 */     this.hasItem = hasItem;
/* 39 */     this.remainingKiskTime = remainingKiskTime;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 45 */     writeC(buf, this.hasRebirth ? 1 : 0);
/* 46 */     writeC(buf, this.hasItem ? 1 : 0);
/* 47 */     writeD(buf, this.remainingKiskTime);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_DIE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */