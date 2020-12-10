package com.aionemu.gameserver.skillengine.task;

import com.aionemu.gameserver.model.gameobjects.StaticObject;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.model.templates.recipe.RecipeTemplate;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CRAFT_ANIMATION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CRAFT_UPDATE;
import com.aionemu.gameserver.services.CraftService;
import com.aionemu.gameserver.utils.PacketSendUtility;




























public class CraftingTask
  extends AbstractCraftTask
{
  private RecipeTemplate recipeTemplate;
  private ItemTemplate itemTemplate;
  private ItemTemplate criticalTemplate;
  
  public CraftingTask(Player requestor, StaticObject responder, RecipeTemplate recipeTemplate, ItemTemplate itemTemplate, ItemTemplate criticalTemplate, int skillLvlDiff) {
    super(requestor, (VisibleObject)responder, 100, 100, skillLvlDiff);
    this.recipeTemplate = recipeTemplate;
    this.itemTemplate = itemTemplate;
    this.criticalTemplate = criticalTemplate;
  }





  
  protected void onFailureFinish() {
    PacketSendUtility.sendPacket(this.requestor, (AionServerPacket)new SM_CRAFT_UPDATE(this.recipeTemplate.getSkillid().intValue(), this.itemTemplate, this.currentSuccessValue, this.currentFailureValue, 6));
    PacketSendUtility.broadcastPacket(this.requestor, (AionServerPacket)new SM_CRAFT_ANIMATION(this.requestor.getObjectId(), this.responder.getObjectId(), 0, 3), true);
  }





  
  protected void onSuccessFinish() {
    PacketSendUtility.sendPacket(this.requestor, (AionServerPacket)new SM_CRAFT_UPDATE(this.recipeTemplate.getSkillid().intValue(), this.setCritical ? this.criticalTemplate : this.itemTemplate, this.currentSuccessValue, this.currentFailureValue, 5));
    PacketSendUtility.broadcastPacket(this.requestor, (AionServerPacket)new SM_CRAFT_ANIMATION(this.requestor.getObjectId(), this.responder.getObjectId(), 0, 2), true);
    CraftService.finishCrafting(this.requestor, this.recipeTemplate, this.critical);
  }





  
  protected void sendInteractionUpdate() {
    PacketSendUtility.sendPacket(this.requestor, (AionServerPacket)new SM_CRAFT_UPDATE(this.recipeTemplate.getSkillid().intValue(), this.itemTemplate, this.currentSuccessValue, this.currentFailureValue, this.setCritical ? 2 : 1));
  }





  
  protected void onInteractionAbort() {
    PacketSendUtility.sendPacket(this.requestor, (AionServerPacket)new SM_CRAFT_UPDATE(this.recipeTemplate.getSkillid().intValue(), this.itemTemplate, 0, 0, 4));
    PacketSendUtility.broadcastPacket(this.requestor, (AionServerPacket)new SM_CRAFT_ANIMATION(this.requestor.getObjectId(), this.responder.getObjectId(), 0, 2), true);
  }






  
  protected void onInteractionFinish() {}





  
  protected void onInteractionStart() {
    PacketSendUtility.sendPacket(this.requestor, (AionServerPacket)new SM_CRAFT_UPDATE(this.recipeTemplate.getSkillid().intValue(), this.itemTemplate, 100, 100, 0));
    PacketSendUtility.sendPacket(this.requestor, (AionServerPacket)new SM_CRAFT_UPDATE(this.recipeTemplate.getSkillid().intValue(), this.itemTemplate, 0, 0, 1));
    PacketSendUtility.broadcastPacket(this.requestor, (AionServerPacket)new SM_CRAFT_ANIMATION(this.requestor.getObjectId(), this.responder.getObjectId(), this.recipeTemplate.getSkillid().intValue(), 0), true);
    PacketSendUtility.broadcastPacket(this.requestor, (AionServerPacket)new SM_CRAFT_ANIMATION(this.requestor.getObjectId(), this.responder.getObjectId(), this.recipeTemplate.getSkillid().intValue(), 1), true);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\task\CraftingTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
