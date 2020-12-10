/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.AionObject;
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
/*    */ public class SM_PLAYER_ID
/*    */   extends AionServerPacket
/*    */ {
/*    */   private AionObject playerAionObject;
/*    */   
/*    */   public SM_PLAYER_ID(AionObject playerAionObject) {
/* 35 */     this.playerAionObject = playerAionObject;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 44 */     writeH(buf, 2);
/* 45 */     writeD(buf, 0);
/* 46 */     writeH(buf, 1);
/* 47 */     writeD(buf, this.playerAionObject.getObjectId());
/* 48 */     writeH(buf, 0);
/* 49 */     writeS(buf, this.playerAionObject.getName());
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_PLAYER_ID.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */