/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Summon;
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
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
/*    */ public class SM_SUMMON_UPDATE
/*    */   extends AionServerPacket
/*    */ {
/*    */   private Summon summon;
/*    */   
/*    */   public SM_SUMMON_UPDATE(Summon summon) {
/* 36 */     this.summon = summon;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 42 */     writeC(buf, this.summon.getLevel());
/* 43 */     writeH(buf, this.summon.getMode().getId());
/* 44 */     writeD(buf, 0);
/* 45 */     writeD(buf, 0);
/* 46 */     writeD(buf, this.summon.getLifeStats().getCurrentHp());
/* 47 */     writeD(buf, this.summon.getGameStats().getCurrentStat(StatEnum.MAXHP));
/* 48 */     writeD(buf, this.summon.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_POWER));
/* 49 */     writeH(buf, this.summon.getGameStats().getCurrentStat(StatEnum.PHYSICAL_DEFENSE));
/* 50 */     writeH(buf, this.summon.getGameStats().getCurrentStat(StatEnum.MAGICAL_RESIST));
/* 51 */     writeH(buf, this.summon.getGameStats().getCurrentStat(StatEnum.ACCURACY));
/* 52 */     writeH(buf, this.summon.getGameStats().getCurrentStat(StatEnum.CRITICAL_RESIST));
/* 53 */     writeH(buf, this.summon.getGameStats().getCurrentStat(StatEnum.BOOST_MAGICAL_SKILL));
/* 54 */     writeH(buf, this.summon.getGameStats().getCurrentStat(StatEnum.MAGICAL_ACCURACY));
/* 55 */     writeH(buf, this.summon.getGameStats().getCurrentStat(StatEnum.MAGICAL_CRITICAL));
/* 56 */     writeH(buf, this.summon.getGameStats().getCurrentStat(StatEnum.PARRY));
/* 57 */     writeH(buf, this.summon.getGameStats().getCurrentStat(StatEnum.EVASION));
/* 58 */     writeD(buf, this.summon.getGameStats().getBaseStat(StatEnum.MAXHP));
/* 59 */     writeD(buf, this.summon.getGameStats().getBaseStat(StatEnum.MAIN_HAND_POWER));
/* 60 */     writeH(buf, this.summon.getGameStats().getBaseStat(StatEnum.PHYSICAL_DEFENSE));
/* 61 */     writeH(buf, this.summon.getGameStats().getBaseStat(StatEnum.MAGICAL_RESIST));
/* 62 */     writeH(buf, this.summon.getGameStats().getBaseStat(StatEnum.ACCURACY));
/* 63 */     writeH(buf, this.summon.getGameStats().getBaseStat(StatEnum.CRITICAL_RESIST));
/* 64 */     writeH(buf, this.summon.getGameStats().getBaseStat(StatEnum.BOOST_MAGICAL_SKILL));
/* 65 */     writeH(buf, this.summon.getGameStats().getBaseStat(StatEnum.MAGICAL_ACCURACY));
/* 66 */     writeH(buf, this.summon.getGameStats().getBaseStat(StatEnum.MAGICAL_CRITICAL));
/* 67 */     writeH(buf, this.summon.getGameStats().getBaseStat(StatEnum.PARRY));
/* 68 */     writeH(buf, this.summon.getGameStats().getBaseStat(StatEnum.EVASION));
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_SUMMON_UPDATE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */