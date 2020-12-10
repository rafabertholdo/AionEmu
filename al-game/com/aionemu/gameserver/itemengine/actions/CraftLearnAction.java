package com.aionemu.gameserver.itemengine.actions;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.recipe.RecipeTemplate;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;






















@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CraftLearnAction")
public class CraftLearnAction
  extends AbstractItemAction
{
  @XmlAttribute
  protected int recipeid;
  
  public boolean canAct(Player player, Item parentItem, Item targetItem) {
    RecipeTemplate template = DataManager.RECIPE_DATA.getRecipeTemplateById(this.recipeid);
    if (template == null) {
      return false;
    }
    if (template.getRace().ordinal() != player.getCommonData().getRace().getRaceId()) {
      return false;
    }
    if (player.getRecipeList().isRecipePresent(this.recipeid)) {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SYSTEM_MESSAGE(1330060, new Object[0]));
      return false;
    } 
    
    if (!player.getSkillList().isSkillPresent(template.getSkillid().intValue())) {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SYSTEM_MESSAGE(1330062, new Object[] { DataManager.SKILL_DATA.getSkillTemplate(template.getSkillid().intValue()).getName() }));
      
      return false;
    } 
    
    if (template.getSkillpoint().intValue() > player.getSkillList().getSkillLevel(template.getSkillid().intValue())) {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SYSTEM_MESSAGE(1330063, new Object[0]));
      return false;
    } 
    return true;
  }


  
  public void act(Player player, Item parentItem, Item targetItem) {
    RecipeTemplate template = DataManager.RECIPE_DATA.getRecipeTemplateById(this.recipeid);
    
    PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.USE_ITEM(new DescriptionId(parentItem.getItemTemplate().getNameId())));
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), parentItem.getObjectId(), parentItem.getItemTemplate().getTemplateId()));
    
    if (ItemService.decreaseItemCount(player, parentItem, 1L) == 0L)
    {
      player.getRecipeList().addRecipe(player, template);
    }
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\itemengine\actions\CraftLearnAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
