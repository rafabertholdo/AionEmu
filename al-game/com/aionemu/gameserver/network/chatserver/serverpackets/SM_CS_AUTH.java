/*    */ package com.aionemu.gameserver.network.chatserver.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.configs.network.IPConfig;
/*    */ import com.aionemu.gameserver.configs.network.NetworkConfig;
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
/*    */ 
/*    */ public class SM_CS_AUTH
/*    */   extends CsServerPacket
/*    */ {
/*    */   public SM_CS_AUTH() {
/* 34 */     super(0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(ChatServerConnection con, ByteBuffer buf) {
/* 40 */     writeC(buf, getOpcode());
/* 41 */     writeC(buf, NetworkConfig.GAMESERVER_ID);
/* 42 */     writeC(buf, (IPConfig.getDefaultAddress()).length);
/* 43 */     writeB(buf, IPConfig.getDefaultAddress());
/* 44 */     writeS(buf, NetworkConfig.CHAT_PASSWORD);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\chatserver\serverpackets\SM_CS_AUTH.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */