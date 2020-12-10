package com.aionemu.gameserver.model.account;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.PlayerAppearance;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.model.legion.Legion;
import com.aionemu.gameserver.model.legion.LegionMember;
import java.sql.Timestamp;
import java.util.List;

public class PlayerAccountData {
  private PlayerCommonData playerCommonData;
  private PlayerAppearance appereance;
  private List<Item> equipment;
  private Timestamp creationDate;
  private Timestamp deletionDate;
  private LegionMember legionMember;

  public PlayerAccountData(PlayerCommonData playerCommonData, PlayerAppearance appereance, List<Item> equipment,
      LegionMember legionMember) {
    this.playerCommonData = playerCommonData;
    this.appereance = appereance;
    this.equipment = equipment;
    this.legionMember = legionMember;
  }

  public Timestamp getCreationDate() {
    return this.creationDate;
  }

  public void setDeletionDate(Timestamp deletionDate) {
    this.deletionDate = deletionDate;
  }

  public Timestamp getDeletionDate() {
    return this.deletionDate;
  }

  public int getDeletionTimeInSeconds() {
    return (this.deletionDate == null) ? 0 : (int) (this.deletionDate.getTime() / 1000L);
  }

  public PlayerCommonData getPlayerCommonData() {
    return this.playerCommonData;
  }

  public void setPlayerCommonData(PlayerCommonData playerCommonData) {
    this.playerCommonData = playerCommonData;
  }

  public PlayerAppearance getAppereance() {
    return this.appereance;
  }

  public void setCreationDate(Timestamp creationDate) {
    this.creationDate = creationDate;
  }

  public Legion getLegion() {
    return this.legionMember.getLegion();
  }

  public boolean isLegionMember() {
    return (this.legionMember != null);
  }

  public List<Item> getEquipment() {
    return this.equipment;
  }

  public void setEquipment(List<Item> equipment) {
    this.equipment = equipment;
  }
}
