package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.account.Account;
import com.aionemu.gameserver.model.account.PlayerAccountData;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.PlayerInfo;
import java.nio.ByteBuffer;

public class SM_CHARACTER_LIST extends PlayerInfo {
  private final int playOk2;

  public SM_CHARACTER_LIST(int playOk2) {
    this.playOk2 = playOk2;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.playOk2);

    Account account = con.getAccount();
    writeC(buf, account.size());

    for (PlayerAccountData playerData : account.getSortedAccountsList()) {

      writePlayerInfo(buf, playerData);
      writeB(buf, new byte[14]);
    }
  }
}
