package com.aionemu.gameserver.model.alliance;

import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;

public class PlayerAllianceMember extends AionObject {
  private Player player;
  private String name;
  private int allianceId;
  private PlayerCommonData playerCommonData;

  public PlayerAllianceMember(Player player) {
    super(Integer.valueOf(player.getObjectId()));
    this.player = player;
    this.name = player.getName();
    this.playerCommonData = player.getCommonData();
  }

  public String getName() {
    return this.name;
  }

  public Player getPlayer() {
    return this.player;
  }

  public void onLogin(Player player) {
    this.player = player;
    this.playerCommonData = player.getCommonData();
  }

  public void onDisconnect() {
    this.player = null;
  }

  public boolean isOnline() {
    return (this.player != null);
  }

  public PlayerCommonData getCommonData() {
    return this.playerCommonData;
  }

  public int getAllianceId() {
    return this.allianceId;
  }

  public void setAllianceId(int allianceId) {
    this.allianceId = allianceId;
  }
}
