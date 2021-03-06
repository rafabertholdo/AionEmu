package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;

public abstract class PlayerPunishmentsDAO implements DAO {
  public final String getClassName() {
    return PlayerPunishmentsDAO.class.getName();
  }

  public abstract void loadPlayerPunishments(Player paramPlayer);

  public abstract void storePlayerPunishments(Player paramPlayer);

  public abstract void punishPlayer(Player paramPlayer, int paramInt);

  public abstract void unpunishPlayer(Player paramPlayer);
}
