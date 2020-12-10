package com.aionemu.gameserver.network.loginserver.serverpackets;

import com.aionemu.gameserver.network.loginserver.LoginServerConnection;
import com.aionemu.gameserver.network.loginserver.LsServerPacket;
import java.nio.ByteBuffer;

public class SM_BAN extends LsServerPacket {
  private final byte type;
  private final int accountId;
  private final String ip;
  private final int time;
  private final int adminObjId;

  public SM_BAN(byte type, int accountId, String ip, int time, int adminObjId) {
    super(6);

    this.type = type;
    this.accountId = accountId;
    this.ip = ip;
    this.time = time;
    this.adminObjId = adminObjId;
  }

  protected void writeImpl(LoginServerConnection con, ByteBuffer buf) {
    writeC(buf, getOpcode());

    writeC(buf, this.type);
    writeD(buf, this.accountId);
    writeS(buf, this.ip);
    writeD(buf, this.time);
    writeD(buf, this.adminObjId);
  }
}
