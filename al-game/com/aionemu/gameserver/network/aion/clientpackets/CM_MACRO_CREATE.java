package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MACRO_RESULT;
import com.aionemu.gameserver.services.PlayerService;
import org.apache.log4j.Logger;

public class CM_MACRO_CREATE extends AionClientPacket {
  private static final Logger log = Logger.getLogger(CM_MACRO_CREATE.class);

  private int macroPosition;

  private String macroXML;

  public CM_MACRO_CREATE(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.macroPosition = readC();
    this.macroXML = readS();
  }

  protected void runImpl() {
    log.debug(
        String.format("Created Macro #%d: %s", new Object[] { Integer.valueOf(this.macroPosition), this.macroXML }));

    PlayerService.addMacro(((AionConnection) getConnection()).getActivePlayer(), this.macroPosition, this.macroXML);

    sendPacket((AionServerPacket) SM_MACRO_RESULT.SM_MACRO_CREATED);
  }
}
