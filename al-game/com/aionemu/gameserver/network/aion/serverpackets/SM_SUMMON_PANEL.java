package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Summon;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_SUMMON_PANEL extends AionServerPacket {
  private Summon summon;

  public SM_SUMMON_PANEL(Summon summon) {
    this.summon = summon;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.summon.getObjectId());
    writeH(buf, this.summon.getLevel());
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, this.summon.getLifeStats().getCurrentHp());
    writeD(buf, this.summon.getGameStats().getCurrentStat(StatEnum.MAXHP));
    writeD(buf, this.summon.getGameStats().getCurrentStat(StatEnum.MAIN_HAND_POWER));
    writeH(buf, this.summon.getGameStats().getCurrentStat(StatEnum.PHYSICAL_DEFENSE));
    writeH(buf, this.summon.getGameStats().getCurrentStat(StatEnum.MAGICAL_RESIST));
    writeD(buf, 0);
    writeH(buf, 0);
  }
}
