package com.aionemu.gameserver.model.siege;

import com.aionemu.gameserver.model.templates.siegelocation.SiegeLocationTemplate;

public class SiegeLocation {
  public static final int INVULNERABLE = 0;
  public static final int VULNERABLE = 1;
  private int locationId;
  private int worldId;
  private SiegeRace siegeRace = SiegeRace.BALAUR;
  private int legionId = 0;

  private boolean isVulnerable = false;
  private int nextState = 0;

  public SiegeLocation() {
  }

  public SiegeLocation(SiegeLocationTemplate template) {
    this.locationId = template.getId();
    this.worldId = template.getWorldId();
  }

  public int getLocationId() {
    return this.locationId;
  }

  public int getWorldId() {
    return this.worldId;
  }

  public SiegeRace getRace() {
    return this.siegeRace;
  }

  public void setRace(SiegeRace siegeRace) {
    this.siegeRace = siegeRace;
  }

  public int getLegionId() {
    return this.legionId;
  }

  public void setLegionId(int legionId) {
    this.legionId = legionId;
  }

  public int getNextState() {
    return this.nextState;
  }

  public void setNextState(Integer nextState) {
    this.nextState = nextState.intValue();
  }

  public boolean isVulnerable() {
    return this.isVulnerable;
  }

  public void setVulnerable(boolean value) {
    this.isVulnerable = value;
  }

  public int getInfluenceValue() {
    return 0;
  }
}
