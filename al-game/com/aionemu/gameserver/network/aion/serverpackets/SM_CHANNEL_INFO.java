/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.world.WorldPosition;
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
/*    */ public class SM_CHANNEL_INFO
/*    */   extends AionServerPacket
/*    */ {
/* 32 */   int instanceCount = 0;
/* 33 */   int currentChannel = 0;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SM_CHANNEL_INFO(WorldPosition position) {
/* 39 */     this.instanceCount = position.getInstanceCount();
/* 40 */     this.currentChannel = position.getInstanceId() - 1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 50 */     writeD(buf, this.currentChannel);
/* 51 */     writeD(buf, this.instanceCount);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_CHANNEL_INFO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */