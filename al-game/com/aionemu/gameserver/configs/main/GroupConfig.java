package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

public class GroupConfig {
  @Property(key = "playergroup.removetime", defaultValue = "600")
  public static int GROUP_REMOVE_TIME;
  
  @Property(key = "playergroup.maxdistance", defaultValue = "100")
  public static int GROUP_MAX_DISTANCE;
  
  @Property(key = "playeralliance.removetime", defaultValue = "600")
  public static int ALLIANCE_REMOVE_TIME;
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\configs\main\GroupConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */