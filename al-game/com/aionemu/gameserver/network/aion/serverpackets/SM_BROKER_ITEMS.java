package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.BrokerItem;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.stats.modifiers.SimpleModifier;
import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
import com.aionemu.gameserver.model.items.GodStone;
import com.aionemu.gameserver.model.items.ManaStone;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;
import java.util.Set;






















public class SM_BROKER_ITEMS
  extends AionServerPacket
{
  private BrokerItem[] brokerItems;
  private int itemsCount;
  private int startPage;
  
  public SM_BROKER_ITEMS(BrokerItem[] brokerItems, int itemsCount, int startPage) {
    this.brokerItems = brokerItems;
    this.itemsCount = itemsCount;
    this.startPage = startPage;
  }



  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.itemsCount);
    writeC(buf, 0);
    writeH(buf, this.startPage);
    writeH(buf, this.brokerItems.length);
    
    for (BrokerItem item : this.brokerItems) {
      
      if (item.getItem().getItemTemplate().isArmor() || item.getItem().getItemTemplate().isWeapon()) {
        writeArmorWeaponInfo(buf, item);
      } else {
        writeCommonInfo(buf, item);
      } 
    } 
  }
  
  private void writeArmorWeaponInfo(ByteBuffer buf, BrokerItem item) {
    writeD(buf, item.getItem().getObjectId());
    writeD(buf, item.getItem().getItemTemplate().getTemplateId());
    writeQ(buf, item.getPrice());
    writeQ(buf, item.getItem().getItemCount());
    writeC(buf, 0);
    writeC(buf, item.getItem().getEnchantLevel());
    writeD(buf, item.getItem().getItemSkinTemplate().getTemplateId());
    writeC(buf, 0);
    
    writeItemStones(buf, item.getItem());
    
    GodStone godStone = item.getItem().getGodStone();
    writeD(buf, (godStone == null) ? 0 : godStone.getItemId());
    
    writeC(buf, 0);
    writeD(buf, 0);
    writeD(buf, 0);
    writeS(buf, item.getSeller());
    writeS(buf, "");
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



  
  private void writeCommonInfo(ByteBuffer buf, BrokerItem item) {
    writeD(buf, item.getItem().getObjectId());
    writeD(buf, item.getItem().getItemTemplate().getTemplateId());
    writeQ(buf, item.getPrice());
    writeQ(buf, item.getItem().getItemCount());
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
    writeS(buf, item.getSeller());
    writeS(buf, "");
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_BROKER_ITEMS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
