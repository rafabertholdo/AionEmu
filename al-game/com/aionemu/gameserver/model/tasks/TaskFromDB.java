package com.aionemu.gameserver.model.tasks;

import java.sql.Timestamp;

































public class TaskFromDB
{
  private int id;
  private String name;
  private String type;
  private Timestamp lastActivation;
  private String startTime;
  private int delay;
  private String[] params;
  
  public TaskFromDB(int id, String name, String type, Timestamp lastActivation, String startTime, int delay, String param) {
    this.id = id;
    this.name = name;
    this.type = type;
    this.lastActivation = lastActivation;
    this.startTime = startTime;
    this.delay = delay;
    
    if (param != null) {
      this.params = param.split(" ");
    } else {
      this.params = new String[0];
    } 
  }





  
  public int getId() {
    return this.id;
  }






  
  public String getName() {
    return this.name;
  }







  
  public String getType() {
    return this.type;
  }






  
  public Timestamp getLastActivation() {
    return this.lastActivation;
  }






  
  public String getStartTime() {
    return this.startTime;
  }






  
  public int getDelay() {
    return this.delay;
  }






  
  public String[] getParams() {
    return this.params;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\tasks\TaskFromDB.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
