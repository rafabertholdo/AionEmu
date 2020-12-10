package com.aionemu.gameserver.model.trade;

























public class TradePSItem
  extends TradeItem
{
  private int itemObjId;
  private long price;
  
  public TradePSItem(int itemObjId, int itemId, long count, long price) {
    super(itemId, count);
    setPrice(price);
    setItemObjId(itemObjId);
  }





  
  public void setPrice(long price) {
    this.price = price;
  }




  
  public long getPrice() {
    return this.price;
  }





  
  public void setItemObjId(int itemObjId) {
    this.itemObjId = itemObjId;
  }




  
  public int getItemObjId() {
    return this.itemObjId;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\trade\TradePSItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
