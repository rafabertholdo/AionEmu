package com.aionemu.gameserver.dao;

import com.aionemu.commons.database.dao.DAO;
import com.aionemu.gameserver.model.gameobjects.player.RecipeList;






















public abstract class PlayerRecipesDAO
  implements DAO
{
  public String getClassName() {
    return PlayerRecipesDAO.class.getName();
  }
  
  public abstract RecipeList load(int paramInt);
  
  public abstract boolean addRecipe(int paramInt1, int paramInt2);
  
  public abstract boolean deleteRecipe(int paramInt1, int paramInt2);
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dao\PlayerRecipesDAO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
