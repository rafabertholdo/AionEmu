/*    */ package com.aionemu.gameserver.network.chatserver;
/*    */ 
/*    */ import com.aionemu.commons.network.packet.BaseServerPacket;
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
/*    */ public abstract class CsServerPacket
/*    */   extends BaseServerPacket
/*    */ {
/*    */   protected CsServerPacket(int opcode) {
/* 36 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final void write(ChatServerConnection con, ByteBuffer buf) {
/* 47 */     buf.putShort((short)0);
/* 48 */     writeImpl(con, buf);
/* 49 */     buf.flip();
/* 50 */     buf.putShort((short)buf.limit());
/* 51 */     buf.position(0);
/*    */   }
/*    */   
/*    */   protected abstract void writeImpl(ChatServerConnection paramChatServerConnection, ByteBuffer paramByteBuffer);
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\chatserver\CsServerPacket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */