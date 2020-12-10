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
/*    */ public class SM_FRIEND_NOTIFY
/*    */   extends AionServerPacket
/*    */ {
/*    */   public static final int LOGIN = 0;
/*    */   public static final int LOGOUT = 1;
/*    */   public static final int DELETED = 2;
/*    */   private final int code;
/*    */   private final String name;
/*    */   
/*    */   public SM_FRIEND_NOTIFY(int code, String name) {
/* 57 */     this.code = code;
/* 58 */     this.name = name;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 64 */     writeS(buf, this.name);
/* 65 */     writeC(buf, this.code);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_FRIEND_NOTIFY.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */