package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_LEGION_ADD_MEMBER extends AionServerPacket {
  private Player player;
  private boolean isMember;
  private int msgId;
  private String text;

  public SM_LEGION_ADD_MEMBER(Player player, boolean isMember, int msgId, String text) {
    this.player = player;
    this.isMember = isMember;
    this.msgId = msgId;
    this.text = text;
  }

  public void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.player.getObjectId());
    writeS(buf, this.player.getName());
    writeC(buf, this.player.getLegionMember().getRank().getRankId());
    writeC(buf, this.isMember ? 1 : 0);
    writeC(buf, this.player.getCommonData().getPlayerClass().getClassId());
    writeC(buf, this.player.getLevel());
    writeD(buf, this.player.getPosition().getMapId());
    writeD(buf, this.msgId);
    writeS(buf, this.text);
  }
}
