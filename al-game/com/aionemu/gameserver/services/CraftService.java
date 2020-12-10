package com.aionemu.gameserver.services;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.StaticObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.model.templates.recipe.Component;
import com.aionemu.gameserver.model.templates.recipe.RecipeTemplate;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.skillengine.task.CraftingTask;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import org.apache.log4j.Logger;

public class CraftService {
  private static final Logger log = Logger.getLogger(CraftService.class);

  public static void finishCrafting(Player player, RecipeTemplate recipetemplate, boolean critical) {
    int productItemId = 0;

    if (critical && recipetemplate.getComboProduct() != null) {
      productItemId = recipetemplate.getComboProduct().intValue();
    } else {
      productItemId = recipetemplate.getProductid().intValue();
    }
    if (productItemId != 0) {

      int xpReward = (int) ((0.008D * (recipetemplate.getSkillpoint().intValue() + 100)
          * (recipetemplate.getSkillpoint().intValue() + 100) + 60.0D) * player.getRates().getCraftingXPRate());
      ItemService.addItem(player, productItemId, recipetemplate.getQuantity().intValue());

      if (player.getSkillList().addSkillXp(player, recipetemplate.getSkillid().intValue(), xpReward)) {
        player.getCommonData().addExp(xpReward);
      } else {
        PacketSendUtility.sendPacket(player, (AionServerPacket) SM_SYSTEM_MESSAGE
            .MSG_DONT_GET_PRODUCTION_EXP(new DescriptionId(recipetemplate.getSkillid().intValue())));
      }
    }
    player.setCraftingTask(null);
  }

  public static void startCrafting(Player player, int targetTemplateId, int recipeId, int targetObjId) {
    if (player.getCraftingTask() != null && player.getCraftingTask().isInProgress()) {
      return;
    }
    RecipeTemplate recipeTemplate = DataManager.RECIPE_DATA.getRecipeTemplateById(recipeId);

    if (recipeTemplate != null) {

      int skillId = recipeTemplate.getSkillid().intValue();
      AionObject target = World.getInstance().findAionObject(targetObjId);

      if (skillId != 40009 && (target == null || !(target instanceof StaticObject))) {

        log.info("[AUDIT] Player " + player.getName() + " tried to craft incorrect target.");

        return;
      }
      if (recipeTemplate.getDp() != null && player.getCommonData().getDp() < recipeTemplate.getDp().intValue()) {

        log.info("[AUDIT] Player " + player.getName() + " modded her/his client.");
        return;
      }
      if (player.getInventory().isFull()) {

        PacketSendUtility.sendPacket(player, (AionServerPacket) SM_SYSTEM_MESSAGE.COMBINE_INVENTORY_IS_FULL);

        return;
      }
      for (Component component : recipeTemplate.getComponent()) {

        if (player.getInventory().getItemCountByItemId(component.getItemid().intValue()) < component.getQuantity()
            .intValue()) {

          log.info("[AUDIT] Player " + player.getName() + " modded her/his client.");

          return;
        }
      }

      ItemTemplate critItemTemplate = null;
      ItemTemplate itemTemplate = DataManager.ITEM_DATA.getItemTemplate(recipeTemplate.getProductid().intValue());
      if (itemTemplate == null) {
        return;
      }
      if (recipeTemplate.getComboProduct() != null) {
        critItemTemplate = DataManager.ITEM_DATA.getItemTemplate(recipeTemplate.getComboProduct().intValue());
      }
      if (critItemTemplate == null) {
        critItemTemplate = itemTemplate;
      }
      if (recipeTemplate.getDp() != null) {
        player.getCommonData().addDp(-recipeTemplate.getDp().intValue());
      }
      for (Component component : recipeTemplate.getComponent()) {
        ItemService.decreaseItemCountByItemId(player, component.getItemid().intValue(),
            component.getQuantity().intValue());
      }

      int skillLvlDiff = player.getSkillList().getSkillLevel(skillId) - recipeTemplate.getSkillpoint().intValue();
      player.setCraftingTask(new CraftingTask(player, (StaticObject) target, recipeTemplate, itemTemplate,
          critItemTemplate, skillLvlDiff));
      player.getCraftingTask().start();
    }
  }
}
