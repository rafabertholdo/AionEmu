/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.DuelResult;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import java.nio.ByteBuffer;
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
/*    */ public class SM_DUEL
/*    */   extends AionServerPacket
/*    */ {
/*    */   private String playerName;
/*    */   private DuelResult result;
/*    */   private int requesterObjId;
/*    */   private int type;
/*    */   
/*    */   private SM_DUEL(int type) {
/* 39 */     this.type = type;
/*    */   }
/*    */ 
/*    */   
/*    */   public static SM_DUEL SM_DUEL_STARTED(int requesterObjId) {
/* 44 */     SM_DUEL packet = new SM_DUEL(0);
/* 45 */     packet.setRequesterObjId(requesterObjId);
/* 46 */     return packet;
/*    */   }
/*    */   
/*    */   private void setRequesterObjId(int requesterObjId) {
/* 50 */     this.requesterObjId = requesterObjId;
/*    */   }
/*    */ 
/*    */   
/*    */   public static SM_DUEL SM_DUEL_RESULT(DuelResult result, String playerName) {
/* 55 */     SM_DUEL packet = new SM_DUEL(1);
/* 56 */     packet.setPlayerName(playerName);
/* 57 */     packet.setResult(result);
/* 58 */     return packet;
/*    */   }
/*    */   
/*    */   private void setPlayerName(String playerName) {
/* 62 */     this.playerName = playerName;
/*    */   }
/*    */   
/*    */   private void setResult(DuelResult result) {
/* 66 */     this.result = result;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeImpl(AionConnection con, ByteBuffer buf) {
/* 72 */     writeC(buf, this.type);
/*    */     
/* 74 */     switch (this.type) {
/*    */       
/*    */       case 0:
/* 77 */         writeD(buf, this.requesterObjId);
/*    */       
/*    */       case 1:
/* 80 */         writeC(buf, this.result.getResultId());
/* 81 */         writeD(buf, this.result.getMsgId());
/* 82 */         writeS(buf, this.playerName);
/*    */       
/*    */       case 224:
/*    */         return;
/*    */     } 
/* 87 */     throw new IllegalArgumentException("invalid SM_DUEL packet type " + this.type);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_DUEL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */