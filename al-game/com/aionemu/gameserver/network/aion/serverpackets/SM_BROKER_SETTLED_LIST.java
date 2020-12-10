package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.BrokerItem;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;
import java.util.List;




























public class SM_BROKER_SETTLED_LIST
  extends AionServerPacket
{
  List<BrokerItem> settledItems;
  private long totalKinah;
  private boolean isIconUpdate;
  private int haveItemsIcon;
  
  public SM_BROKER_SETTLED_LIST(List<BrokerItem> settledItems, long totalKinah) {
    this.isIconUpdate = false;
    this.settledItems = settledItems;
    this.totalKinah = totalKinah;
  }





  
  public SM_BROKER_SETTLED_LIST(boolean haveItems) {
    this.isIconUpdate = true;
    if (haveItems) {
      this.haveItemsIcon = 1;
    } else {
      this.haveItemsIcon = 0;
    } 
  }

  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    if (this.isIconUpdate) {
      
      writeD(buf, 0);
      writeD(buf, this.haveItemsIcon);
      writeH(buf, 1);
      writeD(buf, 0);
      writeC(buf, 1);
      writeH(buf, 0);
    }
    else {
      
      writeQ(buf, this.totalKinah);
      
      writeH(buf, 1);
      writeD(buf, 0);
      writeC(buf, 0);
      
      writeH(buf, this.settledItems.size());
      for (int i = 0; i < this.settledItems.size(); i++) {
        
        writeD(buf, ((BrokerItem)this.settledItems.get(i)).getItemId());
        if (((BrokerItem)this.settledItems.get(i)).isSold()) {
          writeQ(buf, ((BrokerItem)this.settledItems.get(i)).getPrice());
        } else {
          writeQ(buf, 0L);
        }  writeQ(buf, ((BrokerItem)this.settledItems.get(i)).getItemCount());
        writeQ(buf, ((BrokerItem)this.settledItems.get(i)).getItemCount());
        writeD(buf, (int)((BrokerItem)this.settledItems.get(i)).getSettleTime().getTime() / 60000);
        writeH(buf, 0);
        writeD(buf, ((BrokerItem)this.settledItems.get(i)).getItemId());
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
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_BROKER_SETTLED_LIST.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
