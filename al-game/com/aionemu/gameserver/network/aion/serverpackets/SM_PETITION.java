/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.Petition;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.services.PetitionService;
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
/*    */ public class SM_PETITION
/*    */   extends AionServerPacket
/*    */ {
/*    */   private Petition petition;
/*    */   
/*    */   public SM_PETITION() {
/* 36 */     this.petition = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public SM_PETITION(Petition petition) {
/* 41 */     this.petition = petition;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 47 */     if (this.petition == null) {
/*    */       
/* 49 */       writeD(buf, 0);
/* 50 */       writeD(buf, 0);
/* 51 */       writeD(buf, 0);
/* 52 */       writeD(buf, 0);
/* 53 */       writeH(buf, 0);
/* 54 */       writeC(buf, 0);
/*    */     }
/*    */     else {
/*    */       
/* 58 */       writeC(buf, 1);
/* 59 */       writeD(buf, 100);
/* 60 */       writeH(buf, PetitionService.getInstance().getWaitingPlayers(con.getActivePlayer().getObjectId()));
/* 61 */       writeS(buf, Integer.toString(this.petition.getPetitionId()));
/* 62 */       writeH(buf, 0);
/* 63 */       writeC(buf, 50);
/* 64 */       writeC(buf, 49);
/* 65 */       writeH(buf, PetitionService.getInstance().calculateWaitTime(this.petition.getPlayerObjId()));
/* 66 */       writeD(buf, 0);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_PETITION.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */