package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;

public abstract class GameTimeDAO implements DAO {
  public final String getClassName() {
    return GameTimeDAO.class.getName();
  }

  public abstract int load();

  public abstract boolean store(int paramInt);
}
