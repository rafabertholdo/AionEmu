package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;

public abstract class PlayerSettingsDAO implements DAO {
  public final String getClassName() {
    return PlayerSettingsDAO.class.getName();
  }

  public abstract void saveSettings(Player paramPlayer);

  public abstract void loadSettings(Player paramPlayer);
}
