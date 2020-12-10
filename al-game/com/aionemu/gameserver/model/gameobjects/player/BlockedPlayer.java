package com.aionemu.gameserver.model.gameobjects.player;

public class BlockedPlayer {
  PlayerCommonData pcd;
  String reason;

  public BlockedPlayer(PlayerCommonData pcd) {
    this(pcd, "");
  }

  public BlockedPlayer(PlayerCommonData pcd, String reason) {
    this.pcd = pcd;
    this.reason = reason;
  }

  public int getObjId() {
    return this.pcd.getPlayerObjId();
  }

  public String getName() {
    return this.pcd.getName();
  }

  public String getReason() {
    return this.reason;
  }

  public synchronized void setReason(String reason) {
    this.reason = reason;
  }
}
