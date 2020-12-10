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
/*    */ public class SM_SUMMON_PANEL
/*    */   extends AionServerPacket
/*    */ {
/*    */   private Summon summon;
/*    */   
/*    */   public SM_SUMMON_PANEL(Summon summon) {
/* 36 */     this.summon = summon;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 42 */     writeD(buf, this.summon.getObjectId());
/* 43 */     writeH(buf, this.summon.getLevel());
/* 44 */     writeD(buf, 0);
/* 45 */     writeD(buf, 0);
/* 46 */     writeD(buf, this.summon.getLifeStats().getCurrentHp());
/* 47 */     writeD(buf, this.summon.getGameStats().getCurrentStat(StatEnum.MAXHP));
/* 48 */     writeD(buf, this.summon.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_POWER));
/* 49 */     writeH(buf, this.summon.getGameStats().getCurrentStat(StatEnum.PHYSICAL_DEFENSE));
/* 50 */     writeH(buf, this.summon.getGameStats().getCurrentStat(StatEnum.MAGICAL_RESIST));
/* 51 */     writeD(buf, 0);
/* 52 */     writeH(buf, 0);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_SUMMON_PANEL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */