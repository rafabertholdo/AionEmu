package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.MacroList;

public abstract class PlayerMacrossesDAO implements DAO {
  public final String getClassName() {
    return PlayerMacrossesDAO.class.getName();
  }

  public abstract MacroList restoreMacrosses(int paramInt);

  public abstract void addMacro(int paramInt1, int paramInt2, String paramString);

  public abstract void deleteMacro(int paramInt1, int paramInt2);
}
