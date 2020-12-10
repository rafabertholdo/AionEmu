/*     */ package com.aionemu.commons.network;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.channels.SelectionKey;
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
/*     */ public abstract class AConnection
/*     */ {
/*     */   private final SocketChannel socketChannel;
/*     */   private final Dispatcher dispatcher;
/*     */   private SelectionKey key;
/*     */   protected boolean pendingClose;
/*     */   protected boolean isForcedClosing;
/*     */   protected boolean closed;
/*  63 */   protected final Object guard = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ByteBuffer writeBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ByteBuffer readBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String ip;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean locked = false;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AConnection(SocketChannel sc, Dispatcher d) throws IOException {
/*  92 */     this.socketChannel = sc;
/*  93 */     this.dispatcher = d;
/*  94 */     this.writeBuffer = ByteBuffer.allocate(16384);
/*  95 */     this.writeBuffer.flip();
/*  96 */     this.writeBuffer.order(ByteOrder.LITTLE_ENDIAN);
/*  97 */     this.readBuffer = ByteBuffer.allocate(16384);
/*  98 */     this.readBuffer.order(ByteOrder.LITTLE_ENDIAN);
/*     */     
/* 100 */     this.dispatcher.register(this.socketChannel, 1, this);
/*     */     
/* 102 */     this.ip = this.socketChannel.socket().getInetAddress().getHostAddress();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void setKey(SelectionKey key) {
/* 112 */     this.key = key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void enableWriteInterest() {
/* 120 */     if (this.key.isValid()) {
/*     */       
/* 122 */       this.key.interestOps(this.key.interestOps() | 0x4);
/* 123 */       this.key.selector().wakeup();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final Dispatcher getDispatcher() {
/* 132 */     return this.dispatcher;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SocketChannel getSocketChannel() {
/* 140 */     return this.socketChannel;
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
/*     */   public final void close(boolean forced) {
/* 153 */     synchronized (this.guard) {
/*     */       
/* 155 */       if (isWriteDisabled()) {
/*     */         return;
/*     */       }
/* 158 */       this.isForcedClosing = forced;
/* 159 */       getDispatcher().closeConnection(this);
/*     */     } 
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
/*     */   final boolean onlyClose() {
/* 177 */     synchronized (this.guard) {
/*     */       
/* 179 */       if (this.closed) {
/* 180 */         return false;
/*     */       }
/*     */       try {
/* 183 */         if (this.socketChannel.isOpen()) {
/*     */           
/* 185 */           this.socketChannel.close();
/* 186 */           this.key.attach(null);
/* 187 */           this.key.cancel();
/*     */         } 
/* 189 */         this.closed = true;
/*     */       }
/* 191 */       catch (IOException ignored) {}
/*     */     } 
/*     */ 
/*     */     
/* 195 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final boolean isPendingClose() {
/* 203 */     return (this.pendingClose && !this.closed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean isWriteDisabled() {
/* 211 */     return (this.pendingClose || this.closed);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String getIP() {
/* 219 */     return this.ip;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean tryLockConnection() {
/* 230 */     if (this.locked)
/* 231 */       return false; 
/* 232 */     return this.locked = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void unlockConnection() {
/* 240 */     this.locked = false;
/*     */   }
/*     */   
/*     */   protected abstract boolean processData(ByteBuffer paramByteBuffer);
/*     */   
/*     */   protected abstract boolean writeData(ByteBuffer paramByteBuffer);
/*     */   
/*     */   protected abstract long getDisconnectionDelay();
/*     */   
/*     */   protected abstract void onDisconnect();
/*     */   
/*     */   protected abstract void onServerClose();
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\network\AConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */