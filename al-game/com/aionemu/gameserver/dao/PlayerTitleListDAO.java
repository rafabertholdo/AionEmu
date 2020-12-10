package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.TitleList;
























public abstract class PlayerTitleListDAO
  implements DAO
{
  public final String getClassName() {
    return PlayerTitleListDAO.class.getName();
  }
  
  public abstract TitleList loadTitleList(int paramInt);
  
  public abstract boolean storeTitles(Player paramPlayer);
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dao\PlayerTitleListDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
