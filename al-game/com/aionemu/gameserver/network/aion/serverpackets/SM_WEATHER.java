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
/*    */ public class SM_WEATHER
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int weatherCode;
/*    */   
/*    */   public SM_WEATHER(int weatherCode) {
/* 34 */     this.weatherCode = weatherCode;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 40 */     writeH(buf, this.weatherCode);
/* 41 */     writeC(buf, 0);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_WEATHER.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */