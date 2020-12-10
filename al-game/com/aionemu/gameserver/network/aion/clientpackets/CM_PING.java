/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PONG;
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
/*    */ public class CM_PING
/*    */   extends AionClientPacket
/*    */ {
/* 32 */   private static final Logger log = Logger.getLogger(CM_PING.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CM_PING(int opcode) {
/* 41 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 59 */     long lastMS = ((AionConnection)getConnection()).getLastPingTimeMS();
/*    */     
/* 61 */     if (lastMS > 0L) {
/*    */       
/* 63 */       long pingInterval = System.currentTimeMillis() - lastMS;
/*    */       
/* 65 */       if (pingInterval < 100000L) {
/*    */         
/* 67 */         String ip = ((AionConnection)getConnection()).getIP();
/* 68 */         String name = "[unknown]";
/* 69 */         if (((AionConnection)getConnection()).getActivePlayer() != null)
/* 70 */           name = ((AionConnection)getConnection()).getActivePlayer().getName(); 
/* 71 */         log.info("[AUDIT] possible client timer cheat: " + pingInterval + " by " + name + ", ip=" + ip);
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 76 */     ((AionConnection)getConnection()).setLastPingTimeMS(System.currentTimeMillis());
/* 77 */     sendPacket((AionServerPacket)new SM_PONG());
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_PING.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */