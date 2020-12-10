/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.AbyssRank;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.utils.stats.AbyssRankEnum;
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
/*    */ public class SM_ABYSS_RANK
/*    */   extends AionServerPacket
/*    */ {
/*    */   private AbyssRank rank;
/*    */   private int currentRankId;
/*    */   
/*    */   public SM_ABYSS_RANK(AbyssRank rank) {
/* 37 */     this.rank = rank;
/* 38 */     this.currentRankId = rank.getRank().getId();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 44 */     writeQ(buf, this.rank.getAp());
/* 45 */     writeD(buf, this.currentRankId);
/* 46 */     writeD(buf, this.rank.getTopRanking());
/*    */     
/* 48 */     int nextRankId = (this.currentRankId < (AbyssRankEnum.values()).length) ? (this.currentRankId + 1) : this.currentRankId;
/* 49 */     writeD(buf, 100 * this.rank.getAp() / AbyssRankEnum.getRankById(nextRankId).getRequired());
/*    */     
/* 51 */     writeD(buf, this.rank.getAllKill());
/* 52 */     writeD(buf, this.rank.getMaxRank());
/*    */     
/* 54 */     writeD(buf, this.rank.getDailyKill());
/* 55 */     writeQ(buf, this.rank.getDailyAP());
/*    */     
/* 57 */     writeD(buf, this.rank.getWeeklyKill());
/* 58 */     writeQ(buf, this.rank.getWeeklyAP());
/*    */     
/* 60 */     writeD(buf, this.rank.getLastKill());
/* 61 */     writeQ(buf, this.rank.getLastAP());
/*    */     
/* 63 */     writeC(buf, 0);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_ABYSS_RANK.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */