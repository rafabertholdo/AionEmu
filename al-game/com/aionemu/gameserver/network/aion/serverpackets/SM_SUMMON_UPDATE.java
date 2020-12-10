package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Summon;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;






















public class SM_SUMMON_UPDATE
  extends AionServerPacket
{
  private Summon summon;
  
  public SM_SUMMON_UPDATE(Summon summon) {
    this.summon = summon;
  }


  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeC(buf, this.summon.getLevel());
    writeH(buf, this.summon.getMode().getId());
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, this.summon.getLifeStats().getCurrentHp());
    writeD(buf, this.summon.getGameStats().getCurrentStat(StatEnum.MAXHP));
    writeD(buf, this.summon.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_POWER));
    writeH(buf, this.summon.getGameStats().getCurrentStat(StatEnum.PHYSICAL_DEFENSE));
    writeH(buf, this.summon.getGameStats().getCurrentStat(StatEnum.MAGICAL_RESIST));
    writeH(buf, this.summon.getGameStats().getCurrentStat(StatEnum.ACCURACY));
    writeH(buf, this.summon.getGameStats().getCurrentStat(StatEnum.CRITICAL_RESIST));
    writeH(buf, this.summon.getGameStats().getCurrentStat(StatEnum.BOOST_MAGICAL_SKILL));
    writeH(buf, this.summon.getGameStats().getCurrentStat(StatEnum.MAGICAL_ACCURACY));
    writeH(buf, this.summon.getGameStats().getCurrentStat(StatEnum.MAGICAL_CRITICAL));
    writeH(buf, this.summon.getGameStats().getCurrentStat(StatEnum.PARRY));
    writeH(buf, this.summon.getGameStats().getCurrentStat(StatEnum.EVASION));
    writeD(buf, this.summon.getGameStats().getBaseStat(StatEnum.MAXHP));
    writeD(buf, this.summon.getGameStats().getBaseStat(StatEnum.MAIN_HAND_POWER));
    writeH(buf, this.summon.getGameStats().getBaseStat(StatEnum.PHYSICAL_DEFENSE));
    writeH(buf, this.summon.getGameStats().getBaseStat(StatEnum.MAGICAL_RESIST));
    writeH(buf, this.summon.getGameStats().getBaseStat(StatEnum.ACCURACY));
    writeH(buf, this.summon.getGameStats().getBaseStat(StatEnum.CRITICAL_RESIST));
    writeH(buf, this.summon.getGameStats().getBaseStat(StatEnum.BOOST_MAGICAL_SKILL));
    writeH(buf, this.summon.getGameStats().getBaseStat(StatEnum.MAGICAL_ACCURACY));
    writeH(buf, this.summon.getGameStats().getBaseStat(StatEnum.MAGICAL_CRITICAL));
    writeH(buf, this.summon.getGameStats().getBaseStat(StatEnum.PARRY));
    writeH(buf, this.summon.getGameStats().getBaseStat(StatEnum.EVASION));
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_SUMMON_UPDATE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
