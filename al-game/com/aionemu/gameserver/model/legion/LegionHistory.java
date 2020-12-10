package com.aionemu.gameserver.model.legion;

import java.sql.Timestamp;





















public class LegionHistory
{
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


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\legion\LegionHistory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
