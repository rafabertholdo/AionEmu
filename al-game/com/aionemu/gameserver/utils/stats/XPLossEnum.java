package com.aionemu.gameserver.utils.stats;





















public enum XPLossEnum
{
  LEVEL_8(8, 2.997997521D),
  LEVEL_9(9, 2.998974359D),
  LEVEL_10(10, 2.999872482D),
  LEVEL_16(16, 2.999258215D),
  LEVEL_20(20, 2.999859021D),
  LEVEL_21(21, 2.999782255D),
  LEVEL_22(22, 2.999856511D),
  LEVEL_24(24, 2.999925915D),
  LEVEL_33(33, 2.999791422D),
  LEVEL_41(41, 1.369142798D),
  LEVEL_44(44, 1.081953696D),
  LEVEL_50(50, 1.041314239D);
  
  private int level;
  
  private double param;
  
  XPLossEnum(int level, double param) {
    this.level = level;
    this.param = param;
  }




  
  public int getLevel() {
    return this.level;
  }




  
  public double getParam() {
    return this.param;
  }







  
  public static long getExpLoss(int level, long expNeed) {
    if (level < 8) {
      return 0L;
    }
    for (XPLossEnum xpLossEnum : values()) {
      
      if (level <= xpLossEnum.getLevel())
        return Math.round((expNeed / 100L) * xpLossEnum.getParam()); 
    } 
    return 0L;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\XPLossEnum.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
