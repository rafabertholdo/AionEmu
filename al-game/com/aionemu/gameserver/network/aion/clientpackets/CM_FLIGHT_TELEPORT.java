package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.world.World;

public class CM_FLIGHT_TELEPORT extends AionClientPacket {
  float x;
  float y;
  float z;
  int distance;

  public CM_FLIGHT_TELEPORT(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    readD();
    this.x = readF();
    this.y = readF();
    this.z = readF();
    readC();
    this.distance = readD();
  }

  protected void runImpl() {
    Player player = ((AionConnection) getConnection()).getActivePlayer();

    if (player != null && player.isInState(CreatureState.FLIGHT_TELEPORT)) {

      player.setFlightDistance(this.distance);
      World.getInstance().updatePosition((VisibleObject) player, this.x, this.y, this.z, (byte) 0);
    }
  }
}
