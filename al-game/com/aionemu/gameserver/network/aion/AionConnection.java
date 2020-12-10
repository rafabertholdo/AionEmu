/*     */ package com.aionemu.gameserver.network.aion;
/*     */ 
/*     */ import com.aionemu.commons.network.AConnection;
/*     */ import com.aionemu.commons.network.Dispatcher;
/*     */ import com.aionemu.commons.utils.concurrent.RunnableStatsManager;
/*     */ import com.aionemu.gameserver.model.account.Account;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.Crypt;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_KEY;
/*     */ import com.aionemu.gameserver.network.factories.AionPacketHandlerFactory;
/*     */ import com.aionemu.gameserver.network.loginserver.LoginServer;
/*     */ import com.aionemu.gameserver.services.PlayerService;
/*     */ import com.aionemu.gameserver.taskmanager.FIFORunnableQueue;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import javolution.util.FastList;
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
/*     */ public class AionConnection
/*     */   extends AConnection
/*     */ {
/*  49 */   private static final Logger log = Logger.getLogger(AionConnection.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum State
/*     */   {
/*  59 */     CONNECTED,
/*     */ 
/*     */ 
/*     */     
/*  63 */     AUTHED,
/*     */ 
/*     */ 
/*     */     
/*  67 */     IN_GAME;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   private final FastList<AionServerPacket> sendMsgQueue = new FastList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private State state;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Account account;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  88 */   private final Crypt crypt = new Crypt();
/*     */ 
/*     */   
/*     */   private Player activePlayer;
/*     */ 
/*     */   
/*  94 */   private String lastPlayerName = "";
/*     */ 
/*     */ 
/*     */   
/*     */   private AionPacketHandler aionPacketHandler;
/*     */ 
/*     */   
/*     */   private long lastPingTimeMS;
/*     */ 
/*     */   
/*     */   private FIFORunnableQueue<Runnable> packetQueue;
/*     */ 
/*     */ 
/*     */   
/*     */   public AionConnection(SocketChannel sc, Dispatcher d) throws IOException {
/* 109 */     super(sc, d);
/*     */     
/* 111 */     AionPacketHandlerFactory aionPacketHandlerFactory = AionPacketHandlerFactory.getInstance();
/* 112 */     this.aionPacketHandler = aionPacketHandlerFactory.getPacketHandler();
/*     */     
/* 114 */     this.state = State.CONNECTED;
/*     */     
/* 116 */     String ip = getIP();
/* 117 */     log.info("connection from: " + ip);
/*     */ 
/*     */     
/* 120 */     sendPacket((AionServerPacket)new SM_KEY());
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
/*     */   public final int enableCryptKey() {
/* 132 */     return this.crypt.enableKey();
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
/*     */   protected final boolean processData(ByteBuffer data) {
/*     */     try {
/* 146 */       if (!this.crypt.decrypt(data))
/*     */       {
/* 148 */         log.warn("Decrypt fail!");
/* 149 */         return false;
/*     */       }
/*     */     
/* 152 */     } catch (Exception ex) {
/*     */       
/* 154 */       log.error("Exception caught during decrypt!" + ex.getMessage());
/* 155 */       return false;
/*     */     } 
/*     */     
/* 158 */     AionClientPacket pck = this.aionPacketHandler.handle(data, this);
/*     */     
/* 160 */     if (this.state == State.IN_GAME && this.activePlayer == null) {
/*     */       
/* 162 */       log.warn("CHECKPOINT: Skipping packet processing of " + pck.getPacketName() + " for player " + this.lastPlayerName);
/* 163 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 169 */     if (pck != null && pck.read()) {
/* 170 */       getPacketQueue().execute(pck);
/*     */     }
/* 172 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FIFORunnableQueue<Runnable> getPacketQueue() {
/* 179 */     if (this.packetQueue == null)
/* 180 */       this.packetQueue = new FIFORunnableQueue<Runnable>() {  }
/*     */         ; 
/* 182 */     return this.packetQueue;
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
/*     */   protected final boolean writeData(ByteBuffer data) {
/* 194 */     synchronized (this.guard) {
/*     */       
/* 196 */       long begin = System.nanoTime();
/* 197 */       if (this.sendMsgQueue.isEmpty())
/* 198 */         return false; 
/* 199 */       AionServerPacket packet = (AionServerPacket)this.sendMsgQueue.removeFirst();
/*     */       
/*     */       try {
/* 202 */         packet.write(this, data);
/* 203 */         return true;
/*     */       }
/*     */       finally {
/*     */         
/* 207 */         RunnableStatsManager.handleStats(packet.getClass(), "runImpl()", System.nanoTime() - begin);
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
/*     */   protected final long getDisconnectionDelay() {
/* 221 */     return 0L;
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
/*     */   protected final void onDisconnect() {
/* 233 */     if (getAccount() != null)
/* 234 */       LoginServer.getInstance().aionClientDisconnected(getAccount().getId()); 
/* 235 */     if (getActivePlayer() != null) {
/*     */       
/* 237 */       Player player = getActivePlayer();
/*     */       
/* 239 */       if (player.getController().isInShutdownProgress()) {
/* 240 */         PlayerService.playerLoggedOut(player);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 245 */         int delay = 15;
/* 246 */         PlayerService.playerLoggedOutDelay(player, delay * 1000);
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
/*     */   protected final void onServerClose() {
/* 258 */     close(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void encrypt(ByteBuffer buf) {
/* 268 */     this.crypt.encrypt(buf);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sendPacket(AionServerPacket bp) {
/* 279 */     synchronized (this.guard) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 284 */       if (isWriteDisabled()) {
/*     */         return;
/*     */       }
/* 287 */       this.sendMsgQueue.addLast(bp);
/* 288 */       enableWriteInterest();
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
/*     */   public final void close(AionServerPacket closePacket, boolean forced) {
/* 304 */     synchronized (this.guard) {
/*     */       
/* 306 */       if (isWriteDisabled()) {
/*     */         return;
/*     */       }
/* 309 */       log.debug("sending packet: " + closePacket + " and closing connection after that.");
/*     */       
/* 311 */       this.pendingClose = true;
/* 312 */       this.isForcedClosing = forced;
/* 313 */       this.sendMsgQueue.clear();
/* 314 */       this.sendMsgQueue.addLast(closePacket);
/* 315 */       enableWriteInterest();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final State getState() {
/* 326 */     return this.state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setState(State state) {
/* 337 */     this.state = state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Account getAccount() {
/* 347 */     return this.account;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAccount(Account account) {
/* 358 */     this.account = account;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setActivePlayer(Player player) {
/* 369 */     if (this.activePlayer != null && player != null)
/* 370 */       return false; 
/* 371 */     this.activePlayer = player;
/*     */     
/* 373 */     if (this.activePlayer == null) {
/* 374 */       this.state = State.AUTHED;
/*     */     } else {
/* 376 */       this.state = State.IN_GAME;
/*     */     } 
/* 378 */     if (this.activePlayer != null) {
/* 379 */       this.lastPlayerName = player.getName();
/*     */     }
/* 381 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getActivePlayer() {
/* 391 */     return this.activePlayer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLastPingTimeMS() {
/* 399 */     return this.lastPingTimeMS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLastPingTimeMS(long lastPingTimeMS) {
/* 407 */     this.lastPingTimeMS = lastPingTimeMS;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\AionConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */