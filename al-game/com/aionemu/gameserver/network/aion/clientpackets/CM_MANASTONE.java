package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.itemengine.actions.EnchantItemAction;
import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;























public class CM_MANASTONE
  extends AionClientPacket
{
  private int npcObjId;
  private int slotNum;
  private int actionType;
  private int stoneUniqueId;
  private int targetItemUniqueId;
  private int supplementUniqueId;
  
  public CM_MANASTONE(int opcode) {
    super(opcode);
  }


  
  protected void readImpl() {
    this.actionType = readC();
    readC();
    this.targetItemUniqueId = readD();
    switch (this.actionType) {
      
      case 1:
      case 2:
        this.stoneUniqueId = readD();
        this.supplementUniqueId = readD();
        break;
      case 3:
        this.slotNum = readC();
        readC();
        readH();
        this.npcObjId = readD();
        break;
    } 
  }
  protected void runImpl() {
    EnchantItemAction action;
    Item manastone, targetItem;
    long price;
    AionObject npc = World.getInstance().findAionObject(this.npcObjId);
    Player player = ((AionConnection)getConnection()).getActivePlayer();
    
    switch (this.actionType) {
      
      case 1:
      case 2:
        action = new EnchantItemAction();
        manastone = player.getInventory().getItemByObjId(this.stoneUniqueId);
        targetItem = player.getInventory().getItemByObjId(this.targetItemUniqueId);
        if (targetItem == null)
        {
          targetItem = player.getEquipment().getEquippedItemByObjId(this.targetItemUniqueId);
        }
        if (manastone != null && targetItem != null && action.canAct(player, manastone, targetItem)) {
          
          Item supplement = player.getInventory().getItemByObjId(this.supplementUniqueId);
          action.act(player, manastone, targetItem, supplement);
        } 
        break;
      case 3:
        price = player.getPrices().getPriceForService(500L);
        if (!ItemService.decreaseKinah(player, price)) {
          
          PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.NOT_ENOUGH_KINAH(price));
          return;
        } 
        if (npc != null) {
          
          ItemService.decreaseKinah(player, price);
          ItemService.removeManastone(player, this.targetItemUniqueId, this.slotNum);
        } 
        break;
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_MANASTONE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
