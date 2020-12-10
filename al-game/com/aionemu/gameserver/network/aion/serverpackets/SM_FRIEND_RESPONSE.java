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
/*    */ public class SM_FRIEND_RESPONSE
/*    */   extends AionServerPacket
/*    */ {
/*    */   public static final int TARGET_ADDED = 0;
/*    */   public static final int TARGET_OFFLINE = 1;
/*    */   public static final int TARGET_ALREADY_FRIEND = 2;
/*    */   public static final int TARGET_NOT_FOUND = 3;
/*    */   public static final int TARGET_DENIED = 4;
/*    */   public static final int TARGET_LIST_FULL = 5;
/*    */   public static final int TARGET_REMOVED = 6;
/*    */   public static final int TARGET_BLOCKED = 8;
/*    */   public static final int TARGET_DEAD = 9;
/*    */   private final String player;
/*    */   private final int code;
/*    */   
/*    */   public SM_FRIEND_RESPONSE(String playerName, int messageType) {
/* 73 */     this.player = playerName;
/* 74 */     this.code = messageType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 83 */     writeS(buf, this.player);
/* 84 */     writeC(buf, this.code);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_FRIEND_RESPONSE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */