package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.AllianceService;
import com.aionemu.gameserver.services.GroupService;
import com.aionemu.gameserver.world.World;

public class CM_PLAYER_STATUS_INFO extends AionClientPacket {
  private int status;
  private int playerObjId;

  public CM_PLAYER_STATUS_INFO(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.status = readC();
    this.playerObjId = readD();
  }

  protected void runImpl() {
    Player player, myActivePlayer = ((AionConnection) getConnection()).getActivePlayer();

    switch (this.status) {

      case 9:
        ((AionConnection) getConnection()).getActivePlayer().setLookingForGroup((this.playerObjId == 2));
        break;

      case 12:
      case 14:
      case 15:
      case 19:
      case 23:
      case 24:
        AllianceService.getInstance().playerStatusInfo(myActivePlayer, this.status, this.playerObjId);
        break;

      case 2:
      case 3:
      case 6:
        player = null;

        if (this.playerObjId == 0) {
          player = ((AionConnection) getConnection()).getActivePlayer();
        } else {
          player = World.getInstance().findPlayer(this.playerObjId);
        }
        if (player == null || player.getPlayerGroup() == null) {
          return;
        }
        GroupService.getInstance().playerStatusInfo(this.status, player);
        break;
    }
  }
}
