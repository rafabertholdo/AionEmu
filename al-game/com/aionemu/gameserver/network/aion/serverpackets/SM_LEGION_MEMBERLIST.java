package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.legion.LegionMemberEx;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class SM_LEGION_MEMBERLIST extends AionServerPacket {
  private static final int OFFLINE = 0;
  private static final int ONLINE = 1;
  private ArrayList<LegionMemberEx> legionMembers;

  public SM_LEGION_MEMBERLIST(ArrayList<LegionMemberEx> legionMembers) {
    this.legionMembers = legionMembers;
  }

  public void writeImpl(AionConnection con, ByteBuffer buf) {
    writeC(buf, 1);
    writeH(buf, 65536 - this.legionMembers.size());
    for (LegionMemberEx legionMember : this.legionMembers) {

      writeD(buf, legionMember.getObjectId());
      writeS(buf, legionMember.getName());
      writeC(buf, legionMember.getPlayerClass().getClassId());
      writeD(buf, legionMember.getLevel());
      writeC(buf, legionMember.getRank().getRankId());
      writeD(buf, legionMember.getWorldId());
      writeC(buf, legionMember.isOnline() ? 1 : 0);
      writeS(buf, legionMember.getSelfIntro());
      writeS(buf, legionMember.getNickname());
      writeD(buf, legionMember.getLastOnline());
    }
  }
}
