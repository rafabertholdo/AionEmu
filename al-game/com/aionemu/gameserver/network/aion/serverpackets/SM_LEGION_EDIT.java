/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.legion.Legion;
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
/*    */ public class SM_LEGION_EDIT
/*    */   extends AionServerPacket
/*    */ {
/*    */   private int type;
/*    */   private Legion legion;
/*    */   private int unixTime;
/*    */   private String announcement;
/*    */   
/*    */   public SM_LEGION_EDIT(int type) {
/* 39 */     this.type = type;
/*    */   }
/*    */ 
/*    */   
/*    */   public SM_LEGION_EDIT(int type, Legion legion) {
/* 44 */     this.type = type;
/* 45 */     this.legion = legion;
/*    */   }
/*    */ 
/*    */   
/*    */   public SM_LEGION_EDIT(int type, int unixTime) {
/* 50 */     this.type = type;
/* 51 */     this.unixTime = unixTime;
/*    */   }
/*    */ 
/*    */   
/*    */   public SM_LEGION_EDIT(int type, int unixTime, String announcement) {
/* 56 */     this.type = type;
/* 57 */     this.announcement = announcement;
/* 58 */     this.unixTime = unixTime;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeImpl(AionConnection con, ByteBuffer buf) {
/* 64 */     writeC(buf, this.type);
/* 65 */     switch (this.type) {
/*    */ 
/*    */       
/*    */       case 0:
/* 69 */         writeC(buf, this.legion.getLegionLevel());
/*    */         break;
/*    */       
/*    */       case 1:
/* 73 */         writeD(buf, this.legion.getLegionRank());
/*    */         break;
/*    */       
/*    */       case 2:
/* 77 */         writeC(buf, this.legion.getCenturionPermission1());
/* 78 */         writeC(buf, this.legion.getCenturionPermission2());
/* 79 */         writeC(buf, this.legion.getLegionarPermission1());
/* 80 */         writeC(buf, this.legion.getLegionarPermission2());
/*    */         break;
/*    */       
/*    */       case 3:
/* 84 */         writeD(buf, this.legion.getContributionPoints());
/*    */         break;
/*    */       
/*    */       case 5:
/* 88 */         writeS(buf, this.announcement);
/* 89 */         writeD(buf, this.unixTime);
/*    */         break;
/*    */       
/*    */       case 6:
/* 93 */         writeD(buf, this.unixTime);
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_LEGION_EDIT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */