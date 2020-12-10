package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.AllianceService;
import com.aionemu.gameserver.services.GroupService;

public class CM_SHOW_BRAND extends AionClientPacket {
  private int brandId;
  private int targetObjectId;

  public CM_SHOW_BRAND(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.brandId = readD();
    this.targetObjectId = readD();
  }

  protected void runImpl() {
    Player player = ((AionConnection) getConnection()).getActivePlayer();

    if (player == null) {
      return;
    }
    if (player.isInGroup())
      GroupService.getInstance().showBrand(player.getPlayerGroup(), this.brandId, this.targetObjectId);
    if (player.isInAlliance())
      AllianceService.getInstance().showBrand(player.getPlayerAlliance(), this.brandId, this.targetObjectId);
  }
}
