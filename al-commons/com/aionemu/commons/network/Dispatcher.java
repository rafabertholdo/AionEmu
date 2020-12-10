/*     */ package com.aionemu.commons.network;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.nio.channels.spi.SelectorProvider;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public abstract class Dispatcher
/*     */   extends Thread
/*     */ {
/*  43 */   private static final Logger log = Logger.getLogger(Dispatcher.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Selector selector;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final DisconnectionThreadPool dcPool;
/*     */ 
/*     */ 
/*     */   
/*  57 */   private final Object gate = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dispatcher(String name, DisconnectionThreadPool dcPool) throws IOException {
/*  68 */     super(name);
/*  69 */     this.selector = SelectorProvider.provider().openSelector();
/*  70 */     this.dcPool = dcPool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void closeConnection(AConnection paramAConnection);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void dispatch() throws IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Selector selector() {
/*  95 */     return this.selector;
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
/*     */   public void run() {
/*     */     while (true) {
/*     */       try {
/* 110 */         dispatch();
/*     */         
/* 112 */         synchronized (this.gate) {
/*     */ 
/*     */         
/*     */         } 
/* 116 */       } catch (Exception e) {
/*     */         
/* 118 */         log.error("Dispatcher error! " + e, e);
/*     */       } 
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
/*     */   public final void register(SelectableChannel ch, int ops, AConnection att) throws IOException {
/* 134 */     synchronized (this.gate) {
/*     */       
/* 136 */       this.selector.wakeup();
/* 137 */       att.setKey(ch.register(this.selector, ops, att));
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
/*     */   public final SelectionKey register(SelectableChannel ch, int ops, Acceptor att) throws IOException {
/* 152 */     synchronized (this.gate) {
/*     */       
/* 154 */       this.selector.wakeup();
/* 155 */       return ch.register(this.selector, ops, att);
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
/*     */   final void accept(SelectionKey key) {
/*     */     try {
/* 168 */       ((Acceptor)key.attachment()).accept(key);
/*     */     }
/* 170 */     catch (Exception e) {
/*     */       
/* 172 */       log.error("Error while accepting connection: +" + e, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final void read(SelectionKey key) {
/*     */     int numRead;
/* 184 */     SocketChannel socketChannel = (SocketChannel)key.channel();
/* 185 */     AConnection con = (AConnection)key.attachment();
/*     */     
/* 187 */     ByteBuffer rb = con.readBuffer;
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
/*     */     try {
/* 201 */       numRead = socketChannel.read(rb);
/*     */     }
/* 203 */     catch (IOException e) {
/*     */       
/* 205 */       closeConnectionImpl(con);
/*     */       
/*     */       return;
/*     */     } 
/* 209 */     if (numRead == -1) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 214 */       closeConnectionImpl(con);
/*     */       return;
/*     */     } 
/* 217 */     if (numRead == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 222 */     rb.flip();
/* 223 */     while (rb.remaining() > 2 && rb.remaining() >= rb.getShort(rb.position())) {
/*     */ 
/*     */       
/* 226 */       if (!parse(con, rb)) {
/*     */         
/* 228 */         closeConnectionImpl(con);
/*     */         return;
/*     */       } 
/*     */     } 
/* 232 */     if (rb.hasRemaining()) {
/*     */       
/* 234 */       con.readBuffer.compact();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 245 */       rb.clear();
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
/*     */   private boolean parse(AConnection con, ByteBuffer buf) {
/* 259 */     short sz = 0;
/*     */     
/*     */     try {
/* 262 */       sz = buf.getShort();
/* 263 */       if (sz > 1)
/* 264 */         sz = (short)(sz - 2); 
/* 265 */       ByteBuffer b = (ByteBuffer)buf.slice().limit(sz);
/* 266 */       b.order(ByteOrder.LITTLE_ENDIAN);
/*     */       
/* 268 */       buf.position(buf.position() + sz);
/*     */       
/* 270 */       return con.processData(b);
/*     */     }
/* 272 */     catch (IllegalArgumentException e) {
/*     */       
/* 274 */       log.warn("Error on parsing input from client - account: " + con + " packet size: " + sz + " real size:" + buf.remaining(), e);
/*     */       
/* 276 */       return false;
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
/*     */   final void write(SelectionKey key) {
/* 288 */     SocketChannel socketChannel = (SocketChannel)key.channel();
/* 289 */     AConnection con = (AConnection)key.attachment();
/*     */ 
/*     */     
/* 292 */     ByteBuffer wb = con.writeBuffer;
/*     */     
/* 294 */     if (wb.hasRemaining()) {
/*     */       int numWrite;
/*     */       
/*     */       try {
/* 298 */         numWrite = socketChannel.write(wb);
/*     */       }
/* 300 */       catch (IOException e) {
/*     */         
/* 302 */         closeConnectionImpl(con);
/*     */         
/*     */         return;
/*     */       } 
/* 306 */       if (numWrite == 0) {
/*     */         
/* 308 */         log.info("Write " + numWrite + " ip: " + con.getIP());
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 313 */       if (wb.hasRemaining()) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     while (true) {
/*     */       int numWrite;
/* 319 */       wb.clear();
/* 320 */       boolean writeFailed = !con.writeData(wb);
/*     */       
/* 322 */       if (writeFailed) {
/*     */         
/* 324 */         wb.limit(0);
/*     */ 
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/*     */       try {
/* 331 */         numWrite = socketChannel.write(wb);
/*     */       }
/* 333 */       catch (IOException e) {
/*     */         
/* 335 */         closeConnectionImpl(con);
/*     */         
/*     */         return;
/*     */       } 
/* 339 */       if (numWrite == 0) {
/*     */         
/* 341 */         log.info("Write " + numWrite + " ip: " + con.getIP());
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 346 */       if (wb.hasRemaining()) {
/*     */         return;
/*     */       }
/*     */     } 
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
/* 361 */     key.interestOps(key.interestOps() & 0xFFFFFFFB);
/*     */ 
/*     */     
/* 364 */     if (con.isPendingClose()) {
/* 365 */       closeConnectionImpl(con);
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
/*     */   protected final void closeConnectionImpl(AConnection con) {
/* 383 */     if (con.onlyClose())
/* 384 */       this.dcPool.scheduleDisconnection(new DisconnectionTask(con), con.getDisconnectionDelay()); 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\network\Dispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */