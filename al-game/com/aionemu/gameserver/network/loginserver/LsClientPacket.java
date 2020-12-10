package com.aionemu.gameserver.network.loginserver;

import com.aionemu.commons.network.packet.BaseClientPacket;
import org.apache.log4j.Logger;

public abstract class LsClientPacket extends BaseClientPacket<LoginServerConnection> implements Cloneable {
  private static final Logger log = Logger.getLogger(LsClientPacket.class);

  protected LsClientPacket(int opcode) {
    super(opcode);
  }

  public final void run() {
    try {
      runImpl();
    } catch (Throwable e) {

      log.warn("error handling ls (" + ((LoginServerConnection) getConnection()).getIP() + ") message " + this, e);
    }
  }

  protected void sendPacket(LsServerPacket msg) {
    ((LoginServerConnection) getConnection()).sendPacket(msg);
  }

  public LsClientPacket clonePacket() {
    try {
      return (LsClientPacket) clone();
    } catch (CloneNotSupportedException e) {

      return null;
    }
  }
}
