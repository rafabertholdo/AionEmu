package com.aionemu.gameserver.model.trade;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import java.util.HashMap;
import java.util.Map;

public class Exchange {
  private Player activeplayer;
  private Player targetPlayer;
  private boolean confirmed;
  private boolean locked;
  private long kinahCount;
  private Map<Integer, ExchangeItem> items = new HashMap<Integer, ExchangeItem>();

  public Exchange(Player activeplayer, Player targetPlayer) {
    this.activeplayer = activeplayer;
    this.targetPlayer = targetPlayer;
  }

  public void confirm() {
    this.confirmed = true;
  }

  public boolean isConfirmed() {
    return this.confirmed;
  }

  public void lock() {
    this.locked = true;
  }

  public boolean isLocked() {
    return this.locked;
  }

  public void addItem(int parentItemObjId, ExchangeItem exchangeItem) {
    this.items.put(Integer.valueOf(parentItemObjId), exchangeItem);
  }

  public void addKinah(long countToAdd) {
    this.kinahCount += countToAdd;
  }

  public Player getActiveplayer() {
    return this.activeplayer;
  }

  public Player getTargetPlayer() {
    return this.targetPlayer;
  }

  public long getKinahCount() {
    return this.kinahCount;
  }

  public Map<Integer, ExchangeItem> getItems() {
    return this.items;
  }

  public boolean isExchangeListFull() {
    return (this.items.size() > 18);
  }

  public void clear() {
    this.activeplayer = null;
    this.targetPlayer = null;
    this.items.clear();
  }
}
