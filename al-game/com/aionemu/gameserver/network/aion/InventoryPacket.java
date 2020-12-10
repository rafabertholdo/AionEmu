package com.aionemu.gameserver.network.aion;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.stats.modifiers.SimpleModifier;
import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
import com.aionemu.gameserver.model.items.GodStone;
import com.aionemu.gameserver.model.items.ItemSlot;
import com.aionemu.gameserver.model.items.ManaStone;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import java.nio.ByteBuffer;
import java.util.Set;



























public abstract class InventoryPacket
  extends AionServerPacket
{
  protected void writeGeneralInfo(ByteBuffer buf, Item item) {
    writeD(buf, item.getObjectId());
    ItemTemplate itemTemplate = item.getItemTemplate();
    writeD(buf, itemTemplate.getTemplateId());
    writeH(buf, 36);
    writeD(buf, itemTemplate.getNameId());
    writeH(buf, 0);
  }

  
  protected void writeMailGeneralInfo(ByteBuffer buf, Item item) {
    writeD(buf, item.getObjectId());
    ItemTemplate itemTemplate = item.getItemTemplate();
    writeD(buf, itemTemplate.getTemplateId());
    writeD(buf, 1);
    writeD(buf, 0);
    writeH(buf, 36);
    writeD(buf, itemTemplate.getNameId());
    writeH(buf, 0);
  }






  
  protected void writeGeneralItemInfo(ByteBuffer buf, Item item, boolean privateStore, boolean mail) {
    writeH(buf, 22);
    writeC(buf, 0);
    writeH(buf, item.getItemMask());
    writeQ(buf, item.getItemCount());
    writeD(buf, 0);
    writeD(buf, 0);
    if (!privateStore)
      writeH(buf, 0); 
    writeC(buf, 0);
    if (!mail) {
      writeH(buf, item.getEquipmentSlot());
    }
  }
  
  protected void writeStigmaInfo(ByteBuffer buf, Item item) {
    writeH(buf, 325);
    writeC(buf, 6);
    if (item.isEquipped()) {
      writeD(buf, item.getEquipmentSlot());
    } else {
      writeD(buf, 0);
    }  writeC(buf, 7);
    writeH(buf, 702);
    writeD(buf, 0);
    writeH(buf, 0);
    writeD(buf, 60);
    
    writeD(buf, 0);
    writeD(buf, 0);
    
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 1);
    writeD(buf, 0);
    
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeH(buf, 0);
    writeH(buf, 11);

    
    writeC(buf, 0);
    writeD(buf, item.getItemTemplate().getTemplateId());
    
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeC(buf, 0);
    
    writeD(buf, 82750);
    
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeC(buf, 0);
    
    writeC(buf, 34);
    writeH(buf, 0);
  }






  
  protected void writeKinah(ByteBuffer buf, Item item, boolean isInventory) {
    writeH(buf, 22);
    writeC(buf, 0);
    writeH(buf, item.getItemMask());
    writeQ(buf, item.getItemCount());
    writeD(buf, 0);
    writeD(buf, 0);
    writeH(buf, 0);
    writeC(buf, 0);
    writeH(buf, 255);
    if (isInventory) {
      writeC(buf, 0);
    }
  }







  
  protected void writeWeaponInfo(ByteBuffer buf, Item item, boolean isInventory) {
    writeWeaponInfo(buf, item, isInventory, false, false, false);
  }







  
  protected void writeWeaponInfo(ByteBuffer buf, Item item, boolean isInventory, boolean isWeaponSwitch, boolean privateStore, boolean mail) {
    int itemSlotId = item.getEquipmentSlot();
    
    if (isWeaponSwitch) {
      writeH(buf, 5);
    } else {
      writeH(buf, 75);
    } 
    writeC(buf, 6);
    writeD(buf, item.isEquipped() ? itemSlotId : 0);
    
    if (!isWeaponSwitch) {
      
      writeC(buf, 1);
      writeD(buf, ((ItemSlot)ItemSlot.getSlotsFor(item.getItemTemplate().getItemSlot()).get(0)).getSlotIdMask());
      writeD(buf, 2);
      writeC(buf, 11);
      writeC(buf, item.isSoulBound() ? 1 : 0);
      writeC(buf, item.getEnchantLevel());
      writeD(buf, item.getItemSkinTemplate().getTemplateId());
      writeC(buf, 0);
      
      writeItemStones(buf, item);
      
      GodStone godStone = item.getGodStone();
      writeD(buf, (godStone == null) ? 0 : godStone.getItemId());
      writeC(buf, 0);
      
      writeD(buf, 0);
      
      writeD(buf, 0);
      writeC(buf, 0);
      
      writeH(buf, item.getItemMask());
      writeQ(buf, item.getItemCount());
      writeD(buf, 0);
      writeD(buf, 0);
      if (!privateStore)
        writeH(buf, 0); 
      writeC(buf, 0);
      if (!mail)
        writeH(buf, item.isEquipped() ? 255 : item.getEquipmentSlot()); 
      if (isInventory) {
        writeC(buf, 0);
      }
    } 
  }





  
  private void writeItemStones(ByteBuffer buf, Item item) {
    int count = 0;
    
    if (item.hasManaStones()) {
      
      Set<ManaStone> itemStones = item.getItemStones();
      
      for (ManaStone itemStone : itemStones) {
        
        if (count == 6) {
          break;
        }
        StatModifier modifier = itemStone.getFirstModifier();
        if (modifier != null) {
          
          count++;
          writeC(buf, modifier.getStat().getItemStoneMask());
        } 
      } 
      writeB(buf, new byte[6 - count]);
      count = 0;
      for (ManaStone itemStone : itemStones) {
        
        if (count == 6) {
          break;
        }
        StatModifier modifier = itemStone.getFirstModifier();
        if (modifier != null) {
          
          count++;
          writeH(buf, ((SimpleModifier)modifier).getValue());
        } 
      } 
      writeB(buf, new byte[(6 - count) * 2]);
    }
    else {
      
      writeB(buf, new byte[18]);
    } 
  }








  
  protected void writeArmorInfo(ByteBuffer buf, Item item, boolean isInventory, boolean privateStore, boolean mail) {
    int itemSlotId = item.getEquipmentSlot();
    writeH(buf, 79);
    writeC(buf, 6);
    writeD(buf, item.isEquipped() ? itemSlotId : 0);
    writeC(buf, 2);
    writeD(buf, ((ItemSlot)ItemSlot.getSlotsFor(item.getItemTemplate().getItemSlot()).get(0)).getSlotIdMask());
    writeD(buf, 0);
    writeD(buf, 0);
    writeC(buf, 11);
    writeC(buf, item.isSoulBound() ? 1 : 0);
    writeC(buf, item.getEnchantLevel());
    writeD(buf, item.getItemSkinTemplate().getTemplateId());
    
    writeC(buf, 0);
    
    writeItemStones(buf, item);
    
    writeC(buf, 0);
    writeD(buf, item.getItemColor());
    writeD(buf, 0);
    
    writeD(buf, 0);
    writeC(buf, 0);
    
    writeH(buf, item.getItemMask());
    writeQ(buf, item.getItemCount());
    writeD(buf, 0);
    writeD(buf, 0);
    if (!privateStore)
      writeH(buf, 0); 
    writeC(buf, 0);
    if (!mail)
      writeH(buf, item.isEquipped() ? 255 : item.getEquipmentSlot()); 
    if (isInventory)
      writeC(buf, 1); 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\InventoryPacket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
