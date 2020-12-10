package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.drop.DropList;

public abstract class DropListDAO implements DAO {
  public String getClassName() {
    return DropListDAO.class.getName();
  }

  public abstract DropList load();
}
