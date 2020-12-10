package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.itemengine.actions.AbstractItemAction;
import com.aionemu.gameserver.itemengine.actions.ItemActions;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.ItemRace;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.restrictions.RestrictionsManager;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.ArrayList;
import org.apache.log4j.Logger;


















public class CM_USE_ITEM
  extends AionClientPacket
{
  public int uniqueItemId;
  public int type;
  public int targetItemId;
  private static final Logger log = Logger.getLogger(CM_USE_ITEM.class);
  
  public CM_USE_ITEM(int opcode) {
    super(opcode);
  }




  
  protected void readImpl() {
    this.uniqueItemId = readD();
    this.type = readC();
    if (this.type == 2)
    {
      this.targetItemId = readD();
    }
  }





  
  protected void runImpl() {
    Player player = ((AionConnection)getConnection()).getActivePlayer();
    Item item = player.getInventory().getItemByObjId(this.uniqueItemId);
    
    if (item == null) {
      
      log.warn(String.format("CHECKPOINT: null item use action: %d %d", new Object[] { Integer.valueOf(player.getObjectId()), Integer.valueOf(this.uniqueItemId) }));
      
      return;
    } 
    if (!RestrictionsManager.canUseItem(player)) {
      return;
    }
    
    switch (item.getItemTemplate().getRace()) {
      
      case ASMODIANS:
        if (player.getCommonData().getRace() != Race.ASMODIANS)
          return; 
        break;
      case ELYOS:
        if (player.getCommonData().getRace() != Race.ELYOS) {
          return;
        }
        break;
    } 
    
    if (!item.getItemTemplate().isAllowedFor(player.getCommonData().getPlayerClass(), player.getLevel())) {
      return;
    }
    if (QuestEngine.getInstance().onItemUseEvent(new QuestEnv(null, player, Integer.valueOf(0), Integer.valueOf(0)), item)) {
      return;
    }
    
    if (player.isCasting()) {
      return;
    }


    
    Item targetItem = player.getInventory().getItemByObjId(this.targetItemId);
    if (targetItem == null) {
      targetItem = player.getEquipment().getEquippedItemByObjId(this.targetItemId);
    }
    ItemActions itemActions = item.getItemTemplate().getActions();
    ArrayList<AbstractItemAction> actions = new ArrayList<AbstractItemAction>();
    
    if (itemActions == null) {
      return;
    }
    for (AbstractItemAction itemAction : itemActions.getItemActions()) {

      
      if (itemAction.canAct(player, item, targetItem)) {
        actions.add(itemAction);
      }
    } 
    if (actions.size() == 0) {
      return;
    }

    
    if (player.isItemUseDisabled(item.getItemTemplate().getDelayId())) {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_ITEM_CANT_USE_UNTIL_DELAY_TIME);
      
      return;
    } 
    int useDelay = item.getItemTemplate().getDelayTime();
    player.addItemCoolDown(item.getItemTemplate().getDelayId(), System.currentTimeMillis() + useDelay, useDelay / 1000);
    
    for (AbstractItemAction itemAction : actions)
    {
      itemAction.act(player, item, targetItem);
    }
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_USE_ITEM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
