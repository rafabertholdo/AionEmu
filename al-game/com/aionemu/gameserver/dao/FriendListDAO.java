package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.FriendList;
import com.aionemu.gameserver.model.gameobjects.player.Player;

public abstract class FriendListDAO implements DAO {
  public String getClassName() {
    return FriendListDAO.class.getName();
  }

  public abstract FriendList load(Player paramPlayer);

  public abstract boolean addFriends(Player paramPlayer1, Player paramPlayer2);

  public abstract boolean delFriends(int paramInt1, int paramInt2);
}
