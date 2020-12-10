package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;



























public abstract class ItemCooldownsDAO
  implements DAO
{
  public final String getClassName() {
    return ItemCooldownsDAO.class.getName();
  }
  
  public abstract void loadItemCooldowns(Player paramPlayer);
  
  public abstract void storeItemCooldowns(Player paramPlayer);
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dao\ItemCooldownsDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
