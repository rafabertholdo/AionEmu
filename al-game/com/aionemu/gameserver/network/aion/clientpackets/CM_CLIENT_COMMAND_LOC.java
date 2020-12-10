package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import org.apache.log4j.Logger;

public class CM_CLIENT_COMMAND_LOC extends AionClientPacket {
  private static final Logger log = Logger.getLogger(CM_CLIENT_COMMAND_LOC.class);

  public CM_CLIENT_COMMAND_LOC(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
  }

  protected void runImpl() {
    Player player = ((AionConnection) getConnection()).getActivePlayer();
    log.info("[AUDIT] Received \"/loc\" command");

    sendPacket((AionServerPacket) SM_SYSTEM_MESSAGE.CURRENT_LOCATION(player.getWorldId(), player.getX(), player.getY(),
        player.getZ()));
  }
}
