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
