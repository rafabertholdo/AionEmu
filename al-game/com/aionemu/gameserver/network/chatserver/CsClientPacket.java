/*    */ package com.aionemu.gameserver.network.chatserver;
/*    */ 
/*    */ import com.aionemu.commons.network.packet.BaseClientPacket;
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
/*    */ public abstract class CsClientPacket
/*    */   extends BaseClientPacket<ChatServerConnection>
/*    */   implements Cloneable
/*    */ {
/* 31 */   private static final Logger log = Logger.getLogger(CsClientPacket.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected CsClientPacket(int opcode) {
/* 42 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final void run() {
/*    */     try {
/* 52 */       runImpl();
/*    */     }
/* 54 */     catch (Throwable e) {
/*    */       
/* 56 */       log.warn("error handling ls (" + ((ChatServerConnection)getConnection()).getIP() + ") message " + this, e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void sendPacket(CsServerPacket msg) {
/* 68 */     ((ChatServerConnection)getConnection()).sendPacket(msg);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CsClientPacket clonePacket() {
/*    */     try {
/* 80 */       return (CsClientPacket)clone();
/*    */     }
/* 82 */     catch (CloneNotSupportedException e) {
/*    */       
/* 84 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\chatserver\CsClientPacket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */