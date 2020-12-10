package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_LEGION_UPDATE_MEMBER extends AionServerPacket {
  private static final int OFFLINE = 0;
  private static final int ONLINE = 1;
  private Player player;
  private int msgId;
  private String text;

  public SM_LEGION_UPDATE_MEMBER(Player player, int msgId, String text) {
    this.player = player;
    this.msgId = msgId;
    this.text = text;
  }

  public void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.player.getObjectId());
    writeC(buf, this.player.getLegionMember().getRank().getRankId());
    writeC(buf, this.player.getCommonData().getPlayerClass().getClassId());
    writeC(buf, this.player.getLevel());
    writeD(buf, this.player.getPosition().getMapId());
    writeC(buf, this.player.isOnline() ? 1 : 0);
    writeD(buf, this.player.getLastOnline());
    writeD(buf, this.msgId);
    writeS(buf, this.text);
  }
}
