package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;

public class CM_SKILL_DEACTIVATE extends AionClientPacket {
  private int skillId;

  public CM_SKILL_DEACTIVATE(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.skillId = readD();
  }

  protected void runImpl() {
    Player player = ((AionConnection) getConnection()).getActivePlayer();
    player.getEffectController().removeNoshowEffect(this.skillId);
  }
}
