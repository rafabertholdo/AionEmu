package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PONG;
import org.apache.log4j.Logger;

public class CM_PING extends AionClientPacket {
  private static final Logger log = Logger.getLogger(CM_PING.class);

  public CM_PING(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
  }

  protected void runImpl() {
    long lastMS = ((AionConnection) getConnection()).getLastPingTimeMS();

    if (lastMS > 0L) {

      long pingInterval = System.currentTimeMillis() - lastMS;

      if (pingInterval < 100000L) {

        String ip = ((AionConnection) getConnection()).getIP();
        String name = "[unknown]";
        if (((AionConnection) getConnection()).getActivePlayer() != null)
          name = ((AionConnection) getConnection()).getActivePlayer().getName();
        log.info("[AUDIT] possible client timer cheat: " + pingInterval + " by " + name + ", ip=" + ip);
      }
    }

    ((AionConnection) getConnection()).setLastPingTimeMS(System.currentTimeMillis());
    sendPacket((AionServerPacket) new SM_PONG());
  }
}
