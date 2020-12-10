/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.services.LegionService;
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
/*    */ public class CM_LEGION_UPLOAD_INFO
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int totalSize;
/*    */   
/*    */   public CM_LEGION_UPLOAD_INFO(int opcode) {
/* 37 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 43 */     this.totalSize = readD();
/* 44 */     readC();
/* 45 */     readC();
/* 46 */     readC();
/* 47 */     readC();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 53 */     LegionService.getInstance().uploadEmblemInfo(((AionConnection)getConnection()).getActivePlayer(), this.totalSize);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_LEGION_UPLOAD_INFO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */