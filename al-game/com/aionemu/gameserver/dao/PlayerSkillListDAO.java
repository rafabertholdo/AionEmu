package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.SkillList;

public abstract class PlayerSkillListDAO implements DAO {
  public final String getClassName() {
    return PlayerSkillListDAO.class.getName();
  }

  public abstract SkillList loadSkillList(int paramInt);

  public abstract boolean storeSkills(Player paramPlayer);
}
