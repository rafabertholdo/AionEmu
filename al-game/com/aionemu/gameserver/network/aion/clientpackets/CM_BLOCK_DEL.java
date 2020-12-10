package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.BlockedPlayer;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.SocialService;
import org.apache.log4j.Logger;

public class CM_BLOCK_DEL extends AionClientPacket {
  private static Logger log = Logger.getLogger(CM_BLOCK_DEL.class);

  private String targetName;

  public CM_BLOCK_DEL(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.targetName = readS();
  }

  protected void runImpl() {
    Player activePlayer = ((AionConnection) getConnection()).getActivePlayer();

    BlockedPlayer target = activePlayer.getBlockList().getBlockedPlayer(this.targetName);
    if (target == null) {

      sendPacket((AionServerPacket) SM_SYSTEM_MESSAGE.BUDDYLIST_NOT_IN_LIST);

    } else if (!SocialService.deleteBlockedUser(activePlayer, target.getObjId())) {

      log.debug("Could not unblock " + this.targetName + " from " + activePlayer.getName()
          + " blocklist. Check database setup.");
    }
  }
}
