package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.items.ItemId;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.InventoryPacket;
import java.nio.ByteBuffer;
import java.util.List;























public class SM_ADD_ITEMS
  extends InventoryPacket
{
  private List<Item> items;
  private int size;
  
  public SM_ADD_ITEMS(List<Item> items) {
    this.items = items;
    this.size = items.size();
  }






  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeH(buf, 25);
    writeH(buf, this.size);
    for (Item item : this.items) {
      
      writeGeneralInfo(buf, item);
      
      ItemTemplate itemTemplate = item.getItemTemplate();
      
      if (itemTemplate.getTemplateId() == ItemId.KINAH.value()) {
        
        writeKinah(buf, item, true); continue;
      } 
      if (itemTemplate.isWeapon()) {
        
        writeWeaponInfo(buf, item, true); continue;
      } 
      if (itemTemplate.isArmor()) {
        
        writeArmorInfo(buf, item, true, false, false); continue;
      } 
      if (itemTemplate.isStigma()) {
        
        writeStigmaInfo(buf, item);
        
        continue;
      } 
      writeGeneralItemInfo(buf, item, false, false);
      writeC(buf, 0);
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_ADD_ITEMS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
