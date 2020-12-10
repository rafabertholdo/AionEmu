package com.aionemu.gameserver.model.gameobjects;

import com.aionemu.gameserver.model.broker.BrokerRace;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Comparator;

public class BrokerItem implements Comparable<BrokerItem> {
  private Item item;
  private int itemId;
  private int itemUniqueId;
  private long itemCount;
  private long price;
  private String seller;
  private int sellerId;
  private BrokerRace itemBrokerRace;
  private boolean isSold;
  private boolean isSettled;
  private Timestamp expireTime;
  private Timestamp settleTime;
  PersistentState state;

  public BrokerItem(Item item, long price, String seller, int sellerId, BrokerRace itemBrokerRace) {
    this.item = item;
    this.itemId = item.getItemTemplate().getTemplateId();
    this.itemUniqueId = item.getObjectId();
    this.itemCount = item.getItemCount();
    this.price = price;
    this.seller = seller;
    this.sellerId = sellerId;
    this.itemBrokerRace = itemBrokerRace;
    this.isSold = false;
    this.isSettled = false;
    this.expireTime = new Timestamp(Calendar.getInstance().getTimeInMillis() + 691200000L);
    this.settleTime = new Timestamp(Calendar.getInstance().getTimeInMillis());

    this.state = PersistentState.NEW;
  }

  public BrokerItem(Item item, int itemId, int itemUniqueId, long itemCount, long price, String seller, int sellerId,
      BrokerRace itemBrokerRace, boolean isSold, boolean isSettled, Timestamp expireTime, Timestamp settleTime) {
    this.item = item;
    this.itemId = itemId;
    this.itemUniqueId = itemUniqueId;
    this.price = price;
    this.seller = seller;
    this.sellerId = sellerId;
    this.itemBrokerRace = itemBrokerRace;
    this.itemCount = itemCount;
    if (item == null) {

      this.isSold = true;
      this.isSettled = true;

    } else {

      this.isSold = isSold;
      this.isSettled = isSettled;
    }

    this.expireTime = expireTime;
    this.settleTime = settleTime;

    this.state = PersistentState.NOACTION;
  }

  public Item getItem() {
    return this.item;
  }

  public void removeItem() {
    this.item = null;
    this.isSold = true;
    this.isSettled = true;
    this.settleTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
  }

  public int getItemId() {
    return this.itemId;
  }

  public int getItemUniqueId() {
    return this.itemUniqueId;
  }

  public long getPrice() {
    return this.price;
  }

  public String getSeller() {
    return this.seller;
  }

  public int getSellerId() {
    return this.sellerId;
  }

  public BrokerRace getItemBrokerRace() {
    return this.itemBrokerRace;
  }

  public boolean isSold() {
    return this.isSold;
  }

  public void setPersistentState(PersistentState persistentState) {
    switch (persistentState) {

      case DELETED:
        if (this.state == PersistentState.NEW) {
          this.state = PersistentState.NOACTION;
        } else {
          this.state = PersistentState.DELETED;
        }
        return;
      case UPDATE_REQUIRED:
        if (this.state == PersistentState.NEW)
          return;
        break;
    }
    this.state = persistentState;
  }

  public PersistentState getPersistentState() {
    return this.state;
  }

  public boolean isSettled() {
    return this.isSettled;
  }

  public void setSettled() {
    this.isSettled = true;
    this.settleTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
  }

  public Timestamp getExpireTime() {
    return this.expireTime;
  }

  public Timestamp getSettleTime() {
    return this.settleTime;
  }

  public long getItemCount() {
    return this.itemCount;
  }

  private int getItemLevel() {
    return this.item.getItemTemplate().getLevel();
  }

  private long getPiecePrice() {
    return getPrice() / getItemCount();
  }

  private String getItemName() {
    return this.item.getItemName();
  }

  public int compareTo(BrokerItem o) {
    return (this.itemUniqueId > o.getItemUniqueId()) ? 1 : -1;
  }

  static Comparator<BrokerItem> NAME_SORT_ASC = new Comparator<BrokerItem>() {

    public int compare(BrokerItem o1, BrokerItem o2) {
      if (o1 == null || o2 == null)
        return BrokerItem.comparePossiblyNull((T) o1, (T) o2);
      return o1.getItemName().compareTo(o2.getItemName());
    }
  };

