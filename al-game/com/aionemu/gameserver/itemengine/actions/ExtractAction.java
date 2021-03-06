package com.aionemu.gameserver.itemengine.actions;

import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.EnchantService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExtractAction")
public class ExtractAction extends AbstractItemAction {
  public boolean canAct(Player player, Item parentItem, Item targetItem) {
    if (targetItem == null) {

      PacketSendUtility.sendPacket(player, (AionServerPacket) SM_SYSTEM_MESSAGE.STR_ITEM_ERROR);
      return false;
    }

    return true;
  }

  public void act(final Player player, final Item parentItem, final Item targetItem) {
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), parentItem.getObjectId(), parentItem.getItemTemplate().getTemplateId(), 5000, 0, 0));
    
    player.getController().cancelTask(TaskId.ITEM_USE);
    player.getController().addNewTask(TaskId.ITEM_USE, ThreadPoolManager.getInstance().schedule(new Runnable()
          {
            
            public void run()
            {
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), parentItem.getObjectId(), parentItem.getItemTemplate().getTemplateId(), 0, 1, 0));

              
              EnchantService.breakItem(player, targetItem, parentItem);
            }
          }5000L));
  }
}
