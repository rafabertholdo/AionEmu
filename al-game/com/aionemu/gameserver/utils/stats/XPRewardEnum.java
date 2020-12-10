package com.aionemu.gameserver.utils.stats;

import java.util.NoSuchElementException;





















public enum XPRewardEnum
{
  MINUS_11(-11, 0),
  MINUS_10(-10, 1),
  MINUS_9(-9, 10),
  MINUS_8(-8, 20),
  MINUS_7(-7, 30),
  MINUS_6(-6, 40),
  MINUS_5(-5, 50),
  MINUS_4(-4, 60),
  MINUS_3(-3, 90),
  MINUS_2(-2, 100),
  MINUS_1(-1, 100),
  ZERO(0, 100),
  PLUS_1(1, 105),
  PLUS_2(2, 110),
  PLUS_3(3, 115),
  PLUS_4(4, 120);

  
  private int xpRewardPercent;
  
  private int levelDifference;

  
  XPRewardEnum(int levelDifference, int xpRewardPercent) {
    this.levelDifference = levelDifference;
    this.xpRewardPercent = xpRewardPercent;
  }

  
  public int rewardPercent() {
    return this.xpRewardPercent;
  }






  
  public static int xpRewardFrom(int levelDifference) {
    if (levelDifference < MINUS_11.levelDifference)
    {
      return MINUS_11.xpRewardPercent;
    }
    if (levelDifference > PLUS_4.levelDifference)
    {
      return PLUS_4.xpRewardPercent;
    }
    
    for (XPRewardEnum xpReward : values()) {
      
      if (xpReward.levelDifference == levelDifference)
      {
        return xpReward.xpRewardPercent;
      }
    } 
    
    throw new NoSuchElementException("XP reward for such level difference was not found");
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\XPRewardEnum.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
