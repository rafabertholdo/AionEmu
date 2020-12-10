/*    */ package com.aionemu.gameserver.network.chatserver.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.network.chatserver.ChatServerConnection;
/*    */ import com.aionemu.gameserver.network.chatserver.CsServerPacket;
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
/*    */ public class SM_CS_PLAYER_AUTH
/*    */   extends CsServerPacket
/*    */ {
/*    */   private int playerId;
/*    */   private String playerLogin;
/*    */   
/*    */   public SM_CS_PLAYER_AUTH(int playerId, String playerLogin) {
/* 34 */     super(1);
/* 35 */     this.playerId = playerId;
/* 36 */     this.playerLogin = playerLogin;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(ChatServerConnection con, ByteBuffer buf) {
/* 42 */     writeC(buf, getOpcode());
/* 43 */     writeD(buf, this.playerId);
/* 44 */     writeS(buf, this.playerLogin);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\chatserver\serverpackets\SM_CS_PLAYER_AUTH.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */