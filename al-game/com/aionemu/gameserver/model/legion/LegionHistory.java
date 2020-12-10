package com.aionemu.gameserver.model.legion;

import java.sql.Timestamp;

public class LegionHistory {
  private LegionHistoryType legionHistoryType;
  private String name = "";

  private Timestamp time;

  public LegionHistory(LegionHistoryType legionHistoryType, String name, Timestamp time) {
    this.legionHistoryType = legionHistoryType;
    this.name = name;
    this.time = time;
  }

  public LegionHistoryType getLegionHistoryType() {
    return this.legionHistoryType;
  }

  public String getName() {
    return this.name;
  }

  public Timestamp getTime() {
    return this.time;
  }
}
