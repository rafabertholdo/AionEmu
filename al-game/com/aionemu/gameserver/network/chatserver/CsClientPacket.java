package com.aionemu.gameserver.network.chatserver;

import com.aionemu.commons.network.packet.BaseClientPacket;
import org.apache.log4j.Logger;

public abstract class CsClientPacket extends BaseClientPacket<ChatServerConnection> implements Cloneable {
  private static final Logger log = Logger.getLogger(CsClientPacket.class);

  protected CsClientPacket(int opcode) {
    super(opcode);
  }

  public final void run() {
    try {
      runImpl();
    } catch (Throwable e) {

      log.warn("error handling ls (" + ((ChatServerConnection) getConnection()).getIP() + ") message " + this, e);
    }
  }

  protected void sendPacket(CsServerPacket msg) {
    ((ChatServerConnection) getConnection()).sendPacket(msg);
  }

  public CsClientPacket clonePacket() {
    try {
      return (CsClientPacket) clone();
    } catch (CloneNotSupportedException e) {

      return null;
    }
  }
}
