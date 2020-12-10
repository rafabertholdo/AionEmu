package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Friend;
import com.aionemu.gameserver.model.gameobjects.player.FriendList;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_FRIEND_LIST extends AionServerPacket {
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    FriendList list = con.getActivePlayer().getFriendList();

    writeH(buf, 0 - list.getSize());
    writeC(buf, 0);

    for (Friend friend : list) {

      writeS(buf, friend.getName());
      writeD(buf, friend.getLevel());
      writeD(buf, friend.getPlayerClass().getClassId());
      writeC(buf, 1);
      writeD(buf, friend.getMapId());
      writeD(buf, friend.getLastOnlineTime());
      writeS(buf, friend.getNote());
      writeC(buf, friend.getStatus().getIntValue());
    }
  }
}
