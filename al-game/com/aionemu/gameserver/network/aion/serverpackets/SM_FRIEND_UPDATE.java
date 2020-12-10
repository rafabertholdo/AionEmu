package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Friend;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

public class SM_FRIEND_UPDATE extends AionServerPacket {
  private int friendObjId;
  private static Logger log = Logger.getLogger(SM_FRIEND_UPDATE.class);

  public SM_FRIEND_UPDATE(int friendObjId) {
    this.friendObjId = friendObjId;
  }

  public void writeImpl(AionConnection con, ByteBuffer buf) {
    Friend f = con.getActivePlayer().getFriendList().getFriend(this.friendObjId);
    if (f == null) {
      log.debug("Attempted to update friend list status of " + this.friendObjId + " for "
          + con.getActivePlayer().getName() + " - object ID not found on friend list");
    } else {

      writeS(buf, f.getName());
      writeD(buf, f.getLevel());
      writeD(buf, f.getPlayerClass().getClassId());
      writeC(buf, f.isOnline() ? 1 : 0);
      writeD(buf, f.getMapId());
      writeD(buf, f.getLastOnlineTime());
      writeS(buf, f.getNote());
      writeC(buf, f.getStatus().getIntValue());
    }
  }
}
