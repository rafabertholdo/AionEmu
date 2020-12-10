/*     */ package com.aionemu.commons.network;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.ServerSocketChannel;
/*     */ import java.nio.channels.SocketChannel;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Acceptor
/*     */ {
/*     */   private final ConnectionFactory factory;
/*     */   private final NioServer nioServer;
/*     */   
/*     */   Acceptor(ConnectionFactory factory, NioServer nioServer) {
/*  75 */     this.factory = factory;
/*  76 */     this.nioServer = nioServer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void accept(SelectionKey key) throws IOException {
/* 101 */     ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
/*     */     
/* 103 */     SocketChannel socketChannel = serverSocketChannel.accept();
/* 104 */     socketChannel.configureBlocking(false);
/*     */     
/* 106 */     Dispatcher dispatcher = this.nioServer.getReadWriteDispatcher();
/* 107 */     this.factory.create(socketChannel, dispatcher);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\network\Acceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */