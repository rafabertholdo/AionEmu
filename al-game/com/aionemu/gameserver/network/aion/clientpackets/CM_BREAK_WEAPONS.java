package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.ArmsfusionService;

public class CM_BREAK_WEAPONS extends AionClientPacket {
  private int weaponToBreakUniqueId;

  public CM_BREAK_WEAPONS(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    readD();
    this.weaponToBreakUniqueId = readD();
  }

  protected void runImpl() {
    ArmsfusionService.breakWeapons(((AionConnection) getConnection()).getActivePlayer(), this.weaponToBreakUniqueId);
  }
}
