package com.aionemu.gameserver.model.broker;

import com.aionemu.gameserver.model.gameobjects.BrokerItem;





















public class BrokerPlayerCache
{
  private BrokerItem[] brokerListCache = new BrokerItem[0];
  
  private int brokerMaskCache;
  
  private int brokerSoftTypeCache;
  
  private int brokerStartPageCache;

  
  public BrokerItem[] getBrokerListCache() {
    return this.brokerListCache;
  }





  
  public void setBrokerListCache(BrokerItem[] brokerListCache) {
    this.brokerListCache = brokerListCache;
  }




  
  public int getBrokerMaskCache() {
    return this.brokerMaskCache;
  }





  
  public void setBrokerMaskCache(int brokerMaskCache) {
    this.brokerMaskCache = brokerMaskCache;
  }




  
  public int getBrokerSortTypeCache() {
    return this.brokerSoftTypeCache;
  }





  
  public void setBrokerSortTypeCache(int brokerSoftTypeCache) {
    this.brokerSoftTypeCache = brokerSoftTypeCache;
  }




  
  public int getBrokerStartPageCache() {
    return this.brokerStartPageCache;
  }





  
  public void setBrokerStartPageCache(int brokerStartPageCache) {
    this.brokerStartPageCache = brokerStartPageCache;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\broker\BrokerPlayerCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
