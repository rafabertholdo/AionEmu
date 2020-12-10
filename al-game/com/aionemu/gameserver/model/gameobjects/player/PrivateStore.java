package com.aionemu.gameserver.model.gameobjects.player;

import com.aionemu.gameserver.model.trade.TradePSItem;
import java.util.Iterator;
import java.util.LinkedHashMap;


























public class PrivateStore
{
  private Player owner;
  private LinkedHashMap<Integer, TradePSItem> items;
  private String storeMessage;
  
  public PrivateStore(Player owner) {
    this.owner = owner;
    this.items = new LinkedHashMap<Integer, TradePSItem>();
  }






  
  public Player getOwner() {
    return this.owner;
  }






  
  public LinkedHashMap<Integer, TradePSItem> getSoldItems() {
    return this.items;
  }







  
  public void addItemToSell(int itemObjId, TradePSItem tradeItem) {
    this.items.put(Integer.valueOf(itemObjId), tradeItem);
  }






  
  public void removeItem(int itemObjId) {
    if (this.items.containsKey(Integer.valueOf(itemObjId))) {
      
      LinkedHashMap<Integer, TradePSItem> newItems = new LinkedHashMap<Integer, TradePSItem>();
      for (Iterator<Integer> i$ = this.items.keySet().iterator(); i$.hasNext(); ) { int itemObjIds = ((Integer)i$.next()).intValue();
        
        if (itemObjId != itemObjIds)
          newItems.put(Integer.valueOf(itemObjIds), this.items.get(Integer.valueOf(itemObjIds)));  }
      
      this.items = newItems;
    } 
  }





  
  public TradePSItem getTradeItemById(int itemObjId) {
    if (this.items.containsKey(Integer.valueOf(itemObjId)))
      return this.items.get(Integer.valueOf(itemObjId)); 
    return null;
  }





  
  public void setStoreMessage(String storeMessage) {
    this.storeMessage = storeMessage;
  }




  
  public String getStoreMessage() {
    return this.storeMessage;
  }

  
  public void clear() {
    this.owner = null;
    this.items.clear();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\PrivateStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
