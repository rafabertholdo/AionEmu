package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;

public abstract class PlayerLifeStatsDAO implements DAO {
  public final String getClassName() {
    return PlayerLifeStatsDAO.class.getName();
  }

  public abstract void loadPlayerLifeStat(Player paramPlayer);

  public abstract void insertPlayerLifeStat(Player paramPlayer);

  public abstract void updatePlayerLifeStat(Player paramPlayer);

  public abstract void deletePlayerLifeStat(int paramInt);
}
