package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.BlockList;
import com.aionemu.gameserver.model.gameobjects.player.Player;

public abstract class BlockListDAO implements DAO {
  public abstract BlockList load(Player paramPlayer);

  public abstract boolean addBlockedUser(int paramInt1, int paramInt2, String paramString);

  public abstract boolean delBlockedUser(int paramInt1, int paramInt2);

  public abstract boolean setReason(int paramInt1, int paramInt2, String paramString);

  public String getClassName() {
    return BlockListDAO.class.getName();
  }
}
