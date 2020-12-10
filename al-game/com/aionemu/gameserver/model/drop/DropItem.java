package com.aionemu.gameserver.model.drop;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.model.gameobjects.player.Player;

public class DropItem {
  private int index = 0;

  private long count = 0L;

  private DropTemplate dropTemplate;

  private int playerObjId = 0;

  private boolean isFreeForAll = false;

  private long highestValue = 0L;

  private Player winningPlayer = null;

  private boolean isItemWonNotCollected = false;

  private boolean isDistributeItem = false;

  public DropItem(DropTemplate dropTemplate) {
    this.dropTemplate = dropTemplate;
  }

  public void calculateCount(float rate) {
    if (Rnd.get() * 100.0F < this.dropTemplate.getChance() * rate) {
      this.count = Rnd.get(this.dropTemplate.getMin(), this.dropTemplate.getMax());
    }
  }

  public int getIndex() {
    return this.index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public long getCount() {
    return this.count;
  }

  public void setCount(long count) {
    this.count = count;
  }

  public DropTemplate getDropTemplate() {
    return this.dropTemplate;
  }

  public int getPlayerObjId() {
    return this.playerObjId;
  }

  public void setPlayerObjId(int playerObjId) {
    this.playerObjId = playerObjId;
  }

  public void isFreeForAll(boolean isFreeForAll) {
    this.isFreeForAll = isFreeForAll;
  }

  public boolean isFreeForAll() {
    return this.isFreeForAll;
  }

  public long getHighestValue() {
    return this.highestValue;
  }

  public void setHighestValue(long highestValue) {
    this.highestValue = highestValue;
  }

  public void setWinningPlayer(Player winningPlayer) {
    this.winningPlayer = winningPlayer;
  }

  public Player getWinningPlayer() {
    return this.winningPlayer;
  }

  public void isItemWonNotCollected(boolean isItemWonNotCollected) {
    this.isItemWonNotCollected = isItemWonNotCollected;
  }

  public boolean isItemWonNotCollected() {
    return this.isItemWonNotCollected;
  }

  public void isDistributeItem(boolean isDistributeItem) {
    this.isDistributeItem = isDistributeItem;
  }

  public boolean isDistributeItem() {
    return this.isDistributeItem;
  }
}
