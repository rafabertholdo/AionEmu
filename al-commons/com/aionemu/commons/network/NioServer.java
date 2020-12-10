/*     */ package com.aionemu.commons.network;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.ServerSocketChannel;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class NioServer
/*     */ {
/*  40 */   private static final Logger log = Logger.getLogger(NioServer.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   private final List<SelectionKey> serverChannelKeys = new ArrayList<SelectionKey>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Dispatcher acceptDispatcher;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int currentReadWriteDispatcher;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Dispatcher[] readWriteDispatchers;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final DisconnectionThreadPool dcPool;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int readWriteThreads;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ServerCfg[] cfgs;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioServer(int readWriteThreads, DisconnectionThreadPool dcPool, ServerCfg... cfgs) {
/*  98 */     this.dcPool = dcPool;
/*  99 */     this.readWriteThreads = readWriteThreads;
/* 100 */     this.cfgs = cfgs;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect() {
/*     */     try {
/* 107 */       initDispatchers(this.readWriteThreads, this.dcPool);
/*     */ 
/*     */       
/* 110 */       for (ServerCfg cfg : this.cfgs) {
/*     */         InetSocketAddress isa;
/* 112 */         ServerSocketChannel serverChannel = ServerSocketChannel.open();
/* 113 */         serverChannel.configureBlocking(false);
/*     */ 
/*     */ 
/*     */         
/* 117 */         if ("*".equals(cfg.hostName)) {
/*     */           
/* 119 */           isa = new InetSocketAddress(cfg.port);
/* 120 */           log.info("Server listening on all available IPs on Port " + cfg.port + " for " + cfg.connectionName);
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */           
/* 126 */           isa = new InetSocketAddress(cfg.hostName, cfg.port);
/* 127 */           log.info("Server listening on IP: " + cfg.hostName + " Port " + cfg.port + " for " + cfg.connectionName);
/*     */         } 
/*     */         
/* 130 */         serverChannel.socket().bind(isa);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 135 */         SelectionKey acceptKey = getAcceptDispatcher().register(serverChannel, 16, new Acceptor(cfg.factory, this));
/*     */         
/* 137 */         this.serverChannelKeys.add(acceptKey);
/*     */       }
/*     */     
/* 140 */     } catch (Exception e) {
/*     */       
/* 142 */       log.fatal("NioServer Initialization Error: " + e, e);
/* 143 */       throw new Error("NioServer Initialization Error!");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Dispatcher getAcceptDispatcher() {
/* 152 */     return this.acceptDispatcher;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Dispatcher getReadWriteDispatcher() {
/* 160 */     if (this.readWriteDispatchers == null) {
/* 161 */       return this.acceptDispatcher;
/*     */     }
/* 163 */     if (this.readWriteDispatchers.length == 1) {
/* 164 */       return this.readWriteDispatchers[0];
/*     */     }
/* 166 */     if (this.currentReadWriteDispatcher >= this.readWriteDispatchers.length)
/* 167 */       this.currentReadWriteDispatcher = 0; 
/* 168 */     return this.readWriteDispatchers[this.currentReadWriteDispatcher++];
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
/*     */   private void initDispatchers(int readWriteThreads, DisconnectionThreadPool dcPool) throws IOException {
/* 180 */     if (readWriteThreads <= 1) {
/*     */       
/* 182 */       this.acceptDispatcher = new AcceptReadWriteDispatcherImpl("AcceptReadWrite Dispatcher", dcPool);
/* 183 */       this.acceptDispatcher.start();
/*     */     }
/*     */     else {
/*     */       
/* 187 */       this.acceptDispatcher = new AcceptDispatcherImpl("Accept Dispatcher");
/* 188 */       this.acceptDispatcher.start();
/*     */       
/* 190 */       this.readWriteDispatchers = new Dispatcher[readWriteThreads];
/* 191 */       for (int i = 0; i < this.readWriteDispatchers.length; i++) {
/*     */         
/* 193 */         this.readWriteDispatchers[i] = new AcceptReadWriteDispatcherImpl("ReadWrite-" + i + " Dispatcher", dcPool);
/* 194 */         this.readWriteDispatchers[i].start();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getActiveConnections() {
/* 204 */     int count = 0;
/* 205 */     if (this.readWriteDispatchers != null) {
/*     */       
/* 207 */       for (Dispatcher d : this.readWriteDispatchers) {
/* 208 */         count += d.selector().keys().size();
/*     */       }
/*     */     } else {
/*     */       
/* 212 */       count = this.acceptDispatcher.selector().keys().size() - this.serverChannelKeys.size();
/*     */     } 
/* 214 */     return count;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void shutdown() {
/* 222 */     log.info("Closing ServerChannels...");
/*     */     
/*     */     try {
/* 225 */       for (SelectionKey key : this.serverChannelKeys)
/* 226 */         key.cancel(); 
/* 227 */       log.info("ServerChannel closed.");
/*     */     }
/* 229 */     catch (Exception e) {
/*     */       
/* 231 */       log.error("Error during closing ServerChannel, " + e, e);
/*     */     } 
/*     */     
/* 234 */     notifyServerClose();
/*     */ 
/*     */     
/*     */     try {
/* 238 */       Thread.sleep(1000L);
/*     */     }
/* 240 */     catch (Throwable t) {
/*     */       
/* 242 */       log.warn("Nio thread was interrupted during shutdown", t);
/*     */     } 
/*     */     
/* 245 */     log.info(" Active connections: " + getActiveConnections());
/*     */ 
/*     */     
/* 248 */     log.info("Forced Disconnecting all connections...");
/* 249 */     closeAll();
/* 250 */     log.info(" Active connections: " + getActiveConnections());
/*     */     
/* 252 */     this.dcPool.waitForDisconnectionTasks();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 257 */       Thread.sleep(1000L);
/*     */     }
/* 259 */     catch (Throwable t) {
/*     */       
/* 261 */       log.warn("Nio thread was interrupted during shutdown", t);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void notifyServerClose() {
/* 270 */     if (this.readWriteDispatchers != null) {
/*     */       
/* 272 */       for (Dispatcher d : this.readWriteDispatchers) {
/* 273 */         for (SelectionKey key : d.selector().keys()) {
/*     */           
/* 275 */           if (key.attachment() instanceof AConnection)
/*     */           {
/* 277 */             ((AConnection)key.attachment()).onServerClose();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 283 */       for (SelectionKey key : this.acceptDispatcher.selector().keys()) {
/*     */         
/* 285 */         if (key.attachment() instanceof AConnection)
/*     */         {
/* 287 */           ((AConnection)key.attachment()).onServerClose();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void closeAll() {
/* 298 */     if (this.readWriteDispatchers != null) {
/*     */       
/* 300 */       for (Dispatcher d : this.readWriteDispatchers) {
/* 301 */         for (SelectionKey key : d.selector().keys()) {
/*     */           
/* 303 */           if (key.attachment() instanceof AConnection)
/*     */           {
/* 305 */             ((AConnection)key.attachment()).close(true);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 311 */       for (SelectionKey key : this.acceptDispatcher.selector().keys()) {
/*     */         
/* 313 */         if (key.attachment() instanceof AConnection)
/*     */         {
/* 315 */           ((AConnection)key.attachment()).close(true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\network\NioServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */