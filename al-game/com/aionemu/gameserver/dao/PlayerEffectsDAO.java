package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;

public abstract class PlayerEffectsDAO implements DAO {
  public final String getClassName() {
    return PlayerEffectsDAO.class.getName();
  }

  public abstract void loadPlayerEffects(Player paramPlayer);

  public abstract void storePlayerEffects(Player paramPlayer);
}
