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
/*    */ public class SM_STATUPDATE_HP
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int currentHp;
/*    */   private int maxHp;
/*    */   
/*    */   public SM_STATUPDATE_HP(int currentHp, int maxHp) {
/* 43 */     this.currentHp = currentHp;
/* 44 */     this.maxHp = maxHp;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 53 */     writeD(buf, this.currentHp);
/* 54 */     writeD(buf, this.maxHp);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_STATUPDATE_HP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */