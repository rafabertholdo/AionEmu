/*    */ package com.aionemu.gameserver.network.aion.clientpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionClientPacket;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
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
/*    */ public class CM_QUESTION_RESPONSE
/*    */   extends AionClientPacket
/*    */ {
/*    */   private int questionid;
/*    */   private int response;
/*    */   private int senderid;
/*    */   
/*    */   public CM_QUESTION_RESPONSE(int opcode) {
/* 36 */     super(opcode);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void readImpl() {
/* 45 */     this.questionid = readD();
/*    */     
/* 47 */     this.response = readC();
/* 48 */     readC();
/* 49 */     readH();
/* 50 */     this.senderid = readD();
/* 51 */     readD();
/* 52 */     readH();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void runImpl() {
/* 61 */     Player player = ((AionConnection)getConnection()).getActivePlayer();
/* 62 */     player.getResponseRequester().respond(this.questionid, this.response);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_QUESTION_RESPONSE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */