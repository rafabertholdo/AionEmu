package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.MacroList;































public abstract class PlayerMacrossesDAO
  implements DAO
{
  public final String getClassName() {
    return PlayerMacrossesDAO.class.getName();
  }
  
  public abstract MacroList restoreMacrosses(int paramInt);
  
  public abstract void addMacro(int paramInt1, int paramInt2, String paramString);
  
  public abstract void deleteMacro(int paramInt1, int paramInt2);
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dao\PlayerMacrossesDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
