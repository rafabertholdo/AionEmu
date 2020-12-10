package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.model.ChatType;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_MESSAGE extends AionServerPacket {
  private Player player;
  private int senderObjectId;
  private String message;
  private String senderName;
  private Race race;
  private ChatType chatType;
  private float x;
  private float y;
  private float z;

  public SM_MESSAGE(Player player, String message, ChatType chatType) {
    this.player = player;
    this.senderObjectId = player.getObjectId();
    this.senderName = player.getName();
    this.message = message;
    this.race = player.getCommonData().getRace();
    this.chatType = chatType;
    this.x = player.getX();
    this.y = player.getY();
    this.z = player.getZ();
  }

  public SM_MESSAGE(int senderObjectId, String senderName, String message, ChatType chatType) {
    this.senderObjectId = senderObjectId;
    this.senderName = senderName;
    this.message = message;
    this.chatType = chatType;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    boolean canRead = true;

    if (this.race != null) {
      canRead = (this.chatType.isSysMsg() || CustomConfig.FACTIONS_SPEAKING_MODE == 1
          || this.player.getAccessLevel() > 0
          || (con.getActivePlayer() != null && con.getActivePlayer().getAccessLevel() > 0));
    }

    writeC(buf, this.chatType.toInteger());

    writeC(buf, canRead ? 0 : (this.race.getRaceId() + 1));
    writeD(buf, this.senderObjectId);

    switch (this.chatType) {

      case NORMAL:
      case UNKNOWN_0x18:
      case ANNOUNCEMENTS:
      case PERIOD_NOTICE:
      case PERIOD_ANNOUNCEMENTS:
      case SYSTEM_NOTICE:
        writeH(buf, 0);
        writeS(buf, this.message);
        break;
      case SHOUT:
        writeS(buf, this.senderName);
        writeS(buf, this.message);
        writeF(buf, this.x);
        writeF(buf, this.y);
        writeF(buf, this.z);
        break;
      case ALLIANCE:
      case GROUP:
      case GROUP_LEADER:
      case LEGION:
      case WHISPER:
        writeS(buf, this.senderName);
        writeS(buf, this.message);
        break;
    }
  }
}
