package com.aionemu.gameserver.model.gameobjects.player;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.PlayerRecipesDAO;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.templates.recipe.RecipeTemplate;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEARN_RECIPE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_RECIPE_DELETE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.HashSet;
import java.util.Set;

public class RecipeList {
  private Set<Integer> recipeList = new HashSet<Integer>();

  public RecipeList(HashSet<Integer> recipeList) {
    this.recipeList = recipeList;
  }

  public Set<Integer> getRecipeList() {
    return this.recipeList;
  }

  public void addRecipe(Player player, RecipeTemplate recipeTemplate) {
    int recipeId = recipeTemplate.getId().intValue();
    if (!this.recipeList.contains(Integer.valueOf(recipeId))) {

      this.recipeList.add(Integer.valueOf(recipeId));
      ((PlayerRecipesDAO) DAOManager.getDAO(PlayerRecipesDAO.class)).addRecipe(player.getObjectId(), recipeId);
      PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_LEARN_RECIPE(recipeId));
      PacketSendUtility.sendPacket(player,
          (AionServerPacket) SM_SYSTEM_MESSAGE.CRAFT_RECIPE_LEARN(new DescriptionId(recipeTemplate.getNameid())));
    }
  }

  public void deleteRecipe(Player player, int recipeId) {
    if (this.recipeList.contains(Integer.valueOf(recipeId))) {

      this.recipeList.remove(Integer.valueOf(recipeId));
      ((PlayerRecipesDAO) DAOManager.getDAO(PlayerRecipesDAO.class)).deleteRecipe(player.getObjectId(), recipeId);
      PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_RECIPE_DELETE(recipeId));
    }
  }

  public void autoLearnRecipe(Player player, int skillId, int skillLvl) {
    for (RecipeTemplate recipe : DataManager.RECIPE_DATA.getRecipeIdFor(player.getCommonData().getRace(), skillId,
        skillLvl)) {
      player.getRecipeList().addRecipe(player, recipe);
    }
  }

  public boolean isRecipePresent(int recipeId) {
    return this.recipeList.contains(Integer.valueOf(recipeId));
  }
}
