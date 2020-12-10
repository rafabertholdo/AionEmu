/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SM_PLAYER_STATE
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int playerObjId;
/*    */   private int visualState;
/*    */   private int seeState;
/*    */   
/*    */   public SM_PLAYER_STATE(Player player) {
/* 48 */     this.playerObjId = player.getObjectId();
/* 49 */     this.visualState = player.getVisualState();
/* 50 */     this.seeState = player.getSeeState();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 59 */     writeD(buf, this.playerObjId);
/* 60 */     writeC(buf, this.visualState);
/* 61 */     writeC(buf, this.seeState);
/* 62 */     if (this.visualState == 64) {
/* 63 */       writeC(buf, 1);
/*    */     } else {
/* 65 */       writeC(buf, 0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_PLAYER_STATE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */