package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;

public abstract class ItemCooldownsDAO implements DAO {
  public final String getClassName() {
    return ItemCooldownsDAO.class.getName();
  }

  public abstract void loadItemCooldowns(Player paramPlayer);

  public abstract void storeItemCooldowns(Player paramPlayer);
}
