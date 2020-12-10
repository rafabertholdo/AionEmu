package com.aionemu.gameserver.model.trade;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;























public class TradeList
{
  private int sellerObjId;
  private List<TradeItem> tradeItems = new ArrayList<TradeItem>();
  
  private long requiredKinah;
  
  private int requiredAp;
  
  private Map<Integer, Integer> requiredItems = new HashMap<Integer, Integer>();






  
  public void addBuyItem(int itemId, long count) {
    ItemTemplate itemTemplate = DataManager.ITEM_DATA.getItemTemplate(itemId);
    if (itemTemplate != null) {
      
      TradeItem tradeItem = new TradeItem(itemId, count);
      tradeItem.setItemTemplate(itemTemplate);
      this.tradeItems.add(tradeItem);
    } 
  }





  
  public void addPSItem(int itemId, long count) {
    TradeItem tradeItem = new TradeItem(itemId, count);
    this.tradeItems.add(tradeItem);
  }





  
  public void addSellItem(int itemObjId, long count) {
    TradeItem tradeItem = new TradeItem(itemObjId, count);
    this.tradeItems.add(tradeItem);
  }




  
  public boolean calculateBuyListPrice(Player player, int modifier) {
    long availableKinah = player.getInventory().getKinahItem().getItemCount();
    this.requiredKinah = 0L;

    
    for (TradeItem tradeItem : this.tradeItems)
    {
      this.requiredKinah += player.getPrices().getKinahForBuy(tradeItem.getItemTemplate().getPrice()) * tradeItem.getCount() * modifier / 100L;
    }
    
    this.requiredKinah = player.getPrices().getKinahForBuy(this.requiredKinah);
    
    return (availableKinah >= this.requiredKinah);
  }




  
  public boolean calculateAbyssBuyListPrice(Player player) {
    int ap = player.getAbyssRank().getAp();
    
    this.requiredAp = 0;
    this.requiredItems.clear();
    
    for (TradeItem tradeItem : this.tradeItems) {
      
      this.requiredAp = (int)(this.requiredAp + tradeItem.getItemTemplate().getAbyssPoints() * tradeItem.getCount());
      int itemId = tradeItem.getItemTemplate().getAbyssItem();
      
      Integer alreadyAddedCount = this.requiredItems.get(Integer.valueOf(itemId));
      if (alreadyAddedCount == null) {
        this.requiredItems.put(Integer.valueOf(itemId), Integer.valueOf(tradeItem.getItemTemplate().getAbyssItemCount())); continue;
      } 
      this.requiredItems.put(Integer.valueOf(itemId), Integer.valueOf(alreadyAddedCount.intValue() + tradeItem.getItemTemplate().getAbyssItemCount()));
    } 
    
    if (ap < this.requiredAp) {
      return false;
    }
    for (Integer itemId : this.requiredItems.keySet()) {
      
      long count = player.getInventory().getItemCountByItemId(itemId.intValue());
      if (count < ((Integer)this.requiredItems.get(itemId)).intValue()) {
        return false;
      }
    } 
    return true;
  }





  
  public List<TradeItem> getTradeItems() {
    return this.tradeItems;
  }

  
  public int size() {
    return this.tradeItems.size();
  }




  
  public int getSellerObjId() {
    return this.sellerObjId;
  }




  
  public void setSellerObjId(int npcObjId) {
    this.sellerObjId = npcObjId;
  }




  
  public int getRequiredAp() {
    return this.requiredAp;
  }




  
  public long getRequiredKinah() {
    return this.requiredKinah;
  }




  
  public Map<Integer, Integer> getRequiredItems() {
    return this.requiredItems;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\trade\TradeList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
