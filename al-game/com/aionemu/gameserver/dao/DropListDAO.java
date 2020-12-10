package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.drop.DropList;


























public abstract class DropListDAO
  implements DAO
{
  public String getClassName() {
    return DropListDAO.class.getName();
  }
  
  public abstract DropList load();
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dao\DropListDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
