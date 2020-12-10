package com.aionemu.gameserver.utils.gametime;

import com.aionemu.gameserver.spawnengine.DayNightSpawnManager;
import java.security.InvalidParameterException;

























public class GameTime
{
  public static final int MINUTES_IN_YEAR = 535680;
  public static final int MINUTES_IN_MONTH = 44640;
  public static final int MINUTES_IN_DAY = 1440;
  public static final int MINUTES_IN_HOUR = 60;
  private int gameTime = 0;



  
  private DayTime dayTime;




  
  public GameTime(int time) {
    if (time < 0)
      throw new InvalidParameterException("Time must be >= 0"); 
    this.gameTime = time;
  }






  
  public int getTime() {
    return this.gameTime;
  }




  
  protected void increase() {
    this.gameTime++;
    if (getMinute() == 0)
    {
      analyzeDayTime();
    }
  }




  
  private void analyzeDayTime() {
    DayTime newDateTime = null;
    int hour = getHour();
    if (hour > 21 || hour < 4) {
      newDateTime = DayTime.NIGHT;
    } else if (hour > 16) {
      newDateTime = DayTime.EVENING;
    } else if (hour > 8) {
      newDateTime = DayTime.AFTERNOON;
    } else {
      newDateTime = DayTime.MORNING;
    } 
    if (newDateTime != this.dayTime) {
      
      this.dayTime = newDateTime;
      DayNightSpawnManager.getInstance().notifyChangeMode();
    } 
  }






  
  public int getYear() {
    return this.gameTime / 535680;
  }






  
  public int getMonth() {
    return this.gameTime % 535680 / 44640 + 1;
  }






  
  public int getDay() {
    return this.gameTime % 44640 / 1440 + 1;
  }






  
  public int getHour() {
    return this.gameTime % 1440 / 60;
  }






  
  public int getMinute() {
    return this.gameTime % 60;
  }




  
  public DayTime getDayTime() {
    return this.dayTime;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\gametime\GameTime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
