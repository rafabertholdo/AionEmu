package com.aionemu.gameserver.dao;

import com.aionemu.gameserver.model.account.PlayerAccountData;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import java.sql.Timestamp;
import java.util.List;

public abstract class PlayerDAO implements IDFactoryAwareDAO {
  public abstract boolean isNameUsed(String paramString);

  public abstract void storePlayer(Player paramPlayer);

  public abstract boolean saveNewPlayer(PlayerCommonData paramPlayerCommonData, int paramInt, String paramString);

  public abstract PlayerCommonData loadPlayerCommonData(int paramInt);

  public abstract void deletePlayer(int paramInt);

  public abstract void updateDeletionTime(int paramInt, Timestamp paramTimestamp);

  public abstract void storeCreationTime(int paramInt, Timestamp paramTimestamp);

  public abstract void setCreationDeletionTime(PlayerAccountData paramPlayerAccountData);

  public abstract List<Integer> getPlayerOidsOnAccount(int paramInt);

  public abstract void storeLastOnlineTime(int paramInt, Timestamp paramTimestamp);

  public abstract void onlinePlayer(Player paramPlayer, boolean paramBoolean);

  public abstract void setPlayersOffline(boolean paramBoolean);

  public abstract PlayerCommonData loadPlayerCommonDataByName(String paramString);

  public abstract int getAccountIdByName(String paramString);

  public abstract String getPlayerNameByObjId(int paramInt);

  public final String getClassName() {
    return PlayerDAO.class.getName();
  }
}
