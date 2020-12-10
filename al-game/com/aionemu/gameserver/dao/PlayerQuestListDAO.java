package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.QuestStateList;

public abstract class PlayerQuestListDAO implements DAO {
  public String getClassName() {
    return PlayerQuestListDAO.class.getName();
  }

  public abstract QuestStateList load(Player paramPlayer);

  public abstract void store(Player paramPlayer);
}
