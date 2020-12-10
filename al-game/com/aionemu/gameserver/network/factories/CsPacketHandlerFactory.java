/*    */ package com.aionemu.gameserver.network.factories;
/*    */ 
/*    */ import com.aionemu.gameserver.network.chatserver.ChatServerConnection;
/*    */ import com.aionemu.gameserver.network.chatserver.CsClientPacket;
/*    */ import com.aionemu.gameserver.network.chatserver.CsPacketHandler;
/*    */ import com.aionemu.gameserver.network.chatserver.clientpackets.CM_CS_AUTH_RESPONSE;
/*    */ import com.aionemu.gameserver.network.chatserver.clientpackets.CM_CS_PLAYER_AUTH_RESPONSE;
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
/*    */ public class CsPacketHandlerFactory
/*    */ {
/* 30 */   private CsPacketHandler handler = new CsPacketHandler();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CsPacketHandlerFactory() {
/* 37 */     addPacket((CsClientPacket)new CM_CS_AUTH_RESPONSE(0), new ChatServerConnection.State[] { ChatServerConnection.State.CONNECTED });
/* 38 */     addPacket((CsClientPacket)new CM_CS_PLAYER_AUTH_RESPONSE(1), new ChatServerConnection.State[] { ChatServerConnection.State.AUTHED });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void addPacket(CsClientPacket prototype, ChatServerConnection.State... states) {
/* 48 */     this.handler.addPacketPrototype(prototype, states);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CsPacketHandler getPacketHandler() {
/* 56 */     return this.handler;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\factories\CsPacketHandlerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */