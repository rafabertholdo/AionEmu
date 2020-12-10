/*     */ package com.aionemu.gameserver.network.chatserver;
/*     */ 
/*     */ import com.aionemu.commons.network.AConnection;
/*     */ import com.aionemu.commons.network.Dispatcher;
/*     */ import com.aionemu.gameserver.network.chatserver.serverpackets.SM_CS_AUTH;
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
/*     */ public class ChatServerConnection
/*     */   extends AConnection
/*     */ {
/*  37 */   private static final Logger log = Logger.getLogger(ChatServerConnection.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum State
/*     */   {
/*  47 */     CONNECTED,
/*     */ 
/*     */ 
/*     */     
/*  51 */     AUTHED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  57 */   private final Deque<CsServerPacket> sendMsgQueue = new ArrayDeque<CsServerPacket>();
/*     */ 
/*     */ 
/*     */   
/*     */   private State state;
/*     */ 
/*     */ 
/*     */   
/*     */   private ChatServer chatServer;
/*     */ 
/*     */ 
/*     */   
/*     */   private CsPacketHandler csPacketHandler;
/*     */ 
/*     */ 
/*     */   
/*     */   public ChatServerConnection(SocketChannel sc, Dispatcher d, CsPacketHandler csPacketHandler) throws IOException {
/*  74 */     super(sc, d);
/*  75 */     this.chatServer = ChatServer.getInstance();
/*  76 */     this.csPacketHandler = csPacketHandler;
/*     */     
/*  78 */     this.state = State.CONNECTED;
/*  79 */     log.info("Connected to ChatServer!");
/*     */     
/*  81 */     sendPacket((CsServerPacket)new SM_CS_AUTH());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processData(ByteBuffer data) {
/*  87 */     CsClientPacket pck = this.csPacketHandler.handle(data, this);
/*  88 */     log.info("recived packet: " + pck);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  93 */     if (pck != null && pck.read()) {
/*  94 */       ThreadPoolManager.getInstance().executeLsPacket((Runnable)pck);
/*     */     }
/*  96 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final boolean writeData(ByteBuffer data) {
/* 102 */     synchronized (this.guard) {
/*     */       
/* 104 */       CsServerPacket packet = this.sendMsgQueue.pollFirst();
/* 105 */       if (packet == null) {
/* 106 */         return false;
/*     */       }
/* 108 */       packet.write(this, data);
/* 109 */       return true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final long getDisconnectionDelay() {
/* 116 */     return 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void onDisconnect() {
/* 122 */     this.chatServer.chatServerDown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void onServerClose() {
/* 129 */     close(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sendPacket(CsServerPacket bp) {
/* 137 */     synchronized (this.guard) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 142 */       if (isWriteDisabled()) {
/*     */         return;
/*     */       }
/* 145 */       log.info("sending packet: " + bp);
/*     */       
/* 147 */       this.sendMsgQueue.addLast(bp);
/* 148 */       enableWriteInterest();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void close(CsServerPacket closePacket, boolean forced) {
/* 159 */     synchronized (this.guard) {
/*     */       
/* 161 */       if (isWriteDisabled()) {
/*     */         return;
/*     */       }
/* 164 */       log.info("sending packet: " + closePacket + " and closing connection after that.");
/*     */       
/* 166 */       this.pendingClose = true;
/* 167 */       this.isForcedClosing = forced;
/* 168 */       this.sendMsgQueue.clear();
/* 169 */       this.sendMsgQueue.addLast(closePacket);
/* 170 */       enableWriteInterest();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public State getState() {
/* 179 */     return this.state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setState(State state) {
/* 187 */     this.state = state;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 193 */     return "ChatServer " + getIP();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\chatserver\ChatServerConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */