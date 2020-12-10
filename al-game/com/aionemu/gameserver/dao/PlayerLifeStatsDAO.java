package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;



























public abstract class PlayerLifeStatsDAO
  implements DAO
{
  public final String getClassName() {
    return PlayerLifeStatsDAO.class.getName();
  }
  
  public abstract void loadPlayerLifeStat(Player paramPlayer);
  
  public abstract void insertPlayerLifeStat(Player paramPlayer);
  
  public abstract void updatePlayerLifeStat(Player paramPlayer);
  
  public abstract void deletePlayerLifeStat(int paramInt);
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dao\PlayerLifeStatsDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
