package com.aionemu.gameserver.model;




























public enum ChatType
{
  NORMAL(0),



  
  SHOUT(3),



  
  WHISPER(4),



  
  GROUP(5),



  
  ALLIANCE(6),



  
  GROUP_LEADER(7),



  
  LEGION(8),



  
  UNKNOWN_0x18(24),



  
  ANNOUNCEMENTS(25, true),



  
  PERIOD_NOTICE(28, true),



  
  PERIOD_ANNOUNCEMENTS(32, true),



  
  SYSTEM_NOTICE(33, true);








  
  private final int intValue;







  
  private boolean sysMsg;








  
  public int toInteger() {
    return this.intValue;
  }










  
  public static ChatType getChatTypeByInt(int integerValue) throws IllegalArgumentException {
    for (ChatType ct : values()) {
      
      if (ct.toInteger() == integerValue)
      {
        return ct;
      }
    } 
    
    throw new IllegalArgumentException("Unsupported chat type: " + integerValue);
  }

  
  ChatType(int intValue, boolean sysMsg) {
    this.intValue = intValue;
    this.sysMsg = sysMsg;
  }





  
  public boolean isSysMsg() {
    return this.sysMsg;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\ChatType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
