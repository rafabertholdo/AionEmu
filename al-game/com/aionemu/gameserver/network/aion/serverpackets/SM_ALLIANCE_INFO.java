/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.alliance.PlayerAlliance;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.List;
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
/*    */ public class SM_ALLIANCE_INFO
/*    */   extends AionServerPacket
/*    */ {
/*    */   private PlayerAlliance alliance;
/*    */   
/*    */   public SM_ALLIANCE_INFO(PlayerAlliance alliance) {
/* 36 */     this.alliance = alliance;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 41 */     writeH(buf, 4);
/* 42 */     writeD(buf, this.alliance.getObjectId());
/* 43 */     writeD(buf, this.alliance.getCaptainObjectId());
/*    */     
/* 45 */     List<Integer> ids = this.alliance.getViceCaptainObjectIds();
/* 46 */     writeD(buf, (ids.size() > 0) ? ((Integer)ids.get(0)).intValue() : 0);
/* 47 */     writeD(buf, (ids.size() > 1) ? ((Integer)ids.get(1)).intValue() : 0);
/* 48 */     writeD(buf, (ids.size() > 2) ? ((Integer)ids.get(2)).intValue() : 0);
/* 49 */     writeD(buf, (ids.size() > 3) ? ((Integer)ids.get(3)).intValue() : 0);
/*    */     
/* 51 */     writeD(buf, 0);
/* 52 */     writeD(buf, 0);
/* 53 */     writeD(buf, 0);
/* 54 */     writeD(buf, 0);
/* 55 */     writeD(buf, 0);
/* 56 */     writeD(buf, 0);
/* 57 */     writeD(buf, 0);
/* 58 */     writeD(buf, 0);
/* 59 */     writeD(buf, 0);
/* 60 */     writeC(buf, 0);
/*    */     
/* 62 */     writeD(buf, 0);
/* 63 */     writeD(buf, 1000);
/* 64 */     writeD(buf, 1);
/* 65 */     writeD(buf, 1001);
/* 66 */     writeD(buf, 2);
/* 67 */     writeD(buf, 1002);
/* 68 */     writeD(buf, 3);
/* 69 */     writeD(buf, 1003);
/*    */     
/* 71 */     writeD(buf, 0);
/* 72 */     writeH(buf, 0);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_ALLIANCE_INFO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */