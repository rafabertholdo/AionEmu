package com.aionemu.gameserver.model.gameobjects;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import java.util.ArrayList;
import java.util.List;

public class DropNpc {
  private List<Integer> allowedList = new ArrayList<Integer>();
  private List<Player> inRangePlayers = new ArrayList<Player>();
  private List<Player> playerStatus = new ArrayList<Player>();
  private Player lootingPlayer = null;
  private int distributionType = 0;
  private boolean inUse = false;
  private int currentIndex = 0;
  private int groupSize = 0;

  public DropNpc(List<Integer> allowedList) {
    this.allowedList = allowedList;
  }

  public void setFreeLooting() {
    this.allowedList = null;
  }

  public boolean containsKey(int playerObjId) {
    if (this.allowedList == null)
      return true;
    return this.allowedList.contains(Integer.valueOf(playerObjId));
  }

  public void setBeingLooted(Player player) {
    this.lootingPlayer = player;
  }

  public Player getBeingLooted() {
    return this.lootingPlayer;
  }

  public boolean isBeingLooted() {
    return (this.lootingPlayer != null);
  }

  public void setDistributionType(int distributionType) {
    this.distributionType = distributionType;
  }

  public int getDistributionType() {
    return this.distributionType;
  }

  public void isInUse(boolean inUse) {
    this.inUse = inUse;
  }

  public boolean isInUse() {
    return this.inUse;
  }

  public void setCurrentIndex(int currentIndex) {
    this.currentIndex = currentIndex;
  }

  public int getCurrentIndex() {
    return this.currentIndex;
  }

  public void setGroupSize(int groupSize) {
    this.groupSize = groupSize;
  }

  public int getGroupSize() {
    return this.groupSize;
  }

  public void setInRangePlayers(List<Player> inRangePlayers) {
    this.inRangePlayers = inRangePlayers;
  }

  public List<Player> getInRangePlayers() {
    return this.inRangePlayers;
  }

  public void addPlayerStatus(Player player) {
    this.playerStatus.add(player);
  }

  public void delPlayerStatus(Player player) {
    this.playerStatus.remove(player);
  }

  public List<Player> getPlayerStatus() {
    return this.playerStatus;
  }

  public boolean containsPlayerStatus(Player player) {
    return this.playerStatus.contains(player);
  }
}
