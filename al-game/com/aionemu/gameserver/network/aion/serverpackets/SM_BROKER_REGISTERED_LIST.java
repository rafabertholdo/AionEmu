package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.BrokerItem;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;






















public class SM_BROKER_REGISTERED_LIST
  extends AionServerPacket
{
  List<BrokerItem> registeredItems;
  
  public SM_BROKER_REGISTERED_LIST(List<BrokerItem> items) {
    this.registeredItems = items;
  }


  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, 0);
    writeH(buf, this.registeredItems.size());
    for (BrokerItem item : this.registeredItems) {
      
      writeD(buf, item.getItemUniqueId());
      writeD(buf, item.getItemId());
      writeQ(buf, item.getPrice());
      writeQ(buf, item.getItem().getItemCount());
      writeQ(buf, item.getItem().getItemCount());
      Timestamp currentTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
      int daysLeft = Math.round((float)((item.getExpireTime().getTime() - currentTime.getTime()) / 86400000L));
      writeH(buf, daysLeft);
      writeC(buf, 0);
      writeD(buf, item.getItemId());
      writeD(buf, 0);
      writeD(buf, 0);
      writeD(buf, 0);
      writeD(buf, 0);
      writeD(buf, 0);
      writeD(buf, 0);
      writeD(buf, 0);
      writeD(buf, 0);
      writeH(buf, 0);
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_BROKER_REGISTERED_LIST.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
