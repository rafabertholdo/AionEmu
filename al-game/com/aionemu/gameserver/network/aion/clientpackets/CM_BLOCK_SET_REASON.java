package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.BlockedPlayer;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.SocialService;

public class CM_BLOCK_SET_REASON extends AionClientPacket {
  String targetName;
  String reason;

  public CM_BLOCK_SET_REASON(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.targetName = readS();
    this.reason = readS();
  }

  protected void runImpl() {
    Player activePlayer = ((AionConnection) getConnection()).getActivePlayer();
    BlockedPlayer target = activePlayer.getBlockList().getBlockedPlayer(this.targetName);

    if (target == null) {
      sendPacket((AionServerPacket) SM_SYSTEM_MESSAGE.BLOCKLIST_NOT_BLOCKED);
    } else {

      SocialService.setBlockedReason(activePlayer, target, this.reason);
    }
  }
}
