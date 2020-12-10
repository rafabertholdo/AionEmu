package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;
import java.util.List;






















public class SM_VIEW_PLAYER_DETAILS
  extends AionServerPacket
{
  private List<Item> items;
  private int size;
  private int targetObjId;
  
  public SM_VIEW_PLAYER_DETAILS(int targetObjId, List<Item> items) {
    this.items = items;
    this.size = items.size();
  }




  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.targetObjId);
    writeC(buf, 11);
    writeC(buf, this.size);
    writeC(buf, 0);
    writeD(buf, 0);
    for (Item item : this.items) {

      
      writeD(buf, item.getItemTemplate().getTemplateId());
      writeH(buf, 36);
      writeD(buf, item.getItemTemplate().getNameId());
      writeH(buf, 0);
      
      writeH(buf, 36);
      writeC(buf, 4);
      writeC(buf, 1);
      writeH(buf, 0);
      writeH(buf, 0);
      writeC(buf, 0);
      
      writeH(buf, 0);
      writeC(buf, 6);
      writeH(buf, item.getEquipmentSlot());
      writeH(buf, 0);
      writeC(buf, 0);
      writeH(buf, 62);
      writeH(buf, (int)item.getItemCount());


      
      writeD(buf, 0);
      writeD(buf, 0);
      writeD(buf, 0);
      writeD(buf, 0);
      writeD(buf, 0);
      writeC(buf, 0);
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_VIEW_PLAYER_DETAILS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
