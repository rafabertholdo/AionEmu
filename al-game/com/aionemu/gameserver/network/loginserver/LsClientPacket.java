/*    */ package com.aionemu.gameserver.network.loginserver;
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
/*    */ 
/*    */ public abstract class LsClientPacket
/*    */   extends BaseClientPacket<LoginServerConnection>
/*    */   implements Cloneable
/*    */ {
/* 32 */   private static final Logger log = Logger.getLogger(LsClientPacket.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected LsClientPacket(int opcode) {
/* 43 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final void run() {
/*    */     try {
/* 53 */       runImpl();
/*    */     }
/* 55 */     catch (Throwable e) {
/*    */       
/* 57 */       log.warn("error handling ls (" + ((LoginServerConnection)getConnection()).getIP() + ") message " + this, e);
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
/*    */   protected void sendPacket(LsServerPacket msg) {
/* 69 */     ((LoginServerConnection)getConnection()).sendPacket(msg);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LsClientPacket clonePacket() {
/*    */     try {
/* 81 */       return (LsClientPacket)clone();
/*    */     }
/* 83 */     catch (CloneNotSupportedException e) {
/*    */       
/* 85 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\loginserver\LsClientPacket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */