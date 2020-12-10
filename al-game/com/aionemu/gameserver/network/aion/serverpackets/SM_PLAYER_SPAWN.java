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
/*    */ public class SM_PLAYER_SPAWN
/*    */   extends AionServerPacket
/*    */ {
/*    */   private final Player player;
/*    */   
/*    */   public SM_PLAYER_SPAWN(Player player) {
/* 46 */     this.player = player;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 55 */     writeD(buf, this.player.getWorldId());
/* 56 */     writeD(buf, this.player.getWorldId());
/* 57 */     writeD(buf, 0);
/* 58 */     writeC(buf, 0);
/* 59 */     writeF(buf, this.player.getX());
/* 60 */     writeF(buf, this.player.getY());
/* 61 */     writeF(buf, this.player.getZ());
/* 62 */     writeC(buf, this.player.getHeading());
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_PLAYER_SPAWN.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */