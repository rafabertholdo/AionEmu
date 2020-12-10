package com.aionemu.gameserver.dao;

import com.aionemu.gameserver.model.gameobjects.Letter;
import com.aionemu.gameserver.model.gameobjects.player.Mailbox;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import java.sql.Timestamp;

public abstract class MailDAO implements IDFactoryAwareDAO {
  public String getClassName() {
    return MailDAO.class.getName();
  }

  public abstract boolean storeLetter(Timestamp paramTimestamp, Letter paramLetter);

  public abstract Mailbox loadPlayerMailbox(Player paramPlayer);

  public abstract void storeMailbox(Player paramPlayer);

  public abstract boolean deleteLetter(int paramInt);

  public abstract void updateOfflineMailCounter(PlayerCommonData paramPlayerCommonData);
}
