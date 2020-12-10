package com.aionemu.gameserver.itemengine.actions;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.utils.PacketSendUtility;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;






















@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SkillUseAction")
public class SkillUseAction
  extends AbstractItemAction
{
  @XmlAttribute
  protected int skillid;
  @XmlAttribute
  protected int level;
  
  public int getSkillid() {
    return this.skillid;
  }



  
  public int getLevel() {
    return this.level;
  }


  
  public boolean canAct(Player player, Item parentItem, Item targetItem) {
    Skill skill = SkillEngine.getInstance().getSkill((Creature)player, this.skillid, this.level, player.getTarget());
    if (skill == null) {
      return false;
    }
    return skill.canUseSkill();
  }


  
  public void act(Player player, Item parentItem, Item targetItem) {
    Skill skill = SkillEngine.getInstance().getSkill((Creature)player, this.skillid, this.level, player.getTarget());
    if (skill != null) {
      
      skill.setItemTemplate(parentItem.getItemTemplate());
      PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), parentItem.getObjectId(), parentItem.getItemTemplate().getTemplateId()), true);
      
      skill.useSkill();
      
      ItemService.decreaseItemCount(player, parentItem, 1L);
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\itemengine\actions\SkillUseAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
