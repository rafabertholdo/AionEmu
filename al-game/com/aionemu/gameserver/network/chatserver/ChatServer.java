/*     */ package com.aionemu.gameserver.network.chatserver;
/*     */ 
/*     */ import com.aionemu.commons.network.Dispatcher;
/*     */ import com.aionemu.commons.network.NioServer;
/*     */ import com.aionemu.gameserver.configs.network.NetworkConfig;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.chatserver.serverpackets.SM_CS_PLAYER_AUTH;
/*     */ import com.aionemu.gameserver.network.chatserver.serverpackets.SM_CS_PLAYER_LOGOUT;
/*     */ import com.aionemu.gameserver.network.factories.CsPacketHandlerFactory;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import java.nio.channels.SocketChannel;
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
/*     */ public class ChatServer
/*     */ {
/*  37 */   private static final Logger log = Logger.getLogger(ChatServer.class);
/*     */   
/*     */   private ChatServerConnection chatServer;
/*     */   
/*     */   private NioServer nioServer;
/*     */   
/*     */   private boolean serverShutdown = false;
/*     */   
/*     */   public static final ChatServer getInstance() {
/*  46 */     return SingletonHolder.instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNioServer(NioServer nioServer) {
/*  55 */     this.nioServer = nioServer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatServerConnection connect() {
/*     */     while (true) {
/*  66 */       this.chatServer = null;
/*  67 */       log.info("Connecting to ChatServer: " + NetworkConfig.CHAT_ADDRESS);
/*     */       
/*     */       try {
/*  70 */         SocketChannel sc = SocketChannel.open(NetworkConfig.CHAT_ADDRESS);
/*  71 */         sc.configureBlocking(false);
/*  72 */         Dispatcher d = this.nioServer.getReadWriteDispatcher();
/*  73 */         CsPacketHandlerFactory csPacketHandlerFactory = new CsPacketHandlerFactory();
/*  74 */         this.chatServer = new ChatServerConnection(sc, d, csPacketHandlerFactory.getPacketHandler());
/*  75 */         return this.chatServer;
/*     */       }
/*  77 */       catch (Exception e) {
/*     */         
/*  79 */         log.info("Cant connect to ChatServer: " + e.getMessage());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/*  86 */           Thread.sleep(10000L);
/*     */         }
/*  88 */         catch (Exception exception) {}
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void chatServerDown() {
/*  99 */     log.warn("Connection with ChatServer lost...");
/*     */     
/* 101 */     this.chatServer = null;
/*     */     
/* 103 */     if (!this.serverShutdown)
/*     */     {
/* 105 */       ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/* 109 */               ChatServer.this.connect();
/*     */             }
/*     */           },  5000L);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPlayerLoginRequst(Player player) {
/* 121 */     if (this.chatServer != null) {
/* 122 */       this.chatServer.sendPacket((CsServerPacket)new SM_CS_PLAYER_AUTH(player.getObjectId(), player.getAcountName()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPlayerLogout(Player player) {
/* 131 */     if (this.chatServer != null)
/* 132 */       this.chatServer.sendPacket((CsServerPacket)new SM_CS_PLAYER_LOGOUT(player.getObjectId())); 
/*     */   }
/*     */   
/*     */   private ChatServer() {}
/*     */   
/*     */   private static class SingletonHolder {
/* 138 */     protected static final ChatServer instance = new ChatServer();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\chatserver\ChatServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */