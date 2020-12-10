package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.TitleList;

public abstract class PlayerTitleListDAO implements DAO {
  public final String getClassName() {
    return PlayerTitleListDAO.class.getName();
  }

  public abstract TitleList loadTitleList(int paramInt);

  public abstract boolean storeTitles(Player paramPlayer);
}
