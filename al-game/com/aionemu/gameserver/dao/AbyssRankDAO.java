package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;

public abstract class AbyssRankDAO implements DAO {
  public final String getClassName() {
    return AbyssRankDAO.class.getName();
  }

  public abstract void loadAbyssRank(Player paramPlayer);

  public abstract boolean storeAbyssRank(Player paramPlayer);
}
