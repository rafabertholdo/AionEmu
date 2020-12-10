package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;

public class CM_REMOVE_ALTERED_STATE extends AionClientPacket {
  private int skillid;

  public CM_REMOVE_ALTERED_STATE(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.skillid = readH();
  }

  protected void runImpl() {
    Player player = ((AionConnection) getConnection()).getActivePlayer();
    player.getEffectController().removeEffect(this.skillid);
  }
}
