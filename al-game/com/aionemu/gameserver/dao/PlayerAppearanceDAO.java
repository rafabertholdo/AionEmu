package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerAppearance;






























public abstract class PlayerAppearanceDAO
  implements DAO
{
  public final String getClassName() {
    return PlayerAppearanceDAO.class.getName();
  }









  
  public abstract PlayerAppearance load(int paramInt);








  
  public final boolean store(Player player) {
    return store(player.getObjectId(), player.getPlayerAppearance());
  }
  
  public abstract boolean store(int paramInt, PlayerAppearance paramPlayerAppearance);
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dao\PlayerAppearanceDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
