package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.Gatherable;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;

public class CM_GATHER extends AionClientPacket {
  boolean isStartGather = false;

  public CM_GATHER(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    int action = readD();
    if (action == 0) {
      this.isStartGather = true;
    }
  }

  protected void runImpl() {
    Player player = ((AionConnection) getConnection()).getActivePlayer();
    if (player != null) {

      VisibleObject target = player.getTarget();
      if (target != null && target instanceof Gatherable) {
        if (this.isStartGather) {

          ((Gatherable) target).getController().onStartUse(player);
        } else {

          ((Gatherable) target).getController().finishGathering(player);
        }
      }
    }
  }
}
