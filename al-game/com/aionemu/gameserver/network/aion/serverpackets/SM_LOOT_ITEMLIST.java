package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.drop.DropItem;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

























public class SM_LOOT_ITEMLIST
  extends AionServerPacket
{
  private int targetObjectId;
  private DropItem[] dropItems;
  private int size;
  
  public SM_LOOT_ITEMLIST(int targetObjectId, Set<DropItem> dropItems, Player player) {
    this.targetObjectId = targetObjectId;
    Set<DropItem> tmp = new HashSet<DropItem>();
    for (DropItem item : dropItems) {
      
      if (item.getPlayerObjId() == 0 || player.getObjectId() == item.getPlayerObjId())
        tmp.add(item); 
    } 
    this.dropItems = tmp.<DropItem>toArray(new DropItem[tmp.size()]);
    this.size = this.dropItems.length;
  }





  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.targetObjectId);
    writeC(buf, this.size);
    
    for (DropItem dropItem : this.dropItems) {
      
      writeC(buf, dropItem.getIndex());
      writeD(buf, dropItem.getDropTemplate().getItemId());
      writeH(buf, (int)dropItem.getCount());
      writeD(buf, 0);
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_LOOT_ITEMLIST.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
