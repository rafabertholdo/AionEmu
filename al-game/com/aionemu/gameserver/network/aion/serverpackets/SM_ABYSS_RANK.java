package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.AbyssRank;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.utils.stats.AbyssRankEnum;
import java.nio.ByteBuffer;

public class SM_ABYSS_RANK extends AionServerPacket {
  private AbyssRank rank;
  private int currentRankId;

  public SM_ABYSS_RANK(AbyssRank rank) {
    this.rank = rank;
    this.currentRankId = rank.getRank().getId();
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeQ(buf, this.rank.getAp());
    writeD(buf, this.currentRankId);
    writeD(buf, this.rank.getTopRanking());

    int nextRankId = (this.currentRankId < (AbyssRankEnum.values()).length) ? (this.currentRankId + 1)
        : this.currentRankId;
    writeD(buf, 100 * this.rank.getAp() / AbyssRankEnum.getRankById(nextRankId).getRequired());

    writeD(buf, this.rank.getAllKill());
    writeD(buf, this.rank.getMaxRank());

    writeD(buf, this.rank.getDailyKill());
    writeQ(buf, this.rank.getDailyAP());

    writeD(buf, this.rank.getWeeklyKill());
    writeQ(buf, this.rank.getWeeklyAP());

    writeD(buf, this.rank.getLastKill());
    writeQ(buf, this.rank.getLastAP());

    writeC(buf, 0);
  }
}
