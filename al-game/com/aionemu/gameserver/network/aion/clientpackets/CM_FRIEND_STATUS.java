package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.FriendList;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;

public class CM_FRIEND_STATUS extends AionClientPacket {
  private int status;

  public CM_FRIEND_STATUS(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.status = readC();
  }

  protected void runImpl() {
    Player activePlayer = ((AionConnection) getConnection()).getActivePlayer();
    FriendList.Status statusEnum = FriendList.Status.getByValue(this.status);
    if (statusEnum == null)
      statusEnum = FriendList.Status.ONLINE;
    activePlayer.getFriendList().setStatus(statusEnum);
  }
}
