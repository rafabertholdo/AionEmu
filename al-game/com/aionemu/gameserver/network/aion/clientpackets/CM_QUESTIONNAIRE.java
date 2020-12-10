/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
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
/*    */ public class CM_QUESTIONNAIRE
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int objectId;
/*    */   
/*    */   public CM_QUESTIONNAIRE(int opcode) {
/* 31 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 40 */     this.objectId = readD();
/* 41 */     readH();
/* 42 */     readH();
/* 43 */     readH();
/* 44 */     readH();
/*    */   }
/*    */   
/*    */   protected void runImpl() {}
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_QUESTIONNAIRE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */