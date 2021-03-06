package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import org.apache.log4j.Logger;

public class CM_REPORT_PLAYER extends AionClientPacket {
  private static final Logger log = Logger.getLogger(CM_REPORT_PLAYER.class);

  private String player;

  public CM_REPORT_PLAYER(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    readB(1);
    this.player = readS();
  }

  protected void runImpl() {
    Player p = ((AionConnection) getConnection()).getActivePlayer();
    log.info("[AUDIT] " + p.getName() + " reports the player: " + this.player);
  }
}