  static Comparator<BrokerItem> NAME_SORT_DESC = new Comparator<BrokerItem>() {

    public int compare(BrokerItem o1, BrokerItem o2) {
      if (o1 == null || o2 == null)
        return BrokerItem.comparePossiblyNull((T) o1, (T) o2);
      return o1.getItemName().compareTo(o2.getItemName());
    }
  };

  static Comparator<BrokerItem> PRICE_SORT_ASC = new Comparator<BrokerItem>() {

    public int compare(BrokerItem o1, BrokerItem o2) {
      if (o1 == null || o2 == null)
        return BrokerItem.comparePossiblyNull((T) o1, (T) o2);
      if (o1.getPrice() == o2.getPrice())
        return 0;
      return (o1.getPrice() > o2.getPrice()) ? 1 : -1;
    }
  };

  static Comparator<BrokerItem> PRICE_SORT_DESC = new Comparator<BrokerItem>() {

    public int compare(BrokerItem o1, BrokerItem o2) {
      if (o1 == null || o2 == null)
        return BrokerItem.comparePossiblyNull((T) o1, (T) o2);
      if (o1.getPrice() == o2.getPrice())
        return 0;
      return (o1.getPrice() > o2.getPrice()) ? -1 : 1;
    }
  };

  static Comparator<BrokerItem> PIECE_PRICE_SORT_ASC = new Comparator<BrokerItem>() {

    public int compare(BrokerItem o1, BrokerItem o2) {
      if (o1 == null || o2 == null)
        return BrokerItem.comparePossiblyNull((T) o1, (T) o2);
      if (o1.getPiecePrice() == o2.getPiecePrice())
        return 0;
      return (o1.getPiecePrice() > o2.getPiecePrice()) ? 1 : -1;
    }
  };

  static Comparator<BrokerItem> PIECE_PRICE_SORT_DESC = new Comparator<BrokerItem>() {

    public int compare(BrokerItem o1, BrokerItem o2) {
      if (o1 == null || o2 == null)
        return BrokerItem.comparePossiblyNull((T) o1, (T) o2);
      if (o1.getPiecePrice() == o2.getPiecePrice())
        return 0;
      return (o1.getPiecePrice() > o2.getPiecePrice()) ? -1 : 1;
    }
  };

  static Comparator<BrokerItem> LEVEL_SORT_ASC = new Comparator<BrokerItem>() {

    public int compare(BrokerItem o1, BrokerItem o2) {
      if (o1 == null || o2 == null)
        return BrokerItem.comparePossiblyNull((T) o1, (T) o2);
      if (o1.getItemLevel() == o2.getItemLevel())
        return 0;
      return (o1.getItemLevel() > o2.getItemLevel()) ? 1 : -1;
    }
  };

  static Comparator<BrokerItem> LEVEL_SORT_DESC = new Comparator<BrokerItem>() {

    public int compare(BrokerItem o1, BrokerItem o2) {
      if (o1 == null || o2 == null)
        return BrokerItem.comparePossiblyNull((T) o1, (T) o2);
      if (o1.getItemLevel() == o2.getItemLevel())
        return 0;
      return (o1.getItemLevel() > o2.getItemLevel()) ? -1 : 1;
    }
  };

  private static <T extends Comparable<T>> int comparePossiblyNull(T aThis, T aThat) {
    int result = 0;
    if (aThis == null && aThat != null) {

      result = -1;
    } else if (aThis != null && aThat == null) {

      result = 1;
    }
    return result;
  }

  public static Comparator<BrokerItem> getComparatoryByType(int sortType) {
    switch (sortType) {

      case 0:
        return NAME_SORT_ASC;
      case 1:
        return NAME_SORT_DESC;
      case 2:
        return LEVEL_SORT_ASC;
      case 3:
        return LEVEL_SORT_DESC;
      case 4:
        return PRICE_SORT_ASC;
      case 5:
        return PRICE_SORT_DESC;
      case 6:
        return PIECE_PRICE_SORT_ASC;
      case 7:
        return PIECE_PRICE_SORT_DESC;
    }
    throw new IllegalArgumentException("Illegal sort type for broker items");
  }
}
