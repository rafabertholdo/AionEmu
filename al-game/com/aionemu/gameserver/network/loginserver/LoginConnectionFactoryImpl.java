/*    */ package com.aionemu.gameserver.network.loginserver;
/*    */ 
/*    */ import com.aionemu.commons.network.AConnection;
/*    */ import com.aionemu.commons.network.ConnectionFactory;
/*    */ import com.aionemu.commons.network.Dispatcher;
/*    */ import java.io.IOException;
/*    */ import java.nio.channels.SocketChannel;
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
/*    */ public class LoginConnectionFactoryImpl
/*    */   implements ConnectionFactory
/*    */ {
/*    */   public AConnection create(SocketChannel socket, Dispatcher dispatcher) throws IOException {
/* 53 */     return new LoginServerConnection(socket, dispatcher);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\loginserver\LoginConnectionFactoryImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */