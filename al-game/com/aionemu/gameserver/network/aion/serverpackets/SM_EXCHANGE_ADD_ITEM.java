package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.items.ItemId;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.InventoryPacket;
import java.nio.ByteBuffer;























public class SM_EXCHANGE_ADD_ITEM
  extends InventoryPacket
{
  private int action;
  private Item item;
  
  public SM_EXCHANGE_ADD_ITEM(int action, Item item) {
    this.action = action;
    this.item = item;
  }



  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeC(buf, this.action);
    
    writeGeneralInfo(buf, this.item);
    
    ItemTemplate itemTemplate = this.item.getItemTemplate();
    
    if (itemTemplate.getTemplateId() == ItemId.KINAH.value()) {
      
      writeKinah(buf, this.item, true);
    }
    else if (itemTemplate.isWeapon()) {
      
      writeWeaponInfo(buf, this.item, true);
    }
    else if (itemTemplate.isArmor()) {
      
      writeArmorInfo(buf, this.item, true, false, false);
    }
    else {
      
      writeGeneralItemInfo(buf, this.item, false, false);
      writeC(buf, 0);
    } 
  }


  
  protected void writeGeneralInfo(ByteBuffer buf, Item item) {
    ItemTemplate itemTemplate = item.getItemTemplate();
    writeD(buf, itemTemplate.getTemplateId());
    writeD(buf, item.getObjectId());
    writeH(buf, 36);
    writeD(buf, itemTemplate.getNameId());
    writeH(buf, 0);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_EXCHANGE_ADD_ITEM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
