/*     */ package com.aionemu.gameserver.network.loginserver;
/*     */ 
/*     */ import com.aionemu.commons.network.AConnection;
/*     */ import com.aionemu.commons.network.Dispatcher;
/*     */ import com.aionemu.gameserver.network.factories.LsPacketHandlerFactory;
/*     */ import com.aionemu.gameserver.network.loginserver.serverpackets.SM_GS_AUTH;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
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
/*     */ public class LoginServerConnection
/*     */   extends AConnection
/*     */ {
/*  44 */   private static final Logger log = Logger.getLogger(LoginServerConnection.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum State
/*     */   {
/*  54 */     CONNECTED,
/*     */ 
/*     */ 
/*     */     
/*  58 */     AUTHED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private final Deque<LsServerPacket> sendMsgQueue = new ArrayDeque<LsServerPacket>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private State state;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private LsPacketHandler lsPacketHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoginServerConnection(SocketChannel sc, Dispatcher d) throws IOException {
/*  82 */     super(sc, d);
/*  83 */     LsPacketHandlerFactory lsPacketHandlerFactory = LsPacketHandlerFactory.getInstance();
/*  84 */     this.lsPacketHandler = lsPacketHandlerFactory.getPacketHandler();
/*     */     
/*  86 */     this.state = State.CONNECTED;
/*  87 */     log.info("Connected to LoginServer!");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     sendPacket((LsServerPacket)new SM_GS_AUTH());
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
/*     */   public boolean processData(ByteBuffer data) {
/* 104 */     LsClientPacket pck = this.lsPacketHandler.handle(data, this);
/* 105 */     log.info("recived packet: " + pck);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     if (pck != null && pck.read()) {
/* 111 */       ThreadPoolManager.getInstance().executeLsPacket((Runnable)pck);
/*     */     }
/* 113 */     return true;
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
/* 125 */     synchronized (this.guard) {
/*     */       
/* 127 */       LsServerPacket packet = this.sendMsgQueue.pollFirst();
/* 128 */       if (packet == null) {
/* 129 */         return false;
/*     */       }
/* 131 */       packet.write(this, data);
/* 132 */       return true;
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
/*     */   protected final long getDisconnectionDelay() {
/* 144 */     return 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void onDisconnect() {
/* 153 */     LoginServer.getInstance().loginServerDown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void onServerClose() {
/* 163 */     close(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sendPacket(LsServerPacket bp) {
/* 174 */     synchronized (this.guard) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 179 */       if (isWriteDisabled()) {
/*     */         return;
/*     */       }
/* 182 */       log.info("sending packet: " + bp);
/*     */       
/* 184 */       this.sendMsgQueue.addLast(bp);
/* 185 */       enableWriteInterest();
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
/*     */   public final void close(LsServerPacket closePacket, boolean forced) {
/* 201 */     synchronized (this.guard) {
/*     */       
/* 203 */       if (isWriteDisabled()) {
/*     */         return;
/*     */       }
/* 206 */       log.info("sending packet: " + closePacket + " and closing connection after that.");
/*     */       
/* 208 */       this.pendingClose = true;
/* 209 */       this.isForcedClosing = forced;
/* 210 */       this.sendMsgQueue.clear();
/* 211 */       this.sendMsgQueue.addLast(closePacket);
/* 212 */       enableWriteInterest();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public State getState() {
/* 221 */     return this.state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setState(State state) {
/* 230 */     this.state = state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 239 */     return "LoginServer " + getIP();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\loginserver\LoginServerConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */