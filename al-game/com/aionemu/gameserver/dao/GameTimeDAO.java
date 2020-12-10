package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;


























public abstract class GameTimeDAO
  implements DAO
{
  public final String getClassName() {
    return GameTimeDAO.class.getName();
  }
  
  public abstract int load();
  
  public abstract boolean store(int paramInt);
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dao\GameTimeDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
