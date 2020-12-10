package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Friend;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.SocialService;
import org.apache.log4j.Logger;

public class CM_FRIEND_DEL extends AionClientPacket {
  private String targetName;
  private static Logger log = Logger.getLogger(CM_FRIEND_DEL.class);

  public CM_FRIEND_DEL(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.targetName = readS();
  }

  protected void runImpl() {
    Player activePlayer = ((AionConnection) getConnection()).getActivePlayer();
    Friend target = activePlayer.getFriendList().getFriend(this.targetName);
    if (target == null) {

      log.warn(activePlayer.getName() + " tried to delete friend " + this.targetName + " who is not his friend");
      sendPacket((AionServerPacket) SM_SYSTEM_MESSAGE.BUDDYLIST_NOT_IN_LIST);
    } else {

      SocialService.deleteFriend(activePlayer, target.getOid());
    }
  }
}
