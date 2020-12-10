/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.legion.Legion;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.sql.Timestamp;
/*    */ import java.util.Map;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SM_LEGION_INFO
/*    */   extends AionServerPacket
/*    */ {
/*    */   private Legion legion;
/*    */   
/*    */   public SM_LEGION_INFO(Legion legion) {
/* 44 */     this.legion = legion;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void writeImpl(AionConnection con, ByteBuffer buf) {
/* 50 */     writeS(buf, this.legion.getLegionName());
/* 51 */     writeC(buf, this.legion.getLegionLevel());
/* 52 */     writeD(buf, this.legion.getLegionRank());
/* 53 */     writeC(buf, this.legion.getCenturionPermission1());
/* 54 */     writeC(buf, this.legion.getCenturionPermission2());
/* 55 */     writeC(buf, this.legion.getLegionarPermission1());
/* 56 */     writeC(buf, this.legion.getLegionarPermission2());
/* 57 */     writeD(buf, this.legion.getContributionPoints());
/* 58 */     writeD(buf, 0);
/* 59 */     writeD(buf, 0);
/* 60 */     writeD(buf, 0);
/*    */ 
/*    */     
/* 63 */     Map<Timestamp, String> announcementList = this.legion.getAnnouncementList().descendingMap();
/*    */ 
/*    */     
/* 66 */     int i = 0;
/* 67 */     for (Timestamp unixTime : announcementList.keySet()) {
/*    */       
/* 69 */       writeS(buf, announcementList.get(unixTime));
/* 70 */       writeD(buf, (int)(unixTime.getTime() / 1000L));
/* 71 */       i++;
/* 72 */       if (i >= 7) {
/*    */         break;
/*    */       }
/*    */     } 
/* 76 */     writeH(buf, 0);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_LEGION_INFO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */