/*    */ package com.aionemu.gameserver.network.chatserver.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.network.chatserver.ChatServerConnection;
/*    */ import com.aionemu.gameserver.network.chatserver.CsClientPacket;
/*    */ import com.aionemu.gameserver.network.chatserver.CsServerPacket;
/*    */ import com.aionemu.gameserver.network.chatserver.serverpackets.SM_CS_AUTH;
/*    */ import com.aionemu.gameserver.services.ChatService;
/*    */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public class CM_CS_AUTH_RESPONSE
/*    */   extends CsClientPacket
/*    */ {
/* 39 */   protected static final Logger log = Logger.getLogger(CM_CS_AUTH_RESPONSE.class);
/*    */ 
/*    */ 
/*    */   
/*    */   private int response;
/*    */ 
/*    */   
/*    */   private byte[] ip;
/*    */ 
/*    */   
/*    */   private int port;
/*    */ 
/*    */ 
/*    */   
/*    */   public CM_CS_AUTH_RESPONSE(int opcode) {
/* 54 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 60 */     this.response = readC();
/* 61 */     this.ip = readB(4);
/* 62 */     this.port = readH();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 68 */     switch (this.response) {
/*    */       
/*    */       case 0:
/* 71 */         log.info("GameServer authed successfully IP : " + (this.ip[0] & 0xFF) + "." + (this.ip[1] & 0xFF) + "." + (this.ip[2] & 0xFF) + "." + (this.ip[3] & 0xFF) + " Port: " + this.port);
/* 72 */         ((ChatServerConnection)getConnection()).setState(ChatServerConnection.State.AUTHED);
/* 73 */         ChatService.setIp(this.ip);
/* 74 */         ChatService.setPort(this.port);
/*    */         break;
/*    */       case 1:
/* 77 */         log.fatal("GameServer is not authenticated at ChatServer side");
/* 78 */         System.exit(1);
/*    */         break;
/*    */       case 2:
/* 81 */         log.info("GameServer is already registered at ChatServer side! trying again...");
/* 82 */         ThreadPoolManager.getInstance().schedule(new Runnable()
/*    */             {
/*    */               public void run()
/*    */               {
/* 86 */                 ((ChatServerConnection)CM_CS_AUTH_RESPONSE.this.getConnection()).sendPacket((CsServerPacket)new SM_CS_AUTH());
/*    */               }
/*    */             },  10000L);
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\chatserver\clientpackets\CM_CS_AUTH_RESPONSE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */