package com.aionemu.gameserver.model.trade;

public class TradePSItem extends TradeItem {
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
